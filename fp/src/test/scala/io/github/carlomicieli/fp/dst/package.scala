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

package io.github.carlomicieli.fp

import org.scalacheck.util.Buildable
import org.scalacheck.{ Gen, Arbitrary }
import org.scalatest.enablers.Length

import scala.language.implicitConversions

package object dst {
  implicit val listLength: Length[List[_]] = new Length[List[_]] {
    def lengthOf(obj: List[_]): Long = obj.length
  }

  implicit def listToTraversable[T](list: List[T]): Traversable[T] = new Traversable[T] {
    override def foreach[U](f: (T) => U): Unit = list.foreach(f)
  }

  implicit def arbitraryList[T](implicit a: Arbitrary[T]): Arbitrary[List[T]] = Arbitrary {
    import Arbitrary._
    import Gen._

    val genEmptyList = const(List.empty[T])

    val genSingletonList = for { x <- arbitrary[T] } yield List(x)

    def genList(sz: Int): Gen[List[T]] = containerOfN[List, T](sz, arbitrary[T])

    def sizedList(sz: Int) =
      if (sz <= 0) genEmptyList
      else Gen.frequency((1, genEmptyList), (1, genSingletonList), (8, genList(sz)))

    Gen.sized(sz => sizedList(sz))
  }

  implicit def buildableList[T]: Buildable[T, List[T]] = new Buildable[T, List[T]] {
    def builder = new scala.collection.mutable.Builder[T, List[T]]() {
      private var list = List.empty[T]

      override def +=(elem: T): this.type = {
        list = elem +: list
        this
      }

      override def result(): List[T] = list

      override def clear(): Unit = list = List.empty[T]
    }
  }

  implicit def stackToTraversable[T](stack: Stack[T]): Traversable[T] = new Traversable[T] {
    override def foreach[U](f: (T) => U): Unit = stack.foreach(f)
  }

  implicit def buildableStack[T]: Buildable[T, Stack[T]] = new Buildable[T, Stack[T]] {
    def builder = new scala.collection.mutable.Builder[T, Stack[T]]() {
      private var stack = Stack.empty[T]

      override def +=(elem: T): this.type = {
        stack = stack push elem
        this
      }

      override def result(): Stack[T] = stack

      override def clear(): Unit = stack = Stack.empty[T]
    }
  }

  implicit def arbitraryStack[T](implicit a: Arbitrary[T]): Arbitrary[Stack[T]] = Arbitrary {
    import Arbitrary._
    import Gen._

    val genEmptyStack = const(Stack.empty[T])

    def genStack = containerOf[Stack, T](arbitrary[T])

    frequency((1, genEmptyStack), (2, genStack))
  }

  implicit def arbitraryStackOp[T](implicit a: Arbitrary[T]): Arbitrary[StackOp[T]] = Arbitrary {
    import Arbitrary._
    import Gen._

    val genPopOp = const(PopOp)

    def genPushOp = for { v <- arbitrary[T] } yield PushOp(v)

    frequency((1, genPopOp), (2, genPushOp))
  }
}
