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

package io.github.carlomicieli.oop.sorting

import io.github.carlomicieli.test.AbstractTestSpec

class BubbleSortSpec extends AbstractTestSpec with SortingFixture {

  "Bubble Sort" should "sort random arrays" in {
    val cs = arrayFrom("BUBBLESORTEXAMPLE")

    Bubble.sort(cs)

    cs.asString shouldBe "ABBBEEELLMOPRSTUX"
    cs.isSorted shouldBe true
  }

  "Bubble sort" should "sort only a part of an array" in {
    val cs = arrayFrom("BUBBLESORTEXAMPLE")

    Bubble.sort(cs, 0, 6)

    cs.asString shouldBe "BBBELUSORTEXAMPLE"
  }

  "Bubble Sort" should "also sort already sorted array" in {
    val array = arrayFrom("ABCDEFGHILM")

    Bubble.sort(array)

    array.asString shouldBe "ABCDEFGHILM"
    array.isSorted shouldBe true
  }
}