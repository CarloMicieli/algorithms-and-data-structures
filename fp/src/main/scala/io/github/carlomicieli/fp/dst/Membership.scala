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

package io.github.carlomicieli.fp.dst

/** It represents an ''intensional'' set, where objects belongs through some sort of membership function.
  *
  * {{{
  * scala> val oddNumbers = Membership[Int] { _ % 2 != 0 }
  * oddNumbers: io.github.carlomicieli.fp.dst.Membership[Int] = <membership>
  *
  * scala> oddNumbers contains 42
  * res0: Boolean = false
  *
  * scala> oddNumbers contains 21
  * res1: Boolean = true
  *
  * scala> val positiveNumbers = Membership[Int] { _ > 0 }
  * positiveNumbers: io.github.carlomicieli.fp.dst.Membership[Int] = <membership>
  *
  * scala> val positiveAndOddNumbers = positiveNumbers intersection oddNumbers
  * positiveAndOddNumbers: io.github.carlomicieli.fp.dst.Membership[Int] = <membership>
  *
  * scala> positiveAndOddNumbers contains 41
  * res2: Boolean = true
  *
  * scala> positiveAndOddNumbers contains -41
  * res3: Boolean = false
  * }}}
  *
  * @param fun the membership function
  * @tparam A the element type
  */
class Membership[-A] private (fun: A => Boolean) {

  /** Returns the negation for this set.
    * @return the negated set
    */
  def negate: Membership[A] = {
    Membership { (x: A) => !this(x) }
  }

  /** Returns the ''complement'' of `that` in `this`,  is the set of all elements that are members
    * of `this` but not members of `that`.
    *
    * @usecase def complement(that: Membership[A]): Membership[A]
    * @inheritdoc
    * @param that the second set
    * @tparam B the element type
    * @return the complement of `this` and `that`
    */
  def complement[B <: A](that: Membership[B]): Membership[B] = {
    Membership { (x: B) => this(x) && !that(x) }
  }

  /** Returns the ''intersection'' of `this` and `that`. A new set can also be constructed by determining which
    * members two sets have ''in common''.
    *
    * @param that the second set
    * @tparam B the element type
    * @return the intersection of `this` and `that`
    */
  def intersection[B <: A](that: Membership[B]): Membership[B] = {
    Membership { (x: B) => this(x) && that(x) }
  }

  /** Returns the ''union'' of `this` and `that`, is the set of all things that are
    * members of either `this` or `that`.
    *
    * @usecase def union(that: Membership[A]): Membership[A]
    * @inheritdoc
    * @param that the second set
    * @tparam B the element type
    * @return the union of `this` and `that`
    */
  def union[B <: A](that: Membership[B]): Membership[B] = {
    Membership { (x: B) => this(x) || that(x) }
  }

  /** Checks whether this Set contains the given element `x`
    * @param x the element to check
    * @return `true` if `x` belongs to this Set; `false` otherwise
    */
  def contains(x: A): Boolean = fun(x)

  /** Returns `true` if the element `x` belongs to this set.
    * @param x the element to check
    * @return `true` if `x` belongs to this Set; `false` otherwise
    */
  def apply(x: A): Boolean = this contains x

  override def toString: String = "<membership>"
}

object Membership {

  /** Creates a new ''intensional'' set, with the given membership function.
    * @param f the membership function
    * @tparam A the element type
    * @return a new ''intensional'' set
    */
  def apply[A](f: A => Boolean): Membership[A] = new Membership[A](f)
}