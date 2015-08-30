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

import io.github.carlomicieli.util.Maybe

/**
 * It represents a binary search tree.
 * @tparam K the `Key` type
 * @tparam V the `Value` type
 */
sealed trait BsTree[+K, +V] {
  def get: (K, V)

  /**
   * The depth from this `BsTree`.
   * @return
   */
  def depth: Int

  /**
   * The element with the maximum key in this `BsTree` according to the key ordering.
   * @return `Just(max)` if this `BsTree` is not empty, `None` otherwise
   */
  def max: Maybe[K]

  /**
   * The element with the minimum key in this `BsTree` according to the key ordering.
   * @return `Just(min)` if this `BsTree` is not empty, `None` otherwise
   */
  def min: Maybe[K]

  /**
   * Returns the number of pair key-value contained in this `BsTree`
   * @return
   */
  def size: Int

  /**
   * Checks whether the current `BsTree` is empty
   * @return
   */
  def isEmpty: Boolean

  /**
   * It searches for the `key` in this `BsTree`. It returns `Just((key, value))` when the
   * `key` is found, `None` otherwise.
   *
   * @param key
   * @param ord
   * @tparam K1
   * @return
   */
  def lookup[K1 >: K](key: K1)(implicit ord: Ordering[K1]): Maybe[(K1, V)]

  /**
    * If the key `key` doesn't exist, this operation will insert it with the value `value`. On the
   * other hand, if the `key` already exists the value is updated applying the function `f` to the
   * current value.
   *
   * @param key
   * @param value
   * @param f
   * @param ord
   * @tparam K1
   * @tparam V1
   * @return
   */
  def upsert[K1 >: K, V1 >: V](key: K1, value: V1)(f: V1 => V1)(implicit ord: Ordering[K1]): BsTree[K1, V1]

  /**
    * Inserts the `key` and the corresponding `value` to the `BsTree`. If the `key` already exists
   * this operation will replace the previous value.
   *
   * @param key
   * @param value
   * @param ord
   * @tparam K1
   * @tparam V1
   * @return
   */
  def insert[K1 >: K, V1 >: V](key: K1, value: V1)(implicit ord: Ordering[K1]): BsTree[K1, V1]

  /**
    * Delete the node with the provided key. If this `BsTree` doesn't contain the key, the
   * `BsTree` is returned unchanged.
   *
   * @param key the key to be removed
   * @param ord the key ordering
   * @tparam K1
   * @return
   */
  def delete[K1 >: K](key: K1)(implicit ord: Ordering[K1]): (Maybe[V], BsTree[K1, V])

  /**
   * Convert this `BsTree` to a `List` of pair.
   * @return the list with the pair `(key, value)` in this `BsTree`
   */
  def toList: List[(K, V)]
}

object BsTree {
  /**
   * It creates an empty binary search tree.
   * @param ord
   * @tparam K
   * @tparam V
   * @return
   */
  def empty[K, V](implicit ord: Ordering[K]): BsTree[K, V] = ???

  /**
   * It creates a new binary search tree with the provided `elements`.
   * @param elements
   * @param ord
   * @tparam K
   * @tparam V
   * @return
   */
  def apply[K, V](elements: (K, V)*)(implicit ord: Ordering[K]): BsTree[K, V] = ???

  /**
   * It creates a binary search tree from the list elements.
   * @param xs
   * @param ord
   * @tparam K
   * @tparam V
   * @return
   */
  def fromList[K, V](xs: List[(K, V)])(implicit ord: Ordering[K]): BsTree[K, V] = ???
}
