/*
 *                       __                  __
 *      ______________ _/ /___ _      ____ _/ /___ _____
 *     / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *    (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 *   /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                         /____/
 *  Copyright (c) 2016 the original author or authors.
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
package io.github.carlomicieli.fp.typeclasses

import scala.language.implicitConversions
import scala.annotation.implicitNotFound

/**
  * It represents a basic type class for numeric types.
  */
@implicitNotFound("The type ${A} was not made instance of the Num type class")
trait Num[A] {

  val zero: A
  val one: A

  /**
    * The sum operation
    * @param x the first operand
    * @param y the second operand
    * @return the sum of `x` and `y`
    */
  def add(x: A, y: A): A

  /**
    * The subtraction operation
    * @param x the first operand
    * @param y the second operand
    * @return the subtraction of `x` and `y`
    */
  def sub(x: A, y: A): A

  /**
    * The multiplication operation
    * @param x the first operand
    * @param y the second operand
    * @return the multiplication of `x` and `y`
    */
  def mul(x: A, y: A): A

  /**
    * The unary negation
    * @param x the operand
    * @return x negated
    */
  def negate(x: A): A

  /**
    * Returns the absolute value
    * @param x the operand
    * @return the absolute value of `x`
    */
  def abs(x: A): A

  /**
    * Returns the sign of a number.
    *
    * The functions abs and signum should satisfy the law:
    * {{{
    *   abs x * signum x == x
    * }}}
    * For real numbers, the signum is either -1 (negative), 0 (zero) or 1 (positive).
    *
    * @param x the number
    * @return the sign of `x`
    */
  def signum(x: A): A
}

object Num {
  def apply[A](implicit n: Num[A]): Num[A] = n

  trait NumOps[A] {
    def self: A
    def numInstance: Num[A]
    def +(other: A): A = numInstance.add(self, other)
    def -(other: A): A = numInstance.sub(self, other)
    def *(other: A): A = numInstance.mul(self, other)
    def abs: A = numInstance.abs(self)
    def negate: A = numInstance.negate(self)
    def signum: A = numInstance.signum(self)
  }

  object ops {
    implicit def toNumOps[A: Num](x: A): NumOps[A] = new NumOps[A] {
      override def self: A = x
      override def numInstance: Num[A] = implicitly[Num[A]]
    }
  }

  implicit val floatToNum: Num[Float] = numBuilder[Float](0.0f, 1.0f,
    {
      case 0.0        =>  0
      case x if x < 0 => -1
      case x if x > 0 =>  1
    }, _ + _, _ * _ , x => -x
  )

  implicit val doubleToNum: Num[Double] = numBuilder[Double](0.0, 1.0,
    {
      case 0.0        =>  0
      case x if x < 0 => -1
      case x if x > 0 =>  1
    }, _ + _, _ * _ , x => -x
  )

  implicit val intToNum: Num[Int] = numBuilder[Int](0, 1,
    {
      case 0          =>  0
      case x if x < 0 => -1
      case x if x > 0 =>  1
    }, _ + _, _ * _ , x => -x
  )

  implicit val longToNum: Num[Long] = numBuilder[Long](0L, 1L,
    {
      case 0          =>  0L
      case x if x < 0 => -1L
      case x if x > 0 =>  1L
    }, _ + _, _ * _ , x => -x
  )

  private def numBuilder[A](zeroValue: A,
                            oneValue: A,
                            signF: A => A,
                            addF: (A, A) => A,
                            mulF: (A, A) => A,
                            negF: A => A) = new Num[A] {
    override val zero = zeroValue
    override val one = oneValue
    override def mul(x: A, y: A): A = mulF(x, y)
    override def sub(x: A, y: A): A = add(x, negate(y))
    override def negate(x: A): A = negF(x)
    override def abs(x: A): A = mul(signum(x), x)
    override def signum(x: A): A = signF(x)
    override def add(x: A, y: A): A = addF(x, y)
  }
}

trait AdditionLaws {
  import Num.ops._

  def commutativityLaw[A: Num](a: A, b: A): Boolean = (a + b) == (b + a)
  def associativityLaw[A: Num](a: A, b: A, c: A): Boolean = ((a + b) + c) == (a + (b + c))

  def identityElement[A: Num](a: A): Boolean = {
    val id = implicitly[Num[A]].zero
    a + id == id + a
  }
}

