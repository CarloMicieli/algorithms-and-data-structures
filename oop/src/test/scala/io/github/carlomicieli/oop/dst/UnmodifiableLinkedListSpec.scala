/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
 *
 * Copyright (c) 2015 the original author or authors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.carlomicieli.oop.dst

import io.github.carlomicieli.test.AbstractSpec

class UnmodifiableLinkedListSpec extends AbstractSpec with UnmodifiableLinkedListFixture {
  describe("An unmodifiable LinkedList") {
    describe("reading operations") {
      it("should reuse the inner LinkedList implementation") {
        val (inner, l) = newTestLists
        l.headOption shouldBe inner.headOption
        l.lastOption shouldBe inner.lastOption
        l.length shouldBe inner.length
        l.isEmpty shouldBe inner.isEmpty
        l.nonEmpty shouldBe inner.nonEmpty
        l.find(_ > 0) shouldBe inner.find(_ > 0)
        l.foldLeft(0)(_ + _) shouldBe inner.foldLeft(0)(_ + _)
        l.foldRight(0)(_ + _) shouldBe inner.foldRight(0)(_ + _)
        l.contains(42) shouldBe inner.contains(42)
      }
    }

    describe("modifying operations") {
      it("should throw an exception") {
        val expectedMsg: String = "This list is unmodifiable"
        val l = unmodifiableList(numbersList)

        the [UnsupportedOperationException] thrownBy {
          l.addBack(42)
        } should have message expectedMsg

        the [UnsupportedOperationException] thrownBy {
          l.addFront(42)
        } should have message expectedMsg

        the [UnsupportedOperationException] thrownBy {
          l.clear()
        } should have message expectedMsg

        the [UnsupportedOperationException] thrownBy {
          l.remove(42)
        } should have message expectedMsg

        the [UnsupportedOperationException] thrownBy {
          l.insert(42)
        } should have message expectedMsg

        the [UnsupportedOperationException] thrownBy {
          l.removeHead()
        } should have message expectedMsg
      }
    }
  }
}

trait UnmodifiableLinkedListFixture {
  def numbersList: LinkedList[Int] = LinkedList(1, 5, 15, 42, 52, 99)
  def unmodifiableList(l: LinkedList[Int]): LinkedList[Int] = LinkedList.unmodifiableList(l)

  def newTestLists: (LinkedList[Int], LinkedList[Int]) = {
    val n = numbersList
    val u = unmodifiableList(n)
    (n, u)
  }
}