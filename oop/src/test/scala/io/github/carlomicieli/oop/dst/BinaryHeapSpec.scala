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

package io.github.carlomicieli
package oop
package dst

import org.scalatest.{ FunSpec, Matchers }

class BinaryHeapSpec extends FunSpec with Matchers with BinaryHeaps {
  describe("A BinaryHeap") {
    describe("isEmpty") {
      it("should return true for empty heaps") {
        val heap = BinaryHeap[Int, String](16)
        heap.isEmpty shouldBe true
      }

      it("should return false for non empty heaps") {
        val heap = BinaryHeap[Int, String](16)
        heap.insert(2, "two")
        heap.isEmpty shouldBe false
      }
    }

    describe("size") {
      it("should return 0 for empty heaps") {
        val heap = BinaryHeap[Int, String](16)
        heap.size shouldBe 0
      }

      it("should return the heap size") {
        val pq = newHeap(16)
        pq.insert(42, "answer")
        pq.insert(22, "answer")
        pq.insert(12, "answer")
        pq.insert(2, "answer")
        pq.insert(1, "one")
        pq.size shouldBe 5
      }
    }

    describe("decreaseKey") {
      it("should throw an exception if the index is out of bounds") {
        val pq = newHeap(16)
        the[Exception] thrownBy {
          pq.decreaseKey(1, 5)
        } should have message "Binary Heap: index i is out of bounds"
      }

      it("should throw an exception if the new key is greater") {
        val pq = newHeap(16)
        pq.insert(3, "three")

        the[Exception] thrownBy {
          pq.decreaseKey(0, 5)
        } should have message "Binary Heap: new key is greater than current key"
      }

      it("should reduce the key and keeps the heap property") {
        val pq = newHeap(16)
        pq.insert(2, "two")
        pq.insert(4, "four")
        pq.insert(7, "seven")
        pq.insert(12, "twelve")

        pq.decreaseKey(3, 1)
        pq.min shouldBe (1 -> "twelve")
      }
    }

    describe("extractMin") {
      it("should throw an exception extracting the min from an empty heap") {
        val pq = newHeap(16)
        the[BinaryHeapUnderflow] thrownBy {
          pq.extractMin()
        } should have message "Binary Heap underflow: is empty"
      }

      it("should extracting the min value and keeps the heap property") {
        val pq = newHeap(16)
        pq.insert(42, "answer")
        pq.insert(22, "answer")
        pq.insert(12, "answer")
        pq.insert(2, "answer")
        pq.insert(1, "one")

        pq.extractMin() shouldBe (1 -> "one")
        pq.min shouldBe (2 -> "answer")
      }
    }

    describe("insert") {
      it("should add a new min to an empty binary heap") {
        val pq = newHeap(16)
        pq.insert(42, "answer")
        pq.min shouldBe 42 -> "answer"
      }

      it("should keep the heap property") {
        val pq = newHeap(16)
        pq.insert(42, "answer")
        pq.insert(1, "one")
        pq.min shouldBe 1 -> "one"
      }

      it("should keep the heap property (2)") {
        val pq = newHeap(16)
        pq.insert(42, "forty-two")
        pq.insert(2, "two")
        pq.insert(12, "twelve")
        pq.insert(22, "twenty-two")
        pq.insert(1, "one")
        pq.min shouldBe (1 -> "one")

        pq.extractMin() shouldBe 1 -> "one"
        pq.extractMin() shouldBe 2 -> "two"
        pq.extractMin() shouldBe 12 -> "twelve"
        pq.extractMin() shouldBe 22 -> "twenty-two"
        pq.extractMin() shouldBe 42 -> "forty-two"
      }
    }

    describe("min") {
      it("should throw an exception when the heap is empty") {
        val pq = newHeap(16)
        the[BinaryHeapUnderflow] thrownBy {
          pq.min
        } should have message "Binary Heap underflow: is empty"
      }
    }
  }
}

trait BinaryHeaps {
  def newHeap(size: Int): PriorityQueue[Int, String] = BinaryHeap(size)
}