/*
 *                       __                  __
 *      ______________ _/ /___ _      ____ _/ /___ _____
 *     / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *    (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 *   /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                         /____/
 *  Copyright (c) 2015 the original author or authors.
 *  See the LICENCE.txt file distributed with this work for additional
 *  information regarding copyright ownership.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.carlomicieli.util

import io.github.carlomicieli.test.AbstractTestSpec
import io.github.carlomicieli.dst.immutable.List

class MaybeSpec extends AbstractTestSpec {
  "A Maybe value" should "return whether is defined or not" in {
    val none = Maybe.empty[Int]
    val just = Maybe(42)
    none.isDefined shouldBe false
    just.isDefined shouldBe true
  }

  "It" should "extract the contained value from Just[_]" in {
    val just = Maybe(42)
    just.get shouldBe 42
  }

  "It" should "throw an exception extracting a value from a None" in {
    val none = Maybe.empty[Int]
    val r = intercept[NoSuchElementException] {
      none.get
    }
  }

  "getOrElse" should "provide the contained value, or a default for None" in {
    val none = Maybe.empty[Int]
    val just = Maybe(42)

    just.getOrElse(-1) shouldBe 42
    none.getOrElse(-1) shouldBe -1
  }

  "orElse" should "return a new value if it a None" in {
    val none = Maybe(null)
    val just42 = Maybe(42)

    none.orElse(just42) shouldBe just42
    just42.orElse(just42) shouldBe just42
  }

  "foreach" should "apply a function f to the value for its side effects" in {
    val none = Maybe(null)
    val just42 = Maybe(42)
    var res = 0
    none.foreach(x => res = res + 1)
    res shouldBe 0

    just42.foreach(x => res = x + 1)
    res shouldBe 43
  }

  "map" should "apply the function to Just values" in {
    val none = Maybe.empty[Int]
    val just42 = Maybe(42)

    just42.map(_ * 2) shouldBe Just(84)
    none.map(_ * 2) shouldBe None
  }

  "flatMap" should "apply the function to Just values" in {
    val none = Maybe.empty[Int]
    val just42 = Maybe(42)

    just42.flatMap(x => Maybe(x * 2)) shouldBe Just(84)
    none.flatMap(x => Maybe(x * 2)) shouldBe None
  }

  "it" should "produce a string representation for Maybe values" in {
    val none = Maybe.empty[Int]
    val just42 = Maybe(42)

    none.toString shouldBe "None"
    just42.toString shouldBe "Just(42)"
  }

  "it" should "convert a Maybe value to a list" in {
    val none = Maybe.empty[Int]
    val just42 = Maybe(42)

    none.toList shouldBe List()
    just42.toList shouldBe List(42)
  }

  "catMaybes" should "extract the Just values from a list" in {
    val list = List(Just(1), None, None, Just(4), Just(5), None)
    Maybe.catMaybes(list) shouldBe List(1, 4, 5)
  }
}
