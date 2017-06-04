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

private[this] class BinaryHeap[K, V] private (a: Array[(K, V)])(implicit val ord: Ordering[K])
    extends PriorityQueue[K, V] {

  private val array: Array[(K, V)] = a
  private var heapSize: Int = 0

  override def insert(key: K, value: V): Unit = {
    array(heapSize) = key -> value
    fixHeap(heapSize)
    heapSize += 1
  }

  override def decreaseKey(i: Int, newKey: K): Unit = {
    import Ordered._

    if (i < 0 || i > heapSize)
      throw new Exception("Binary Heap: index i is out of bounds")

    if (newKey > array(i)._1)
      throw new Exception("Binary Heap: new key is greater than current key")

    array(i) = array(i).copy(_1 = newKey)
    fixHeap(i)
  }

  override def min: (K, V) = {
    if (isEmpty)
      throw new BinaryHeapUnderflow

    array(0)
  }

  override def extractMin(): (K, V) = {
    if (isEmpty)
      throw new BinaryHeapUnderflow

    val min = array(0)
    heapSize = heapSize - 1
    swap(0, heapSize)
    minHeapify(0)
    min
  }

  override def size: Int = heapSize

  override def isEmpty: Boolean = heapSize == 0

  private def parent(i: Int): Int = i >> 1
  private def left(i: Int): Int = i << 1
  private def right(i: Int): Int = (i << 1) + 1

  private def buildMinHeap(length: Int): Unit = {
    val start = math.floor(length / 2).toInt
    for (i <- start downTo 1) {
      minHeapify(i)
    }
  }

  private def fixHeap(i: Int): Unit = {
    var j = i
    while (j > 0 && !heapPropertyHold(j)) {
      swap(j, parent(j))
      j = parent(j)
    }
  }

  @tailrec private def minHeapify(i: Int): Unit = {
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

  private def less(i: Int, j: Int): Boolean = {
    import Ordered._
    array(i)._1 < array(j)._1
  }

  private def heapPropertyHold(i: Int): Boolean = {
    less(parent(i), i)
  }
}

/** A `binary heap` is a data structure that takes the form of a binary tree.
  *
  * == Overview ==
  * A binary heap is defined as a binary tree with two additional constraints:
  *
  * - ''Shape property'': a binary heap is a complete binary tree; that is, all levels of the tree, except possibly
  * the last one (deepest) are fully filled, and, if the last level of the tree is not complete, the nodes of
  * that level are filled from left to right.
  *
  * - ''Heap property'': the key stored in each node is either greater than or equal to (≥) or less than or equal to (≤)
  * the keys in the node's children, according to some total order.
  */
object BinaryHeap {
  /** Creates a new binary heap with the provided capacity.
    *
    * @param s the heap capacity
    * @tparam K the Key type
    * @tparam V the Value type
    * @return a new Priority queue
    */
  def apply[K: Ordering: ClassTag, V: ClassTag](s: Int): PriorityQueue[K, V] = {
    require(s > 0, "Binary heap size must be positive")
    val a = new Array[(K, V)](s)
    new BinaryHeap(a)
  }
}

final class BinaryHeapOverflow extends NoSuchElementException("Binary Heap overflow: is full")
final class BinaryHeapUnderflow extends NoSuchElementException("Binary Heap underflow: is empty")