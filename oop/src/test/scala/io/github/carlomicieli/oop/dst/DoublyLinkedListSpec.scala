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

      it("should return a Some with the head value for non empty lists") {
        val l = list(1, 2, 33)
        l.headOption shouldBe Some(1)

        val l2 = singletonList(42)
        l2.headOption shouldBe Some(42)
      }
    }

    describe("lastOption") {
      it("should return a None for the empty list") {
        val l = DoublyLinkedList.empty[Int]
        l.lastOption shouldBe Option.empty[Int]
      }

      it("should return a Some with the last element for non empty lists") {
        val l = list(1, 2, 33)
        l.lastOption shouldBe Some(33)

        val l2 = singletonList(42)
        l2.lastOption shouldBe Some(42)
      }
    }

    describe("append") {
      it("should add a new element to a empty list") {
        val l = newEmptyList
        l.append(42)
        l.length shouldBe 1
      }

      it("should add a new element to a non empty list") {
        val l = list(1, 2)
        l.append(42)
        l.length shouldBe 3
      }
    }

    describe("prepend") {
      it("should prepend a new element to a empty list") {
        val l = newEmptyList
        l.prepend(42)
        l.length shouldBe 1
      }

      it("should prepend a new element to a singleton list") {
        val l = singletonList(42)
        l.prepend(1)
        l.length shouldBe 2
        l.head shouldBe 1
      }

      it("should prepend a new element to a list") {
        val l = list(42, 84)
        l.prepend(1)
        l.length shouldBe 3
        l.head shouldBe 1
      }
    }

    describe("isEmpty") {
      it("should return true for the empty list") {
        val l = DoublyLinkedList.empty[Int]
        l.isEmpty shouldBe true
      }

      it("should return false for non empty list") {
        val l = singletonList(42)
        l.isEmpty shouldBe false
      }
    }

    describe("length") {
      it("should return 0 for the empty list") {
        val l = DoublyLinkedList.empty[Int]
        l.length shouldBe 0
      }

      it("should return 1 for the signleton list") {
        val l = singletonList(42)
        l.length shouldBe 1
      }
    }

    describe("foldLeft") {
      it("should return the initial value for empty lists") {
        val l = DoublyLinkedList.empty[Int]
        l.foldLeft(42)(_ + _) shouldBe 42
      }

      it("should apply a function to list elements from left") {
        val l = list(1, 2, 3, 4, 5)
        l.foldLeft(0)(_ + _) shouldBe 15
      }
    }

    describe("foldRight") {
      it("should return the initial value for empty lists") {
        val l = DoublyLinkedList.empty[Int]
        l.foldRight(42)(_ + _) shouldBe 42
      }

      it("should apply a function to list elements from right") {
        val l = list(1, 2, 3, 4, 5)
        l.foldRight(0)(_ + _) shouldBe 15
      }
    }

    describe("mkString") {
      it("should produce a string representation for empty string") {
        val l = newEmptyList
        l.mkString(", ", "[", "]") shouldBe "[]"
      }

      it("should produce a string representation for the singleton string") {
        val l = singletonList(42)
        l.mkString(", ", "[", "]") shouldBe "[42]"
      }

      it("should produce a string representation for a list") {
        val l = list(1, 2, 3)
        l.mkString(", ", "[", "]") shouldBe "[1, 2, 3]"
      }
    }

    describe("find") {
      it("should return a None for empty list") {
        val l = newEmptyList
        l.find(_ > 10) shouldBe None
      }

      it("should return a None when no value matches the predicate") {
        val l = list(1, 2, 340, 2)
        l.find(_ == 42) shouldBe None
      }

      it("should return a Some with the first value which match the predicate") {
        val l = list(1, 2, 340, 2)
        l.find(_ > 10) shouldBe Some(340)
      }
    }

    describe("remove") {
      it("should remove an element from the empty list") {
        val l = newEmptyList
        l.remove(42)
        l.length shouldBe 0
      }

      it("should remove an element from the singleton list") {
        val l = singletonList(42)
        l.remove(42)
        l.length shouldBe 0
      }

      it("should remove an element from a list") {
        val l = list(42, 4, 556, 7)
        l.remove(42)
        l.length shouldBe 3
      }
    }

    describe("clear") {
      it("should make a list empty") {
        val l = list(42, 4, 556, 7)
        l.clear()
        l.isEmpty shouldBe true
        l.length shouldBe 0
      }
    }
  }
}

trait DoublyLinkedLists {
  def newEmptyList: LinkedList[Int] = DoublyLinkedList.empty[Int]

  def singletonList(x: Int): LinkedList[Int] = {
    val l = DoublyLinkedList.empty[Int]
    l.append(x)
    l
  }

  def list(xs: Int*): LinkedList[Int] = DoublyLinkedList(xs: _*)
}