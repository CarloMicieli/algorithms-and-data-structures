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
  * The Ord type class is used for totally ordered data types.
  * @tparam A
  */
@implicitNotFound("The type ${A} was not made an instance of the Ord type class")
trait Ord[A] {
  def compare(x: A, y: A): Ordering
}

object Ord {
  def apply[A](implicit o: Ord[A]): Ord[A] = o

  trait OrdOps[A] {
    def self: A
    def ordInstance: Ord[A]

    def ===(that: A): Boolean = ordInstance.compare(self, that) == Ordering.EQ
    def =/=(that: A): Boolean = ordInstance.compare(self, that) != Ordering.EQ
    def <(that: A): Boolean = ordInstance.compare(self, that) == Ordering.LT
    def <=(that: A): Boolean = {
      val res = ordInstance.compare(self, that)
      res == Ordering.LT || res == Ordering.EQ
    }

    def >(that: A): Boolean = ordInstance.compare(self, that) == Ordering.GT
    def >=(that: A): Boolean = {
      val res = ordInstance.compare(self, that)
      res == Ordering.GT || res == Ordering.EQ
    }
  }

  object ops {
    implicit def toOrdOps[A: Ord](x: A): OrdOps[A] = new OrdOps[A] {
      override def self: A = x
      override def ordInstance: Ord[A] = implicitly[Ord[A]]
    }
  }

  implicit val booleanOrd: Ord[Boolean] = ordInstance[Boolean]((x, y) => x compare y)
  implicit val intOrd: Ord[Int] = ordInstance[Int]((x, y) => x compare y)
  implicit val stringOrd: Ord[String] = ordInstance[String]((x, y) => x compare y)

  private def ordInstance[A](cmp: (A, A) => Int): Ord[A] = new Ord[A] {
      override def compare(x: A, y: A) = Ordering(cmp)(x, y)
  }
}

/**
  *
  *  - If x ≤ y and y ≤ x then x = y (antisymmetry);
  *  - If x ≤ y and y ≤ z then x ≤ z (transitivity);
  *  - x ≤ y or y ≤ z (totality).
  */
trait OrdLaws {
  import Ord.ops._

  def antisymmetryLaw[A: Ord](x: A, y: A): Boolean = {
    if (x <= y && y <= x) x === y else true
  }

  def transitivityLaw[A: Ord](x: A, y: A, z: A): Boolean = {
    if (x <= y && y <= z) x <= z else true
  }

  def totalityLaw[A: Ord](x: A, y: A): Boolean = {
    x <= y || y <= x
  }
}

sealed trait Ordering

object Ordering {
  case object LT extends Ordering
  case object EQ extends Ordering
  case object GT extends Ordering

  def apply[A](cmp: (A, A) => Int)(x: A, y: A): Ordering = {
    cmp(x, y) match {
      case 0          => Ordering.EQ
      case n if n < 0 => Ordering.LT
      case n if n > 0 => Ordering.GT
    }
  }
}

