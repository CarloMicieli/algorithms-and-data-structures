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

package io.github.carlomicieli.fp.typeclasses

import scala.annotation.implicitNotFound
import scala.language.implicitConversions

/** Eq is used for types that support equality and inequality testing.
  * The functions its members implement are `eq` and `neq`.
  *
  * @tparam A
  */
@implicitNotFound("The type ${A} was not made an instance of the Eq type class")
trait Eq[A] {
  /** Checks whether lhs and rhs are equals
    * @param lhs the first value
    * @param rhs the second value
    * @return `true` if they are equals; `false` otherwise
    */
  def eq(lhs: A, rhs: A): Boolean

  /** Checks whether lhs and rhs are different
    * @param lhs the first value
    * @param rhs the second value
    * @return `true` if they are equals; `false` otherwise
    */
  def neq(lhs: A, rhs: A): Boolean = !eq(lhs, rhs)
}

object Eq {
  def apply[A](implicit ev: Eq[A]) = ev

  trait EqOps[A] {
    def self: A
    def eqInstance: Eq[A]
    def ===(that: A): Boolean = eqInstance.eq(self, that)
    def =/=(that: A): Boolean = eqInstance.neq(self, that)
  }

  object ops {
    implicit def toEqOp[A](x: A)(implicit ev: Eq[A]) = new EqOps[A] {
      override def self: A = x
      override def eqInstance: Eq[A] = ev
    }
  }

  implicit def optionToEq[A: Eq]: Eq[Option[A]] = new Eq[Option[A]] {
    override def eq(lhs: Option[A], rhs: Option[A]): Boolean = lhs.equals(rhs)
  }

  implicit val floatEq: Eq[Float] = new Eq[Float] {
    override def eq(lhs: Float, rhs: Float): Boolean = lhs.equals(rhs)
  }

  implicit val doubleEq: Eq[Double] = new Eq[Double] {
    override def eq(lhs: Double, rhs: Double): Boolean = lhs.equals(rhs)
  }

  implicit val shortEq: Eq[Short] = new Eq[Short] {
    override def eq(lhs: Short, rhs: Short): Boolean = lhs.equals(rhs)
  }

  implicit val charEq: Eq[Char] = new Eq[Char] {
    override def eq(lhs: Char, rhs: Char): Boolean = lhs.equals(rhs)
  }

  implicit val stringEq: Eq[String] = new Eq[String] {
    override def eq(lhs: String, rhs: String): Boolean = lhs.equals(rhs)
  }

  implicit val booleanEq: Eq[Boolean] = new Eq[Boolean] {
    override def eq(lhs: Boolean, rhs: Boolean): Boolean = lhs.equals(rhs)
  }

  implicit val intEq: Eq[Int] = new Eq[Int] {
    override def eq(lhs: Int, rhs: Int): Boolean = lhs == rhs
  }

  implicit val byteEq: Eq[Byte] = new Eq[Byte] {
    override def eq(lhs: Byte, rhs: Byte): Boolean = lhs == rhs
  }

  implicit val longEq: Eq[Long] = new Eq[Long] {
    override def eq(lhs: Long, rhs: Long): Boolean = lhs == rhs
  }

  implicit val anyValEq: Eq[AnyVal] = new Eq[AnyVal] {
    override def eq(lhs: AnyVal, rhs: AnyVal): Boolean = lhs.equals(rhs)
  }

  implicit val anyEq: Eq[Any] = new Eq[Any] {
    override def eq(lhs: Any, rhs: Any): Boolean = lhs.equals(rhs)
  }
}

/** Laws Eq instances should have are the following:
  * - Reflexivity: x == x should always be True.
  * - Symmetry: x == y iff y == x.
  * - Transitivity: If x == y and y == z, then x == z.
  * - Substitution: If x == y, then f x == f y for all f.
  */
trait EqLaws {

  def reflexivityLaw[A: Eq](x: A): Boolean = Eq[A].eq(x, x)
  def symmetryLaw[A: Eq](x: A, y: A): Boolean = Eq[A].eq(x, y) == Eq[A].eq(y, x)
  def transitivityLaw[A: Eq](x: A, y: A, z: A): Boolean = if (Eq[A].eq(x, y) && Eq[A].eq(y, z)) Eq[A].eq(x, z) else true
  def substitutionLaw[A: Eq](x: A, y: A)(f: A => A): Boolean = Eq[A].eq(x, y) == Eq[A].eq(f(x), f(y))

}