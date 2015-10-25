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
package io.github.carlomicieli.dst.immutable

import io.github.carlomicieli.test.AbstractSpec
import io.github.carlomicieli.util.{Just, Good, None}

class ListStackSpec extends AbstractSpec with StackFixture {

  describe("A ListStack") {
    describe("size") {
      it("should be 0 for the empty stack") {
        val emptyStack = Stack.empty[Int]
        emptyStack.size shouldBe 0
      }

      it("should be the number of elements for a non empty stack") {
        nonEmptyStack.size shouldBe 6
      }
    }

    describe("isEmpty") {
      it("should return 'true' for the empty stack") {
        emptyStack.isEmpty shouldBe true
      }

      it("should return 'false' for the empty stack") {
        nonEmptyStack.isEmpty shouldBe false
      }
    }

    describe("nonEmpty") {
      it("should return 'false' for the empty stack") {
        emptyStack.nonEmpty shouldBe false
      }

      it("should return 'true' for the empty stack") {
        nonEmptyStack.nonEmpty shouldBe true
      }
    }

    describe("push") {
      it("should create a new Stack with the new element") {
        val newStack = emptyStack.push(99)
        newStack.size shouldBe 1
        newStack.isEmpty shouldBe false
        newStack.top shouldBe Just(99)
      }

      it("should add elements in the LIFO way") {
        val stack = emptyStack.push(1).push(2).push(3)
        stack.size shouldBe 3
        val Good((x, _)) = stack.pop
        x shouldBe 3
      }
    }

    describe("pop") {
      it("should return a 'Bad' value popping elements out of the empty stack") {
        val x = emptyStack.pop
        x.isBad shouldBe true
      }

      it("should return a 'Good' value with a pair with the popped element and resulting stack") {
        val Good((x, newStack)) = nonEmptyStack.pop
        x shouldBe 15
        newStack.size shouldBe nonEmptyStack.size - 1
      }
    }

    describe("popUntil") {
      it("should return a pair with an empty result for the empty stack") {
        val (removed, newStack) = emptyStack.popUntil(_ % 2 == 0)
        removed.isEmpty shouldBe true
        newStack.isEmpty shouldBe true
      }

      it("should return the removed elements") {
        val (removed, newStack) = nonEmptyStack.popUntil(_ != 42)
        removed.length shouldBe 2
        removed shouldBe List(15, 41)
        newStack.size shouldBe nonEmptyStack.size - 2
        newStack.top shouldBe Just(42)
      }
    }

    describe("top") {
      it("should return a 'None' for the empty stack") {
        emptyStack.top shouldBe None
      }

      it("should return a 'Just' with the top element for the non empty stack") {
        nonEmptyStack.top shouldBe Just(15)
      }
    }

    describe("toString") {
      it("should produce a string representation for the empty stack") {
        emptyStack.toString shouldBe "<emptystack>"
      }

      it("should produce a string representation for the non empty stack") {
        nonEmptyStack.toString shouldBe "<stack:top = 15>"
      }
    }
  }
}

trait StackFixture {
  def emptyStack: Stack[Int] = ListStack.empty[Int]

  def nonEmptyStack: Stack[Int] = {
    val s = ListStack.empty[Int]
    s.push(1).push(2).push(3).push(42).push(41).push(15)
  }
}