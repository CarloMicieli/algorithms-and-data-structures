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

package io.github.carlomicieli.oop.dst

import io.github.carlomicieli.test.AbstractSpec

class DynamicArraySpec extends AbstractSpec with DynamicArrayFixture {

  describe("A Dynamic array") {
    describe("size") {
      it("should be the max number of elements") {
        val a = DynamicArray.empty[Int](10)
        a.size shouldBe 10
      }
    }

    describe("isDefinedAt") {
      it("should return 'true' when index is inside the bounds") {
        emptyArray.isDefinedAt(1) shouldBe true
        emptyArray.isDefinedAt(0) shouldBe true
        emptyArray.isDefinedAt(emptyArray.size - 1) shouldBe true
      }

      it("should return 'false' when index is out of bounds") {
        emptyArray.isDefinedAt(-1) shouldBe false
        emptyArray.isDefinedAt(99) shouldBe false
      }
    }

    describe("apply") {
      it("should return the element at given index") {
        numbersArray(4) shouldBe 5
      }

      it("should throw an exception when the index is out of bounds") {
        val _ = the[ArrayIndexOutOfBoundsException] thrownBy {
          numbersArray(99)
        }
      }
    }

    describe("update") {
      it("should replace a value in the array at given index") {
        val a = emptyArray
        a(1) = 1
        a(2) = 2
        a(1) shouldBe 1
        a(2) shouldBe 2
      }

      it("should throw an exception when the index is out of bounds") {
        val a = emptyArray
        val _ = the[ArrayIndexOutOfBoundsException] thrownBy {
          a(99) = 100
        }
      }
    }

    describe("shift") {
      it("should slide all elements from starting point in DynamicArray") {
        val a = DynamicArray(40, 41, 42, 43, 44, 45, 46)
        a.shift(1, 1)
        a shouldBe DynamicArray(40, 41, 41, 42, 43, 44, 45)
      }
    }

    describe("insert") {
      it("should insert the new element at the first where the predicate matches") {
        val a = DynamicArray(1, 2, 4, 5, 0)
        val inserted = a.insert(3)(_ <= _)
        inserted shouldBe true
        a.toString shouldBe "[1, 2, 3, 4, 5]"
      }
    }

    describe("expand") {
      it("should grow") {
        val a = DynamicArray.empty[Int](10)
        a.size shouldBe 10

        val b = a.expand
        b.size shouldBe 15
      }
    }

    describe("shrink") {
      it("should shrink") {
        val a = DynamicArray.empty[Int](10)
        a.size shouldBe 10

        val b = a.shrink
        b.size shouldBe 6
      }
    }

    describe("toString") {
      it("should produce a string for empty arrays") {
        emptyArray.toString() shouldBe "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]"
      }

      it("should produce a string with the array elements") {
        numbersArray.toString() shouldBe "[1, 2, 3, 4, 5, 6]"
      }
    }

    describe("swap") {
      it("should swap two array elements") {
        val a = numbersArray
        val prev1 = a(1)
        val prev2 = a(2)
        a.swap(1, 2)
        a(1) shouldBe prev2
        a(2) shouldBe prev1
      }

      it("should do nothing when i and j are the same index") {
        val a = numbersArray
        val prev = a(1)
        a.swap(1, 1)
        a(1) shouldBe prev
      }
    }

    describe("equals") {
      it("should return 'false' when the two arrays are different") {
        val a1 = emptyArray
        val a2 = numbersArray
        a1 == a2 shouldBe false
      }

      it("should return 'true' when the two arrays are equals") {
        val a1 = numbersArray
        val a2 = numbersArray
        a1 == a2 shouldBe true
      }
    }

    describe("elements") {
      it("should return an iterator with the array elements") {
        numbersArray.elements.mkString(" - ") shouldBe "1 - 2 - 3 - 4 - 5 - 6"
      }
    }
  }
}

trait DynamicArrayFixture {
  def emptyArray: DynamicArray[Int] = DynamicArray.empty[Int](10)
  def dynArray(items: Int*): DynamicArray[Int] = DynamicArray(items.head, items.tail: _*)
  def numbersArray: DynamicArray[Int] = DynamicArray(1, 2, 3, 4, 5, 6)
}