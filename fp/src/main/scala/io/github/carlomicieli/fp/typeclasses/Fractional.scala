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

/**
  * It represents the type class for fractional numbers, supporting real division.
  */
trait Fractional[A] {
  def div(x: A, y: A): A
}

object Fractional {
  def apply[A](implicit fr: Fractional[A]): Fractional[A] = fr

  trait FractionalOps[A] {
    def self: A
    def fractionalInstance: Fractional[A]

    def /(other: A): A = fractionalInstance.div(self, other)
  }

  object ops {
    implicit def toFractionalOps[A](x: A)(implicit fr: Fractional[A]): FractionalOps[A] = new FractionalOps[A] {
      override def self: A = x
      override def fractionalInstance: Fractional[A] = fr
    }
  }

  implicit val floatToFractional: Fractional[Float] = new Fractional[Float] {
    override def div(x: Float, y: Float): Float = x / y
  }

  implicit val doubleToFractional: Fractional[Double] = new Fractional[Double] {
    override def div(x: Double, y: Double): Double = x / y
  }
}

