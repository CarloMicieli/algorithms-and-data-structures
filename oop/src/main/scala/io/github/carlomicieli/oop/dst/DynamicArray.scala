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

import scala.reflect.ClassTag

/** It represents a resizable array.
  * @tparam A the element type
  */
trait DynamicArray[A] extends PartialFunction[Int, A] {
  /** Updates the element at index `i` with the given value `v`.
    * @param i the element index
    * @param v the new value
    */
  def update(i: Int, v: A): Unit

  /** Returns the element at index `i` in this array.
    * @param i the element index
    * @return the element at index `i`
    */
  def apply(i: Int): A

  /** Returns the max number of elements allowed in `this` array.
    * @return the max number of elements
    */
  def size: Int

  /** Grows this array, according to the default ratio (ie 3/2).
    * @return a new array
    */
  def expand: DynamicArray[A]

  /** Shrinks this array, according to the default ratio (ie 2/3).
    * @return a new array
    */
  def shrink: DynamicArray[A]

  /** Shifts the array elements by `step` position, starting at index `start`. Elements that will be out of bounds
    * after this operation are discarded from the array
    * @param step the number of shift
    * @param start the starting index
    */
  def shift(step: Int, start: Int): Unit

  /** Inserts the new element before the first index that doesn't match the predicate, sliding the remaining elements.
    * @param el the element to be added
    * @param p the predicate to find the insertion point
    * @return `true` if any element has been inserted; `false` otherwise
    */
  def insert(el: A)(p: (A, A) => Boolean): Boolean

  /** Swap the elements at index `i` and `j`, if those indexes are different.
    * @param i the first index
    * @param j the second index
    */
  def swap(i: Int, j: Int): Unit

  /** Returns an iterator for the array elements.
    * @return an iterator
    */
  def elements: Iterable[A]
}

object DynamicArray {
  /** Creates a new `DynamicArray` with the provided elements.
    * @param el the first element
    * @param items the rest of the elements
    * @tparam A the element type
    * @return a new `DynamicArray`
    */
  def apply[A: ClassTag](el: A, items: A*): DynamicArray[A] = {
    val arr = DynamicArray.empty[A](items.length + 1)
    arr(0) = el
    for (i <- 0 until items.length) {
      arr(i + 1) = items(i)
    }
    arr
  }

  /** Creates a new empty `DynamicArray`.
    * @param size the number of elements
    * @tparam A the element type
    * @return an empty array
    */
  def empty[A: ClassTag](size: Int): DynamicArray[A] = {
    val st = new Array[A](size)
    new DynamicArrayImpl[A](st)
  }

  private class DynamicArrayImpl[A: ClassTag](private val storage: Array[A]) extends DynamicArray[A] {
    private val ResizeRatio: Double = 3.0 / 2

    override def update(i: Int, v: A): Unit = { storage(i) = v }

    override def shrink: DynamicArray[A] = resize(2.0 / 3)

    override def expand: DynamicArray[A] = resize(ResizeRatio)

    override def swap(i: Int, j: Int): Unit = {
      if (i != j) {
        val tmp = storage(i)
        storage(i) = storage(j)
        storage(j) = tmp
      }
    }

    override def insert(el: A)(p: (A, A) => Boolean): Boolean = {
      @annotation.tailrec
      def loop(i: Int): Option[Int] = {
        if (i >= size) None
        else {
          if (p(el, storage(i))) Some(i) else loop(i + 1)
        }
      }

      loop(0).map { i =>
        shift(1, i)
        update(i, el)
        i
      }.isDefined
    }

    override def size: Int = storage.length

    override def elements: Iterable[A] = new Iterable[A] {
      def iterator: Iterator[A] = new Iterator[A] {
        private var curr = 0
        def hasNext: Boolean = curr < storage.length
        def next(): A = {
          val el = storage(curr)
          curr = curr + 1
          el
        }
      }
    }

    override def apply(i: Int): A = storage(i)

    override def shift(n: Int, s: Int): Unit = {
      for (i <- last downTo (s + n)) {
        storage(i) = storage(i - n)
      }
    }

    override def isDefinedAt(x: Int): Boolean = x >= 0 && x < size

    override def toString() = {
      val items = storage.mkString(", ")
      s"[$items]"
    }

    override def equals(o: Any): Boolean = o match {
      case that: DynamicArrayImpl[A] => this.storage sameElements that.storage
      case _                         => false
    }

    def resize(ratio: Double): DynamicArray[A] = {
      val newCapacity = (storage.length * ratio).toInt
      val newArr = DynamicArray.empty[A](newCapacity)
      for (i <- 0 until math.min(newCapacity, storage.length)) {
        newArr(i) = storage(i)
      }
      newArr
    }

    private def last: Int = storage.length - 1
  }
}