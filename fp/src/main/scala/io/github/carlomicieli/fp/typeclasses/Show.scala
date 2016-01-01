/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
 *
 * Copyright (c) 2015 the original author or authors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.carlomicieli.fp.typeclasses

import scala.language.implicitConversions
import scala.annotation.implicitNotFound
import io.github.carlomicieli.fp.dst.List

/**
  * It represents the type class for conversion of values to readable Strings.
  * @tparam A
  */
@implicitNotFound("Type ${A} was not made an instance of the Show type class")
trait Show[A] {
  def show(x: A): String
  def showList(xs: List[A]): String = xs.map(show).mkString(", ", "[", "]")
}

object Show {
  def apply[A](implicit S: Show[A]): Show[A] = S

  trait ShowOps[A] {
    def self: A
    def showInstance: Show[A]
    def show: String = showInstance.show(self)
  }

  object ops {
    implicit def toShowOps[A: Show](x: A): ShowOps[A] = new ShowOps[A] {
      override def self: A = x
      override def showInstance: Show[A] = implicitly[Show[A]]
    }
  }

  implicit val booleanShow: Show[Boolean] = new Show[Boolean] {
    override def show(x: Boolean): String = x.toString
  }

  implicit val byteShow: Show[Byte] = new Show[Byte] {
    override def show(x: Byte): String = x.toString
  }

  implicit val intShow: Show[Int] = new Show[Int] {
    override def show(x: Int): String = x.toString
  }

  implicit val longShow: Show[Long] = new Show[Long] {
    override def show(x: Long): String = x.toString
  }

  implicit val floatShow: Show[Float] = new Show[Float] {
    override def show(x: Float): String = x.toString
  }

  implicit val doubleShow: Show[Double] = new Show[Double] {
    override def show(x: Double): String = x.toString
  }

  implicit val charShow: Show[Char] = new Show[Char] {
    override def show(x: Char): String = s"'$x'"
    override def showList(xs: List[Char]): String = xs.mkString("", "\"", "\"")
  }

  implicit val stringShow: Show[String] = new Show[String] {
    override def show(x: String): String = s""""$x""""
  }
}