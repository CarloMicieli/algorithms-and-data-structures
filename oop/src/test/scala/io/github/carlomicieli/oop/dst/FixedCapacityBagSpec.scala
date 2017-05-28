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

package io.github.carlomicieli.oop.dst

import io.github.carlomicieli.test.AbstractSpec

class FixedCapacityBagSpec extends AbstractSpec with FixedCapacityBagsFixture {

  describe("A fixed capacity bag") {
    describe("isEmpty") {
      it("should return 'true' for an empty bag") {
        val bag = emptyFixedCapBag(16)
        bag.isEmpty shouldBe true
      }

      it("should return 'false' for non empty bags") {
        val bag = fixedCapBag(1, 2, 3, 4)
        bag.isEmpty shouldBe false
      }
    }

    describe("size") {
      it("should return 0 for empty bags") {
        val bag = emptyFixedCapBag(16)
        bag.size shouldBe 0
      }

      it("should return the number of items for non empty bags") {
        val bag = fixedCapBag(1, 2, 3, 4)
        bag.size shouldBe 4
      }
    }

    describe("add") {
      it("should add items to bags") {
        val bag = emptyFixedCapBag(16)
        bag.add(1)
        bag.add(2)
        bag.add(3)
        bag.size shouldBe 3
      }

      it("should increase the bag size") {
        val bag = emptyFixedCapBag(4)
        bag.add(5)
        bag.size shouldBe 1
      }
    }

    describe("contains") {
      it("should 'false' searching an item in an empty bag") {
        emptyFixedCapBag(16).contains(99) shouldBe false
      }

      it("should 'false' searching an item not in a bag") {
        fixedCapBag(1, 2, 3, 4).contains(99) shouldBe false
      }

      it("should 'true' searching an item not in a bag") {
        fixedCapBag(1, 2, 3, 4).contains(3) shouldBe true
      }
    }
  }
}

trait FixedCapacityBagsFixture {
  def emptyFixedCapBag(n: Int): Bag[Int] = FixedCapacityBag[Int](n)

  def fixedCapBag(items: Int*): Bag[Int] = {
    val bag = FixedCapacityBag[Int](items.length)
    for (i <- items)
      bag.add(i)
    bag
  }
}