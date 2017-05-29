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

final class MaxPQ[A] private (storage: Array[A], size: Int) {
  private var hSize = size

  def heapSize: Int = hSize
  def length: Int = storage.length

  def max: A = storage(0)

  def removeMax()(implicit ord: Ordering[A]): A = {
    val maxValue = max
    hSize = hSize - 1
    swap(storage, 1, heapSize)
    maxHeapify(1)
    maxValue
  }

  private def maxHeapify(i: Int)(implicit ord: Ordering[A]): Unit = {
    import Ordered._

    def checkHeapProp(x: Int, y: Int): Boolean = {
      y <= heapSize && a(y) > a(x)
    }

    def findLargest(i: Int): Option[Int] = {
      val l = left(i)
      val r = right(i)

      val largest = if (checkHeapProp(i, l))
        Some(l)
      else
        Some(i)

      largest flatMap { el =>
        if (checkHeapProp(el, r))
          Some(r)
        else if (el == i) None else Some(el)
      }
    }

    findLargest(i) match {
      case None => ()
      case Some(largest) =>
        swap(storage, i, largest)
        maxHeapify(largest)
    }
  }

  private def swap(array: Array[A], i: Int, j: Int): Unit = {
    val ii = i - 1
    val ij = j - 1
    val tmp: A = array(ii)
    array(ii) = array(ij)
    array(ij) = tmp
  }

  //private def parent(i: Int): Int = i >> 1
  private def left(i: Int): Int = i << 1
  private def right(i: Int): Int = 1 + (i << 1)

  private def a(i: Int): A = storage(i - 1)

  override def toString: String = {
    val s = storage.toList.take(heapSize).mkString(", ")
    s"MaxPQ($s)"
  }
}

object MaxPQ {
  implicit class IntOps(val i: Int) extends AnyVal {
    def downTo(n: Int): Range = n.to(i).reverse
  }

  def apply[A: scala.reflect.ClassTag](capacity: Int): MaxPQ[A] = {
    val st = new Array[A](capacity)
    new MaxPQ[A](st, 0)
  }

  def apply[A](array: Array[A])(implicit ord: Ordering[A]): MaxPQ[A] = {
    val N = array.length
    val heap = new MaxPQ[A](array, N)
    for (i <- (N / 2) downTo 1) {
      heap.maxHeapify(i)
    }
    heap
  }
}