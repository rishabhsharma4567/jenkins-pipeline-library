/*-
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2017 wcm.io DevOps
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.devops.jenkins.pipeline.versioning

import com.cloudbees.groovy.cps.NonCPS
import io.wcm.devops.jenkins.pipeline.utils.ListUtils

/**
 * Jenkins groovy sandbox compatible version of
 * https://github.com/apache/maven/blob/master/maven-artifact/src/main/java/org/apache/maven/artifact/versioning/ComparableVersion.java / ListItem
 *
 * Extending ArrayList<Item> like the Original is not possible due to the sandbox so List interface was implemented.
 */
class ListItem implements List, Item, Serializable {

  static final long serialVersionUID = 1L

  ArrayList<Item> list = []

  @Override
  @NonCPS
  int compareTo(Item item) {
    if (item == null) {
      if (this.size() == 0) {
        return 0 // 1-0 = 1- (normalize) = 1
      }
      Item first = get(0)
      return first.compareTo(null)
    }
    switch (item.getType()) {
      case INTEGER_ITEM:
        return -1 // 1-1 < 1.0.x

      case STRING_ITEM:
        return 1 // 1-1 > 1-sp

      case LIST_ITEM:
        Iterator<Item> left = iterator()
        Iterator<Item> right = ((ListItem) item).iterator()

        while (left.hasNext() || right.hasNext()) {
          Item l = left.hasNext() ? left.next() : null
          Item r = right.hasNext() ? right.next() : null

          // if this is shorter, then invert the compare and mul with -1
          int result = l == null ? (r == null ? 0 : -1 * r.compareTo(l)) : l.compareTo(r)

          if (result != 0) {
            return result
          }
        }

        return 0

      default:
        throw new RuntimeException("invalid item: " + item.getClass())
    }
  }

  @Override
  @NonCPS
  int getType() {
    return LIST_ITEM
  }

  @Override
  @NonCPS
  boolean isNull() {
    return (this.size() == 0)
  }

  @NonCPS
  void normalize() {
    for (int i = this.size() - 1; i >= 0; i--) {
      Item lastItem = get(i)

      if (lastItem.isNull()) {
        // remove null trailing items: 0, "", empty list
        ListUtils.removeAt(this.list, i)
      } else if (!(isListItem(lastItem))) {
        break
      }
    }
  }

  @NonCPS
  String toString() {
    String result = ""
    for (Item item : this) {
      if (result.length() > 0) {
        if (isListItem(item)) {
          result = "${result}-"
        } else {
          result = "${result}."
        }
      }
      result = "$result${item.toString()}"
    }
    return result
  }

  /**
   * Utility function to return true for all ListItem objects
   *
   * @param object ListItem object
   * @return true
   */
  @NonCPS
  Boolean isListItem(ListItem object) {
    return true
  }

  /**
   * Utility function to return false for all non ListItem objects
   *
   * @param object Any other object that is not of type ListItem
   * @return false
   */
  @NonCPS
  Boolean isListItem(Object object) {
    return false
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  int size() {
    return 0
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  boolean isEmpty() {
    return this.list.isEmpty()
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  boolean contains(Object o) {
    return this.list.contains(o)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  Iterator iterator() {
    return this.list.iterator()
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  Object[] toArray() {
    return this.list.toArray()
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  boolean add(Object o) {
    return this.list.add(o)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  boolean remove(Object o) {
    return this.list.remove(o)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  boolean addAll(Collection c) {
    return this.list.addAll(c)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  boolean addAll(int index, Collection c) {
    return this.list.addAll(index, c)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  void clear() {
    this.list.clear()
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  Object get(int index) {
    return this.list.get(index)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  Object set(int index, Object element) {
    return this.list.set(index, element)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  void add(int index, Object element) {
    this.list.add(index, element)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  Object remove(int index) {
    return this.list.remove(index)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  int indexOf(Object o) {
    return this.list.indexOf(o)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  int lastIndexOf(Object o) {
    return this.list.lastIndexOf(o)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  ListIterator listIterator() {
    return this.list.listIterator()
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  ListIterator listIterator(int index) {
    return this.list.listIterator(index)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  List subList(int fromIndex, int toIndex) {
    return this.list.subList(fromIndex, toIndex)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  boolean retainAll(Collection c) {
    return this.list.retainAll(c)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  boolean removeAll(Collection c) {
    return this.list.removeAll(c)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  boolean containsAll(Collection c) {
    return this.list.containsAll(c)
  }

  /**
   * Adapter function for internal list object
   */
  @Override
  @NonCPS
  Object[] toArray(Object[] a) {
    return this.list.toArray(a)
  }
}
