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

package io.github.carlomicieli.fp.typeclasses

import scala.annotation.implicitNotFound

/** The `Enum` type class is used to name the upper and lower limits of a type. It also defines
  * operations on sequentially ordered types.
  *
  * For any type that is an instance of type class `Enum`, the following should hold:
  * - `fromEnum` and `toEnum` should returns `None` if the result value is not representable in the result type.
  * - the calls `next maxBound` and `prev minBound` should result in a `None` value;
  *
  * @tparam A the element type
  */
@implicitNotFound("The type ${A} was not made an instance of the Enum type class.")
trait Enum[A] {
  /** Convert from an `Int` to the corresponding `A` value.
    *
    * @param x the element id
    * @return `Some(el)` if the input is valid, `None` otherwise
    */
  def toEnum(x: Int): Option[A]

  /** Convert from an `A` value to the corresponding `Int`.
    * @param x the element to convert
    * @return the corresponding element id
    */
  def fromEnum(x: A): Int

  /** Returns the successor of a value.
    * @param x the current element
    * @return `Some(el)` if the element has a successor, `None` otherwise.
    */
  def next(x: A): Option[A] = toEnum(fromEnum(x) + 1)

  /** Returns the predecessor of a value.
    * @param x the current element
    * @return `Some(el)` if the element has a predecessor, `None` otherwise.
    */
  def prev(x: A): Option[A] = toEnum(fromEnum(x) - 1)

  /** Returns the lower limit for the type `A`.
    * @return the lower limit
    */
  def minBound: A

  /** Returns the upper limit for the type `A`.
    * @return the upper limit
    */
  def maxBound: A

  /** The element ids range.
    */
  val range: Range = fromEnum(minBound) to fromEnum(maxBound)
}

object Enum {
  def apply[A](implicit e: Enum[A]): Enum[A] = e

  implicit val intEnum: Enum[Int] = new Enum[Int] {
    override def minBound: Int = Int.MinValue
    override def maxBound: Int = Int.MaxValue
    override def toEnum(x: Int): Option[Int] = Some(x)
    override def fromEnum(x: Int): Int = x
  }

  implicit val charEnum: Enum[Char] = new Enum[Char] {
    override def minBound: Char = Char.MinValue
    override def maxBound: Char = Char.MaxValue
    override def fromEnum(x: Char): Int = x.toInt
    override def toEnum(x: Int): Option[Char] = if (range.contains(x)) Some(x.toChar) else None
  }
}