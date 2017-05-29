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

/** A trait for tree-like data structures.
  * @tparam K the key type
  * @tparam V the value type
  */
trait Tree[K, V] {

  /** `O(n)` It applies the function `f` to all key value pairs in this tree in sorted order.
    * @param f the function to apply
    */
  def inorderWalk(f: KeyValuePair[K, V] => Unit): Unit

  /** `O(1)` Checks whether `this` tree is empty
    * @return `true` if empty; `false` otherwise
    */
  def isEmpty: Boolean

  /** `O(1)` Checks whether `this` tree is non empty.
    * @return `true` if not empty; `false` otherwise
    */
  def nonEmpty: Boolean = !isEmpty

  /** `O(n)` Returns the number of elements contained in `this` tree.
    * @return the number of elements
    */
  def size: Int

  /** `O(h)` Insert a new value into `this` binary search tree.
    * @param key the `key` for the new element
    * @param value the `value` for the new element
    * @param ord the keys ordering function
    */
  def insert(key: K, value: V)(implicit ord: Ordering[K]): Unit

  /** `O(h)` Remove the provided key from `this` binary search tree.
    * @param key the `key` to remove
    * @param ord the keys ordering function
    * @return optionally, the value which correspond to the removed key
    */
  def delete(key: K)(implicit ord: Ordering[K]): Option[V]

  /** `O(h)` It search for the given key in this binary search tree.
    * @param key the key to find
    * @param ord the keys ordering
    * @return optionally the corresponding value for `key`, if exists
    */
  def search(key: K)(implicit ord: Ordering[K]): Option[V]

  /** `O(h)` Returns the minimum element in this tree.
    * @return the minimum element, if any
    */
  def min: K

  /** `O(h)` Returns the maximum element in this tree.
    * @return the maximum element, if any
    */
  def max: K

  /** Returns the successor of the key `key` in this tree.
    *
    * The successor is the node with the smallest key greater than `key`.
    *
    * @usecase def successor(key: K): K
    * @inheritdoc
    * @param key the key
    * @return the `key`'s successor
    */
  def successor(key: K)(implicit ord: Ordering[K]): K

  /** Returns the predecessor of the key `key` in this tree.
    *
    * The predecessor is the node with the greatest key smaller than `key`.
    *
    * @usecase def predecessor(key: K): K
    * @inheritdoc
    * @param key the key
    * @return the `key`'s predecessor
    */
  def predecessor(key: K)(implicit ord: Ordering[K]): K
}