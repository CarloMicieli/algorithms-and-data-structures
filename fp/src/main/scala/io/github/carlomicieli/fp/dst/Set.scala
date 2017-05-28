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

package io.github.carlomicieli.fp.dst

/** @tparam A
  */
trait Set[+A] {
  /** Returns the union of sets this and that.
    * @param that the second set
    * @return a new set union of this and that
    */
  def union[B >: A](that: Set[B]): Set[B]

  /** Returns the intersection of sets S and T.
    */
  def intersection[B >: A](that: Set[B]): Set[B]

  /** returns the difference of sets S and T.
    */
  def difference[B >: A](that: Set[B]): Set[B]

  /** Checks whether this set is a subset of set that.
    */
  def isSubset[B >: A](that: Set[B]): Boolean

  /** Checks whether this set is a proper subset of set that.
    */
  def isProperSubsetOf[B >: A](that: Set[B]): Boolean

  /** _O(log n)_. The expression (split x set) is a pair (set1,set2) where set1 comprises the
    * elements of set less than x and set2 comprises the elements of set greater than x.
    */
  def split[B >: A](x: B)(implicit ord: Ordering[B]): (Set[B], Set[B])

  /** Returns the subset containing all elements of this set that satisfy a given predicate p.
    */
  def filter(p: A => Boolean): Set[A]

  /** `O(n * log n)`. Returns the set of distinct values resulting from applying function f
    * to each element of this set.
    */
  def map[B](f: A => B): Set[B]

  /** `O(log n)`. Checks whether x is an element of this set.
    */
  def contains[B >: A](x: B): Boolean

  /** `O(log n)`. Return the index of an element, which is its zero-based index
    * in the sorted sequence of elements. The index is a number from 0 up to,
    * but not including, the size of the set.
    */
  def findIndex[B >: A](x: B): Maybe[Int]

  /** `O(1)`. Checks whether this Set is the empty set.
    */
  def isEmpty: Boolean

  /** `O(n)`. Returns the number of elements in S.
    */
  def size: Int

  def foreach[U](f: A => U): Unit

  /** `O(n)`. Convert the set to a list of elements.
    */
  def toList: List[A]
}

trait DynamicSet[+A] extends Set[A] {
  /** `O(log n)`. Add the element x to this Set, if it is not present already.
    */
  def add[B >: A](x: B): DynamicSet[B]

  /** `O(log n)`. Removes the element x from S, if it is present.
    */
  def remove[B >: A](x: B): (Maybe[B], DynamicSet[B])

  /** `O(1)`. Returns the maximum number of values that this set can hold.
    */
  def capacity: Int
}

object Set {
  /** `O(1)`. Create a singleton set.
    */
  def singleton[A](x: A): Set[A] = ???

  /** `O(n*log n)`. Create a set structure with values from `items`.
    */
  def apply[A](items: A*): Set[A] = ???

  /** `O(n*log n)`. Create a set from a list of elements.
    */
  def fromList[A](xs: List[A]): Set[A] = ???

  /** Create a new, initially empty set structure.
    */
  def empty[A]: DynamicSet[A] = ???

  /** Create a new set structure, initially empty but capable of holding up to n elements.
    */
  def withCapacity[A](n: Int): DynamicSet[A] = ???
}