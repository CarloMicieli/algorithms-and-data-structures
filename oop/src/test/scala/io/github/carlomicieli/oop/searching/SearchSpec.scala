/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                   /____/
 * Copyright (c) 2017 the original author or authors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.carlomicieli.oop.searching

import io.github.carlomicieli.test.AbstractTestSpec

class SearchSpec extends AbstractTestSpec with BinarySearchFixture {
  import Search._

  "binary search" should "find an element in a sorted array" in {
    binarySearch(charsArray, charsArray(12)) shouldBe Some(12)
    binarySearch(charsArray, '@') shouldBe None
  }

  "binary search" should "find an element in a range in a sorted array" in {
    binarySearch(numbersArray, 150, 100, 200) shouldBe Some(150)
    binarySearch(numbersArray, 50, 100, 200) shouldBe None
  }
}

trait BinarySearchFixture {
  def charsArray: Array[Char] = ('A' to 'Z').toArray
  def numbersArray: Array[Int] = (0 to 1000).toArray
}