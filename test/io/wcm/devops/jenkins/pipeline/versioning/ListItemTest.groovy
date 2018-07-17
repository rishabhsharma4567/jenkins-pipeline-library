/*-
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2017 - 2018 wcm.io DevOps
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

import static org.junit.Assert.*
import org.junit.Test

class ListItemTest {

  @Test
  void shouldReturnCorrectListSizeSingleItemModififation() {
    ListItem underTest = new ListItem()
    assertEquals(0, underTest.size())
    underTest.add("1")
    assertEquals(1, underTest.size())
    underTest.add("2")
    underTest.add("3")
    assertEquals(3, underTest.size())
    underTest.add(0, "0")
    assertEquals(4, underTest.size())
    underTest.remove("0")
    assertEquals(3, underTest.size())
    underTest.remove("3")
    assertEquals(2, underTest.size())
    underTest.remove("2")
    assertEquals(1, underTest.size())
    underTest.remove("1")
    assertEquals(0, underTest.size())
  }

  @Test
  void shouldReturnCorrectListSizeOnAddAll() {
    ListItem underTest = new ListItem()
    assertEquals(0, underTest.size())
    underTest.addAll([1,2,3])
    assertEquals(3, underTest.size())
    underTest.addAll([4])
    assertEquals(4, underTest.size())
    underTest.removeAll([1,2])
    assertEquals(2, underTest.size())
    underTest.retainAll([3])
    assertEquals(1, underTest.size())
    underTest.clear()
    assertEquals(0, underTest.size())
  }

}
