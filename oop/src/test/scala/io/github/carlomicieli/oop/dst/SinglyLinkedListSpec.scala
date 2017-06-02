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

import scala.util.Success

class SinglyLinkedListSpec extends AbstractSpec with SinglyLinkedListFixture {
  describe("A singly linked list") {
    describe("head") {
      it("should throw an exception for the empty list") {
        the[EmptyLinkedListException] thrownBy {
          emptyList.head
        } should have message "LinkedList.head: this list is empty"
      }

      it("should return the first element in the list") {
        numbersList.head shouldBe 1
      }
    }

    describe("headOption") {
      it("should return 'None' for the empty list") {
        emptyList.headOption shouldBe None
      }

      it("should return a 'Just' for non empty lists") {
        numbersList.headOption shouldBe Some(1)
      }
    }

    describe("last") {
      it("should throw an exception for the empty list") {
        the[EmptyLinkedListException] thrownBy {
          emptyList.last
        } should have message "LinkedList.last: this list is empty"
      }

      it("should return the last element in the list") {
        numbersList.last shouldBe 6
      }
    }

    describe("lastOption") {
      it("should return 'None' for the empty list") {
        emptyList.lastOption shouldBe None
      }

      it("should return a 'Just' for non empty lists") {
        numbersList.lastOption shouldBe Some(6)
      }
    }

    describe("length") {
      it("should be 0 for the empty list") {
        emptyList.length shouldBe 0
      }

      it("should be the number of element for non empty lists") {
        numbersList.length shouldBe 6
      }
    }

    describe("isEmpty") {
      it("should be 'true' for the empty list") {
        emptyList.isEmpty shouldBe true
      }

      it("should be 'false' for non empty lists") {
        numbersList.isEmpty shouldBe false
      }
    }

    describe("nonEmpty") {
      it("should be 'false' for the empty list") {
        emptyList.nonEmpty shouldBe false
      }

      it("should be 'true' for non empty lists") {
        numbersList.nonEmpty shouldBe true
      }
    }

    describe("addFront") {
      it("should increase the list length by 1") {
        val l = emptyList
        l.prepend(1)
        l.length shouldBe 1
      }

      it("should change the list head") {
        val l = emptyList
        l.prepend(1)
        l.prepend(42)
        l.head shouldBe 42
      }
    }

    describe("addBack") {
      it("should increase the list length by 1") {
        val l = emptyList
        l.append(1)
        l.length shouldBe 1
      }

      it("should change the last element in the list") {
        val l = numbersList
        l.append(1)
        l.append(42)
        l.last shouldBe 42
      }

      it("should work as operator as well") {
        val l = numbersList
        l += 1
        l += 42
        l.length shouldBe numbersList.length + 2
        l.last shouldBe 42
      }
    }

    describe("++=") {
      it("should append items to a linked list") {
        val l = numbersList
        l ++= (1, 2, 3)
        l.toString shouldBe "[1, 2, 3, 4, 5, 6, 1, 2, 3]"
      }
    }

    describe("addFront and addBack") {
      it("should add element the list when they are intermixed") {
        val l = SinglyLinkedList.empty[Int]
        l.prepend(3)
        l.append(4)
        l.prepend(2)
        l.append(5)
        l.prepend(1)

        l.length shouldBe 5
        l.head shouldBe 1
        l.last shouldBe 5
      }
    }

    describe("insert") {
      it("should produce sorted lists") {
        val l = emptyList
        l.insert(1)
        l.insert(42)
        l.insert(99)
        l.insert(15)
        l.insert(52)
        l.length shouldBe 5
        l.toString shouldBe "[1, 15, 42, 52, 99]"
      }
    }

    describe("apply") {
      it("should create a new list") {
        val l = SinglyLinkedList(1, 2, 3, 4, 5, 6)
        l.length shouldBe 6
        l.head shouldBe 1
        l.last shouldBe 6
      }
    }

    describe("mkString") {
      it("should produce an empty string for the empty list") {
        emptyList.mkString(", ") shouldBe ""
      }

      it("should produce a string for a singleton list") {
        singletonList.mkString(", ") shouldBe "42"
      }

      it("should produce a string with the list elements") {
        numbersList.mkString(", ") shouldBe "1, 2, 3, 4, 5, 6"
      }

      it("should produce a string with prefix and suffix") {
        numbersList.mkString(", ", "<", ">") shouldBe "<1, 2, 3, 4, 5, 6>"
      }
    }

    describe("toString") {
      it("should produce a string representation for the empty list") {
        emptyList.toString shouldBe "[]"
      }

      it("should produce a string representation for non empty lists") {
        numbersList.toString shouldBe "[1, 2, 3, 4, 5, 6]"
      }
    }

    describe("foreach") {
      it("should never apply the function for the empty list") {
        var applied = false
        emptyList.foreach { n => applied = true; n + 1 }
        applied shouldBe false
      }

      it("should apply a function to all list elements") {
        var sum = 0
        numbersList.foreach { n => sum = sum + n }
        sum shouldBe 21
      }
    }

    describe("contains") {
      it("should return 'false' when the list is empty") {
        emptyList.contains(99) shouldBe false
      }

      it("should return 'false' when the element is not in the list") {
        numbersList.contains(99) shouldBe false
      }

      it("should return 'true' when the element is in the list") {
        numbersList.contains(6) shouldBe true
      }
    }

    describe("remove") {
      it("should remove an element at the middle") {
        val l = numbersList
        val res = l.remove(4)

        res shouldBe true
        l.toString shouldBe "[1, 2, 3, 5, 6]"
      }

      it("should remove the head from a list") {
        val l = numbersList
        l.remove(1) shouldBe true
        l.headOption shouldBe Some(2)
      }

      it("should remove the last from a list") {
        val l = numbersList

        l.remove(6) shouldBe true
        l.lastOption shouldBe Some(6)
      }

      it("should remove the only element of a list") {
        val l = singletonList
        l.remove(42)

        l.headOption shouldBe None
        l.lastOption shouldBe None
      }

      it("should work as operator as well") {
        val l = numbersList
        l -= 6
        l.length shouldBe 5
      }
    }

    describe("removeHead") {
      it("should remove the head from a list") {
        val l = numbersList
        val Success(h) = l.removeHead()
        h should be(1)
        l.elements.toList.length shouldBe 5
      }

      it("should return a Bad value for the empty list") {
        emptyList.removeHead().isFailure shouldBe true
      }
    }

    describe("findFirst") {
      it("should return the first key that matches the predicate") {
        val l = numbersList
        l.find {
          _ == 5
        } shouldBe Some(5)
        l.find {
          _ == 9
        } shouldBe None
      }
    }

    describe("keys") {
      it("should produce an Iterable from the linked list keys") {
        val l = numbersList
        l.elements.size shouldBe l.length
        l.elements.mkString(",") shouldBe "1,2,3,4,5,6"
      }
    }

    describe("update") {
      it("should update an element in the list") {
        val l = SinglyLinkedList(1 -> "one", 2 -> "two", 3 -> "III", 4 -> "four")
        l.update(3 -> "three")
        l.contains(3 -> "three") shouldBe true
      }
    }

    describe("foldLeft") {
      it("should return the accumulator value for the empty list") {
        emptyList.foldLeft(0)(_ + _) shouldBe 0
      }

      it("should apply a function starting from left to right") {
        numbersList.foldLeft("")((str, s) => s"($str $s)") shouldBe "(((((( 1) 2) 3) 4) 5) 6)"
      }
    }

    describe("foldRight") {
      it("should return the accumulator value for the empty list") {
        emptyList.foldRight(0)(_ + _) shouldBe 0
      }

      it("should apply a function starting from right to left") {
        numbersList.foldRight("")((str, s) => s"($str $s)") shouldBe "(1 (2 (3 (4 (5 (6 ))))))"
      }
    }

    describe("clear") {
      it("should remove all the element") {
        val l = numbersList
        l.clear()
        l.isEmpty shouldBe true
      }
    }
  }
}

trait SinglyLinkedListFixture {
  def emptyList: LinkedList[Int] = SinglyLinkedList.empty[Int]
  def numbersList: LinkedList[Int] = SinglyLinkedList(1, 2, 3, 4, 5, 6)
  def singletonList: LinkedList[Int] = SinglyLinkedList(42)
}