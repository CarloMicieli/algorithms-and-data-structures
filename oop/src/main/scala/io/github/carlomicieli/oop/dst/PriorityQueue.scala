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

/** This is a collection of prioritized elements that allows arbitrary element insertion,
  * and allows the removal of the element that has first priority.
  *
  * When an element is added to a priority queue, the user designates its priority by providing an
  * associated key. The element with the minimal key will be the next to be removed from the queue.
  *
  * A priority queue may have multiple entries with equivalent keys, in which case
  * methods min and removeMin may report an arbitrary choice among those entry
  * having minimal key.
  *
  * @tparam K the Key type
  * @tparam V the Value type
  * @author Carlo Micieli
  * @since 1.0.0
  */
trait PriorityQueue[K, V] {
  /** Creates an entry with key k and value v in the priority queue.
    * @param key the key
    * @param value the value
    * @param ord the keys natural ordering
    */
  def insert(key: K, value: V)(implicit ord: Ordering[K]): Unit

  /** Returns (but does not remove) a priority queue entry (k,v)
    * having minimal key; returns null if the priority queue is empty.
    * @return
    */
  def min: (K, V)

  /** Removes and returns an entry (k,v) having minimal key from
    * the priority queue; returns null if the priority queue is empty.
    * @return
    */
  def removeMin(): (K, V)

  /** Returns the number of entries in the priority queue.
    * @return
    */
  def size: Int

  /** Returns a boolean indicating whether the priority queue is empty
    * @return
    */
  def isEmpty: Boolean
}

object PriorityQueue {
  /** Creates a new binary heap with the provided capacity.
    * @param s the heap capacity
    * @tparam K the Key type
    * @tparam V the Value type
    * @return a new Priority queue
    */
  def binaryHeap[K: Ordering: ClassTag, V: ClassTag](s: Int): PriorityQueue[K, V] = {
    BinaryHeap(s)
  }
}