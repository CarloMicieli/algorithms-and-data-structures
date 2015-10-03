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
import io.github.carlomicieli.util.{Just, None}

class ImmutableListsSpec extends AbstractSpec with ImmutableListsFixture {

  describe("An immutable list") {
    describe("+:") {
      it("should add the new element in front of the list") {
        val list = 1 +: emptyList
        list.head shouldBe 1
      }

      it("should create a new list, with size increased by 1") {
        val list = 1 +: emptyList
        emptyList should be theSameInstanceAs emptyList
        list should have length 1
      }
    }

    describe("isEmpty") {
      it("should return true for empty lists") {
        emptyList.isEmpty shouldBe true
        emptyList.nonEmpty shouldBe false
      }

      it("should return false for non empty lists") {
        numbersList.isEmpty shouldBe false
        numbersList.nonEmpty shouldBe true
      }
    }

    describe("foreach") {
      it("should apply a function to list elements, for its side-effect") {
        var res = 0
        numbersList.foreach(n => res = res + n)
        res shouldBe 55
      }

      it("should not apply the function for empty lists") {
        var applied = false
        emptyList.foreach(n => applied = true)
        applied shouldBe false
      }
    }

    describe("filter") {
      it("should return the empty list, filtering the empty list") {
        emptyList.filter(_ % 2 == 0) should be theSameInstanceAs emptyList
      }

      it("should filter out elements that don't match the filter predicate") {
        numbersList.filter(_ % 2 == 0) shouldBe List(2, 4, 6, 8, 10)
      }

      it("should produce the empty list, if no element matches the predicate") {
        numbersList.filter(_ > 999) shouldBe List()
      }

      it("should produce a copy of the original list when all elements match the predicate") {
        val list = numbersList.filter(_ > 0)
        list shouldBe numbersList
        list shouldNot be theSameInstanceAs numbersList
      }
    }

    describe("filterNot") {
      it("should return the empty list, filtering the empty list") {
        emptyList.filterNot(_ % 2 == 0) should be theSameInstanceAs emptyList
      }

      it("should filter out elements that match the filter predicate") {
        numbersList.filterNot(_ % 2 != 0) shouldBe List(2, 4, 6, 8, 10)
      }

      it("should produce the empty list, if all elements match the predicate") {
        numbersList.filterNot(_ < 999) shouldBe List()
      }

      it("should produce a copy of the original list when all elements match the predicate") {
        val list = numbersList.filterNot(_ < 0)
        list shouldBe numbersList
        list shouldNot be theSameInstanceAs numbersList
      }
    }

    describe("head") {
      it("should return None for empty list head") {
        emptyList.headOption shouldBe None
      }

      it("should return the element in front of this list") {
        numbersList.head shouldBe 1
        numbersList.headOption shouldBe Just(1)
      }

      it("should throw an exception for empty lists head") {
        the [NoSuchElementException] thrownBy {
          emptyList.head
        } should have message "List.head: empty list"
      }
    }

    describe("tail") {
      it("should throws an exception getting the list tail") {
        the[NoSuchElementException] thrownBy {
          emptyList.tail
        } should have message "List.tail: empty list"
      }

      it("should return the tail for the list") {
        val list = numbersList.tail
        list should have length (numbersList.length - 1)
        list shouldBe List(2, 3, 4, 5, 6, 7, 8, 9, 10)
      }
    }

    describe("mkString") {
      it("should produce an empty string for empty lists") {
        emptyList.mkString(" - ") shouldBe ""
      }

      it("should produce a string with a given separator") {
        numbersList.mkString(";") shouldBe "1;2;3;4;5;6;7;8;9;10"
      }

      it("should produce a string with start and end strings") {
        numbersList.mkString(";", "<<", ">>") shouldBe "<<1;2;3;4;5;6;7;8;9;10>>"
      }
    }

    describe("toString") {
      it("should have '[]' as string representation for empty lists") {
        emptyList.toString shouldBe "[]"
      }

      it("should produce string representation for non empty lists") {
        numbersList.toString shouldBe "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]"
      }
    }

    describe("length") {
      it("should return the number of elements contained") {
        numbersList should have length 10
      }

      it("should return 0 for empty lists") {
        emptyList should have length 0
      }
    }

    describe("map") {
      it("should return an empty list, when the function is applied to empty lists") {
        val l = emptyList map { _ *  2}
        l.isEmpty shouldBe true
      }

      it("should apply a function to list elements") {
        val l = numbersList.map { _ *  2}
        l shouldBe List(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)
      }
    }

    describe("flatMap") {
      it("should return an empty list, when the function is applied to empty lists") {
        val l = emptyList flatMap { x => List(2 * x) }
        l.isEmpty shouldBe true
      }

      it("should apply a function to list elements") {
        val l = numbersList flatMap { x => List(2 * x) }
        l shouldBe List(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)
      }
    }

    describe("foldLeft") {
      it("should return the seed value for empty lists") {
        val result = emptyList.foldLeft(100)(_ - _)
        result shouldBe 100
      }

      it("should apply a binary operator to list elements, from left to right") {
        val result = numbersList.foldLeft(100)(_ - _)
        result shouldBe 45
      }
    }

    describe("foldRight") {
      it("should return the seed value for empty lists") {
        val result = emptyList.foldRight(100)(_ - _)
        result shouldBe 100
      }

      it("should apply a binary operator to list elements, from right to left") {
        val result = numbersList.foldRight(100)(_ - _)
        result shouldBe 95
      }
    }

    describe("take") {
      it("should return an empty list, take the first n elements from empty lists") {
        val l = emptyList take 5
        l.isEmpty shouldBe true
      }

      it("should take the first n elements from the list") {
        val l = numbersList take 5
        l shouldBe List(1, 2, 3, 4, 5)
      }
    }

    describe("takeWhile") {
      it("should remove the prefix until the predicate matches") {
        val l = randomList takeWhile { _ != 2 }
        l shouldBe List(56, 34)
      }

      it("should produce the empty list, from empty lists") {
        emptyList takeWhile { _ != 2 } shouldBe Nil
      }
    }

    describe("++") {
      it("should produce a new list, concatenating the two lists elements") {
        val l1 = List(5, 6, 7, 8, 9, 10)
        val l2 = List(1, 2, 3, 4, 5)

        val l3 = l1 ++ l2
        l3 shouldBe List(5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5)
      }

      it("should return the original list, if the other is empty") {
        numbersList ++ List() should be theSameInstanceAs numbersList
        List() ++ numbersList should be theSameInstanceAs numbersList
      }
    }

    describe("reverse") {
      it("should return an empty list reversing an empty list") {
        emptyList.reverse should be theSameInstanceAs emptyList
      }

      it("should produce the list in reverse order") {
        val reversedList = numbersList.reverse
        reversedList.length === numbersList.length
        reversedList shouldBe List(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
      }
    }

    describe("drop") {
      it("should remove the first n elements from the list") {
        val l2 = numbersList drop 5
        l2 shouldBe List(6, 7, 8, 9, 10)
      }

      it("should return the empty list, dropping n elements from the empty list") {
        val l1 = emptyList drop 5
        l1 shouldBe List()
      }
    }

    describe("dropWhile") {
      it("should remove the prefix until the predicate matches") {
        val l = randomList dropWhile { _ != 2 }
        l shouldBe List(2, 9, 15, 99, 52)
      }

      it("should produce an empty list, from empty lists") {
        emptyList.dropWhile(_ != 2) shouldBe Nil
      }
    }

    describe("sort") {
      it("should produce a sorted list") {
        randomList.sort shouldBe List(2, 9, 15, 34, 52, 56, 99)
      }

      it("should return the empty list, sorting the empty list") {
        emptyList.sort.isEmpty shouldBe true
      }
    }

    describe("unfoldRight") {
      it("should produce a new list from a seed value and a function") {
        val l = List.unfoldRight(1)(n => if (n < 10) Just((s"N($n)", n + 1)) else None)
        l shouldBe List("N(9)", "N(8)", "N(7)", "N(6)", "N(5)", "N(4)", "N(3)", "N(2)", "N(1)")
      }
    }

    describe("interperse") {
      it("should interperse a value between list elements") {
        val l = List('a', 'b', 'c', 'd', 'e').intersperse('-')
        l shouldBe List('a', '-', 'b', '-', 'c', '-', 'd', '-', 'e')
      }
    }

    describe("equals") {
      it("should check whether two lists are equals") {
        val l1 = numbersList
        val l2 = numbersList
        val l3 = emptyList
        val l4 = emptyList

        l1 == l2 shouldBe true
        l1 == l3 shouldBe false
        l3 == l2 shouldBe false
        l3 == l4 shouldBe true
      }
    }

    describe("flatten") {
      it("should produce an empty list, flattening empty lists") {
        val empty: List[List[Int]] = Nil
        empty.flatten shouldBe empty
      }

      it("should produce a list, after the elements have been flatten") {
        val list = List(List(1, 2), List(3), List(4, 5))
        list.flatten shouldBe List(1, 2, 3, 4, 5)
      }
    }

    describe("splitAt") {
      it("should produce two empty lists, splitting an empty list") {
        emptyList.splitAt(1) should be ((emptyList, emptyList))
      }

      it("should split the list at the given index") {
        numbersList.splitAt(4) shouldBe ((List(1, 2, 3, 4), List(5, 6, 7, 8, 9, 10)))
      }

      it("should produce an empty list as first element, if m is non positive") {
        numbersList.splitAt(-4) shouldBe ((Nil, numbersList))
      }

      it("should produce an empty list as second element, if m is greater than list length") {
        numbersList.splitAt(10) shouldBe ((numbersList, Nil))
      }
    }

    describe("span") {
      it("should produce a pair of empty lists, when applied to empty lists") {
        emptyList.span(_ > 100) shouldBe ((Nil, Nil))
      }

      it("should produce a pair of list applying a predicate") {
        numbersList.span(_ % 2 == 0) shouldBe ((List(2, 4, 6, 8, 10), List(1, 3, 5, 7, 9)))
      }

      it("should produce a pair with a Nil as first element, if no element is matching the predicate") {
        numbersList.span(_ > 999) shouldBe ((Nil, numbersList))
      }

      it("should produce a pair with a Nil as second element, if no element is matching the predicate") {
        numbersList.span(_ < 999) shouldBe ((numbersList, Nil))
      }
    }
  }
}

trait ImmutableListsFixture {
  val emptyList: List[Int] = List()
  val numbersList: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val randomList: List[Int] = List(56, 34, 2, 9, 15, 99, 52)
  def list[A](items: A*): List[A] = List(items: _*)
}
