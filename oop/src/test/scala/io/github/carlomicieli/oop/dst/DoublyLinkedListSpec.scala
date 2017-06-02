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

class DoublyLinkedListSpec extends AbstractSpec with DoublyLinkedLists {
  describe("A Doubly Linked List") {
    describe("headOption") {
      it("should return a None for the empty list") {
        val l = DoublyLinkedList.empty[Int]
        l.headOption shouldBe Option.empty[Int]
      }
    }

    describe("lastOption") {
      it("should return a None for the empty list") {
        val l = DoublyLinkedList.empty[Int]
        l.lastOption shouldBe Option.empty[Int]
      }
    }

    ignore("append") {
      it("should add a new element to a empty list") {
        val l = newEmptyList
        l.append(42)
        l.length shouldBe 1
      }
    }

    describe("isEmpty") {
      it("should return true for the empty list") {
        val l = DoublyLinkedList.empty[Int]
        l.isEmpty shouldBe true
      }
    }

    describe("length") {
      it("should return 0 for the empty list") {
        val l = DoublyLinkedList.empty[Int]
        l.length shouldBe 0
      }
    }

    describe("foldLeft") {
      it("should return the initial value for empty lists") {
        val l = DoublyLinkedList.empty[Int]
        l.foldLeft(42)(_ + _) shouldBe 42
      }
    }
  }
}

trait DoublyLinkedLists {
  def newEmptyList: LinkedList[Int] = DoublyLinkedList.empty[Int]
}