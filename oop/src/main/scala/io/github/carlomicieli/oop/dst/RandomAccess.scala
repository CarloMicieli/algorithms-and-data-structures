/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                   /____/
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

import io.github.carlomicieli.util.Default

/** A trait for data structure implementations which allow efficient
  * random access operations (like arrays).
  *
  * @tparam A the element type
  */
trait RandomAccess[A] extends PartialFunction[Int, A] {

  /** Updates the element at the index `i` with the given value `x`.
    *
    * This operation is throwing an exception if the index `i` is greater than the
    * `capacity`. In other words, this operation is not allowed to grow the data
    * structure.
    *
    * @param i the element index
    * @param x the new value to store at index `i`
    */
  def update(i: Int, x: A): Unit

  /** It appends a new element `x`. This method can force the data structure to
    * grow if the loading factor is too high.
    *
    * @param x the new element
    */
  def add(x: A): Unit

  /** Checks whether this array list contains the given element `x`.
    * @param x the element to be tested
    * @return `true` if the element is this array list; `false` otherwise
    */
  def contains(x: A): Boolean

  /** Removes the element with value `x`, and slide the remaining elements
    * to the left.
    *
    * This operation can cause the data structure to shrink if the loading
    * factor is becoming too low after the element has been removed.
    *
    * @param x the element to remove
    * @return `true` if an element has been removed; `false` otherwise
    */
  def remove(x: A): Boolean

  /** Returns the maximum number of elements which this data structure
    * can store.
    *
    * @return the capacity
    */
  def capacity: Int

  /** Returns the number of elements contained in this data structure.
    * @return the number of elements
    */
  def size: Int

  /** Returns the current loading factor for this data structure.
    * The allowed range for loading factor values is `[0.0, 1.0)`.
    *
    * @return the load factor
    */
  def loadFactor: Float = size.toFloat / capacity.toFloat

  /** It shrinks the underlying array, but only if the provided `newCapacity`
    * value is at least large as the current size.
    *
    * @param newCapacity the new capacity
    */
  def ensureCapacity(newCapacity: Int): Unit

  /** Sets the new `capacity` value equals to the current `size`.
    */
  def trimToSize(): Unit

  /** Returns `true` whether the current data structure is empty.
    * @return `true` if empty; `false` otherwise
    */
  def isEmpty: Boolean = size == 0

  /** Returns `true` whether the current data structure is non empty.
    * @return `true` if not empty; `false` otherwise
    */
  def nonEmpty: Boolean = size != 0

  /** Apply the function `f` to all array list elements, for its side-effects.
    * @param f the function to apply
    * @tparam U
    */
  def foreach[U](f: A => U): Unit

  /** Apply a function from right to left, with the given starting value `z`.
    * @param z the initial value
    * @param f the function to apply
    * @tparam B the resulting value type
    * @return the result
    */
  def foldRight[B](z: B)(f: (A, B) => B): B

  /** Apply a function from left to right, with the given starting value `z`.
    * @param z the initial value
    * @param f the function to apply
    * @tparam B the resulting value type
    * @return the result
    */
  def foldLeft[B](z: B)(f: (B, A) => B): B

  /** Remove all the element from this data structure.
    * The implementation can shrink back to the initial capacity.
    * @param default the generator for default values
    */
  def clear(implicit default: Default[A]): Unit

  /** Returns a string representation for the array list elements.
    * @param sep the string separator
    * @param start the prefix for the resulting string
    * @param end the suffix for the resulting string
    * @return the string for the array list elements
    */
  def mkString(sep: String, start: String = "", end: String = ""): String
}