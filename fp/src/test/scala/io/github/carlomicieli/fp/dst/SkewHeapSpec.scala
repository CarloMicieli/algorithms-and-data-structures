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

import io.github.carlomicieli.test.AbstractSpec

class SkewHeapSpec extends AbstractSpec with SkewHeapTestFixture {
  describe("A Skew Heap") {
    describe("isEmpty") {
      it("should be 'true' for the empty skew heap") {
        val empty = SkewHeap.empty[Int]
        empty.isEmpty shouldBe true
      }

      it("should be 'false' when the skew heap is not empty") {
        val heap = SkewHeap.empty[Int].insert(42)
        heap.isEmpty shouldBe false
      }
    }

    describe("weight") {
      it("should be 0 for the empty skew heap") {
        emptyHeap.weight shouldBe 0
      }

      it("should be the number of entries in the skew heap") {
        heap.weight shouldBe 5
      }
    }

    describe("min") {
      it("should return None for the empty skew heap") {
        emptyHeap.min shouldBe None
      }

      it("should return Just with the entry for the minimum key") {
        heap.min shouldBe Just(15)
      }
    }

    describe("removeMin") {
      it("should return the empty skew heap if it's empty") {
        emptyHeap.removeMin shouldBe emptyHeap
      }

      it("should return a new skew heap without the minimum key entry") {
        heap.removeMin.weight shouldBe heap.weight - 1
      }
    }

    describe("insert") {
      it("should increase the heap size by 1") {
        val h = emptyHeap.insert(42)
        h.isEmpty shouldBe false
        h.weight shouldBe 1
      }
    }
  }
}

trait SkewHeapTestFixture {
  val emptyHeap: SkewHeap[Int] = SkewHeap.empty[Int]
  val heap: SkewHeap[Int] = SkewHeap.fromList(List(42, 36, 66, 15, 99))
}
