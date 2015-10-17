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

import io.github.carlomicieli.test.AbstractSpec

class ArrayListSpec extends AbstractSpec with ArrayListFixture {
  describe("An ArrayList") {
    describe("add") {
      it("should increase the size of the array list") {
        val e = emptyArray
        e.add(42)
        e.size shouldBe 1
      }

      it("should append an element into the array list") {
        val arr = emptyArray
        arr.add(1)
        arr.add(2)
        arr.add(3)

        arr.size shouldBe 3
        arr(2) shouldBe 3
      }
    }

    describe("size") {
      it("should be 0 for empty array lists") {
        emptyArray.size shouldBe 0
      }

      it("should return the number of elements in this array list") {
        numbersArray.size shouldBe 6
      }
    }

    describe("update") {
      it("should replace a value in the array list") {
        val arr = numbersArray
        arr.update(2, 42)
        arr(2) shouldBe 42
      }

      it("should throw an exception if the index is greater than the current size") {
        val arr = numbersArray
        the [IndexOutOfBoundsException] thrownBy {
          arr(7) = 99
        } should have message "7"
      }
    }

    describe("mkString") {
      it("should produce a string representation for empty array list") {
        val arr = emptyArray
        arr.mkString(", ") shouldBe ""
      }

      it("should produce a string representation with the array list elements") {
        singletonArray.mkString(", ") shouldBe "42"
        numbersArray.mkString(".") shouldBe "1.2.3.4.5.6"
      }

      it("should produce string with prefix and suffix") {
        numbersArray.mkString(", ", "Array(", ")") shouldBe "Array(1, 2, 3, 4, 5, 6)"
      }
    }

    describe("toString") {
      it("should produce string representation for array lists") {
        emptyArray.toString() shouldBe "[]"
        numbersArray.toString() shouldBe "[1, 2, 3, 4, 5, 6]"
      }
    }

    describe("capacity") {
      it("should be initial capacity for empty array lists") {
        emptyArray.capacity shouldBe ArrayList.InitialCapacity
        emptyArray.loadFactor shouldBe 0
      }
    }

    describe("foreach") {
      it("should apply a function to all array list elements") {
        val arr = numbersArray
        var res = 0
        arr.foreach(x => res = res + x)
        res shouldBe 21
      }
    }

    describe("foldLeft") {
      it("should apply a function from left to right") {
        numbersArray.foldLeft("")((str, x) => s"($x $str)") shouldBe "(6 (5 (4 (3 (2 (1 ))))))"
        numbersArray.foldLeft(0)(_ + _) shouldBe 21
      }
    }

    describe("foldRight") {
      it("should apply a function from right to left") {
        numbersArray.foldRight("")((x, str) => s"($x $str)") shouldBe "(1 (2 (3 (4 (5 (6 ))))))"
        numbersArray.foldRight(0)(_ + _) shouldBe 21
      }
    }

    describe("remove") {
      it("should return 'false' removing an element from the empty array list") {
        val arr = emptyArray
        arr.remove(42) shouldBe false
        arr.size shouldBe 0
      }

      it("should return 'false' if the element to remove is not contained in the array list") {
        val arr = numbersArray
        arr.remove(42) shouldBe false
        arr.size shouldBe 6
      }

      it("should return 'true' when an element has been removed") {
        val arr = numbersArray
        arr.remove(5) shouldBe true
        arr.toString() shouldBe "[1, 2, 3, 4, 6]"
      }
    }

    describe("equals") {
      it("should check whether two array lists are equals") {
        val x = numbersArray
        val y = numbersArray
        x.equals(y) shouldBe true
      }

      it("should check whether two array lists are different") {
        val x = numbersArray
        val y = emptyArray
        x.equals(y) shouldBe false
      }
    }
  }
}

trait ArrayListFixture {
  def emptyArray: ArrayList[Int] = ArrayList.empty[Int]

  def singletonArray: ArrayList[Int] = ArrayList(42)

  def numbersArray: ArrayList[Int] = ArrayList(1, 2, 3, 4, 5, 6)
}
