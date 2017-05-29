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

import scala.reflect.ClassTag

/** Created by carlo on 29/05/17.
  */
private[this] class BinaryHeap[K, V] private (a: Array[(K, V)]) extends PriorityQueue[K, V] {

  //private val array: Array[(K, V)] = a
  private val heapSize: Int = 0

  override def insert(key: K, value: V)(implicit ord: Ordered[K]): Unit = ???

  override def min: (K, V) = ???

  override def removeMin(): (K, V) = ???

  override def size: Int = heapSize

  override def isEmpty: Boolean = heapSize == 0
}

private[this] object BinaryHeap {
  def apply[K <: Ordered[K] with ClassTag[K], V: ClassTag](s: Int): BinaryHeap[K, V] = {
    val a = new Array[(K, V)](s)
    new BinaryHeap(a)
  }
}