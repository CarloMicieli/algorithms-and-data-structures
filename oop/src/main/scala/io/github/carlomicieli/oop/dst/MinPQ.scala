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

import scala.reflect.ClassTag

final class MinPQ[A] private(st: Array[A]) {
  private val storage: Array[A] = st
  private var heapSize = 0

  def insert(key: A)(implicit ord: Ordering[A]): Unit = {
    if (isFull) throw new Exception("MinPQ.insert: heap is full")
    heapSize = heapSize + 1
    storage(heapSize) = key
    swim()
  }

  def min(): Option[A] = if (isEmpty) None else Some(storage(1))

  def deleteMin()(implicit ord: Ordering[A]): A = {
    val min = storage(1)
    swap(1, heapSize)
    heapSize = heapSize - 1
    sink()
    min
  }

  def isFull: Boolean = heapSize == (storage.length - 1)
  def isEmpty: Boolean = heapSize == 0
  def size: Int = heapSize

  def toList: List[A] = {
    def loop(i: Int, acc: List[A]): List[A] =
      if (i <= 0) acc
      else {
        loop(i - 1, storage(i) :: acc)
      }

    loop(heapSize, List())
  }

  private def sink()(implicit ord: Ordering[A]): Unit = {
    import Ordered._

    var i = 1
    while (i <= heapSize / 2) {
      val left = i << 1
      val right = left + 1

      val small = if (left <= heapSize && a(left) < a(i)) left else i
      val j = if (right <= heapSize && a(right) < a(small)) right else small

      if (i != j) {
        swap(i, j)
        i = j
      } else {
        i = heapSize
      }
    }
  }

  private def swim()(implicit ord: Ordering[A]): Unit = {
    import Ordered._

    var i = heapSize
    while (i > 1) {
      val parent = i >> 1
      if (a(i) < a(parent)) {
        swap(i, parent)
        i = parent
      } else {
        i = 0
      }
    }
  }

  private def swap(i: Int, j: Int): Unit = {
    val tmp = storage(i)
    storage(i) = storage(j)
    storage(j) = tmp
  }

  override def toString = {
    val data = storage.toList.slice(1, heapSize + 1).mkString(", ")
    s"MinPQ($data)"
  }

  private def a(i: Int): A =
    storage(i)
}

object MinPQ {
  def apply[A: ClassTag](capacity: Int): MinPQ[A] = {
    val a = new Array[A](capacity + 1)
    new MinPQ(a)
  }
}