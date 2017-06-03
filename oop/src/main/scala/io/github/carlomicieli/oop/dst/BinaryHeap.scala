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

package io.github.carlomicieli
package oop
package dst

import scala.annotation.tailrec
import scala.reflect.ClassTag

private[this] class BinaryHeap[K, V] private (a: Array[(K, V)]) extends PriorityQueue[K, V] {

  private val array: Array[(K, V)] = a
  private var heapSize: Int = 0

  override def insert(key: K, value: V)(implicit ord: Ordering[K]): Unit = {
    array(heapSize) = key -> value
    heapSize += 1
  }

  override def min: (K, V) = {
    if (isEmpty)
      throw new Exception("Priority queue is empty")

    array(0)
  }

  override def removeMin(implicit ord: Ordering[K]): (K, V) = ???

  override def size: Int = heapSize

  override def isEmpty: Boolean = heapSize == 0

  private def parent(i: Int): Int = math.floor(i / 2).toInt
  private def left(i: Int): Int = i << 1
  private def right(i: Int): Int = (i << 1) + 1

  private def buildMinHeap(implicit ord: Ordering[K]): Unit = {
    val start = math.floor((heapSize - 1) / 2).toInt
    for (i <- start downTo 1) {
      minHeapify(i)
    }
  }

  @tailrec private def minHeapify(i: Int)(implicit ord: Ordering[K]): Unit = {
    val l = left(i)
    val r = right(i)

    var smallest =
      if (l <= heapSize && less(l, i))
        l
      else
        i
    if (r <= heapSize && less(r, smallest))
      smallest = r

    if (smallest != i) {
      swap(i, smallest)
      minHeapify(smallest)
    }
  }

  private def swap(i: Int, j: Int): Unit = {
    val tmp = array(i)
    array(i) = array(j)
    array(j) = tmp
  }

  private def less(i: Int, j: Int)(implicit ord: Ordering[K]): Boolean = {
    import Ordered._
    array(i)._1 <= array(j)._1
  }

  private def minHeapProperty(i: Int)(implicit ord: Ordering[K]): Boolean = {
    less(parent(i), i)
  }
}

private[this] object BinaryHeap {
  def apply[K: Ordering: ClassTag, V: ClassTag](s: Int): PriorityQueue[K, V] = {
    val a = new Array[(K, V)](s)
    new BinaryHeap(a)
  }
}