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

import org.scalacheck.{ Arbitrary, Gen }
import org.scalacheck.util.Buildable

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable
import scala.language.implicitConversions

object arbitraryDoublyLinkedList {
  implicit def linkedListToTraversable[T](list: LinkedList[T]): Traversable[T] = new Traversable[T] {
    override def foreach[U](f: (T) => U): Unit = list foreach f
  }

  implicit def buildableCanBuildFrom[T, F, C](implicit c: CanBuildFrom[F, T, C]) =
    new Buildable[T, C] {
      def builder: mutable.Builder[T, C] = c.apply
    }

  implicit def buildableDoublyLinkedList[A] = new Buildable[A, LinkedList[A]] {
    override def builder = new mutable.Builder[A, LinkedList[A]] {
      val ll = new DoublyLinkedList[A]
      override def +=(elem: A): this.type = {
        ll.append(elem)
        this
      }

      override def clear(): Unit = ll.clear()

      override def result(): LinkedList[A] = ll
    }
  }

  implicit def arbitraryList[T](implicit a: Arbitrary[T]): Arbitrary[LinkedList[T]] = Arbitrary {
    import Gen._
    containerOf[LinkedList, T](a.arbitrary)
  }
}
