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
package io.github.carlomicieli.dst.mutable

import org.scalatest.{Matchers, FlatSpec}

class DynamicArraySpec extends FlatSpec with Matchers {
  "An empty DynamicArray" should "have a length" in {
    val a = DynamicArray.empty[Int](10)
    a.length should be(10)
  }

  "Setting element to DynamicArray" should "be index based" in {
    val a = DynamicArray.empty[Int](10)
    a(0) = 1
    a(1) = 2
    a(0) should be(1)
    a(1) should be(2)
  }

  "resize operation" should "change the DynamicArray size" in {
    val a = DynamicArray.empty[Int](5)
    val b = a.resize(2.0)
    b.length should be(10)
  }

  "shift" should "slide all elements from starting point in DynamicArray" in {
    val a = DynamicArray(1, 2, 3, 4, 5)
    a.shift(1, 1)

    a.toString should be("[1, 2, 2, 3, 4]")
  }

  "insert()" should "insert the new element when the predicate is true" in {
    val a = DynamicArray(1, 2, 4, 5, 0)
    val inserted = a.insert(3)(_ <= _)
    inserted should be(true)
    a.toString should be("[1, 2, 3, 4, 5]")
  }

  "DynamicArray" should "growth and shrink" in {
    val a = DynamicArray.empty[Int](10)
    a.length should be(10)

    val b = a.expand
    b.length should be(15)

    val c = a.shrink
    c.length should be(6)
  }
}
