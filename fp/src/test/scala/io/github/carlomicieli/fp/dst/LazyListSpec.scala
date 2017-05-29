/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
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

package io.github.carlomicieli.fp.dst

import io.github.carlomicieli.test.AbstractTestSpec

class LazyListSpec extends AbstractTestSpec {
  "An empty LazyList" should "have size 0" in {
    val list = LazyList.empty[Int]
    list.isEmpty shouldBe true
    list.size shouldBe 0
  }

  "The size for a LazyList" should "not evaluatse the elements" in {
    val list = LazyList.cons(1, LazyList.cons(throw new Exception, LazyList.cons(3, Empty)))
    list.size shouldBe 3
  }

  "Mapping a LazyList" should "produce a new list with the function applied to its elements" in {
    val list = LazyList.cons(1, LazyList.cons(2, LazyList.cons(3, Empty)))
    val resultList = list map { _ * 3 }

    resultList.toList shouldBe List(3, 6, 9)
  }
}