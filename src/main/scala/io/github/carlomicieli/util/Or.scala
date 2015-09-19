/*
 *                       __                  __
 *      ______________ _/ /___ _      ____ _/ /___ _____
 *     / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *    (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 *   /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                         /____/
 *  Copyright (c) 2015 the original author or authors.
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
package io.github.carlomicieli.util

/**
 * It represents a value that is one of two possible types, with one type being `Good[A]` and the other `Bad[B]`.
 * All the methods are biased on the `Good` type.
 *
 * The only way to extract the value encapsulated by a `Bad` is through pattern matching.
 *
 * @tparam A the `Good` element type
 * @tparam B the `Bad` element type
 */
sealed trait Or[+A, +B] {

  /**
   * Returns `true` if `this` is a `Good[_]` value; `false` otherwise.
   * @return `true` if `this` is a `Good[_]` value; `false` otherwise
   */
  def isBad: Boolean

  /**
   * Returns `true` if `this` is a `Bad[_]` value; `false` otherwise.
   * @return `true` if `this` is a `Bad[_]` value; `false` otherwise
   */
  def isGood: Boolean

  /**
   * Returns the value contained if `this` is a `Good[_]` value; it
   * throws a `NoSuchElementException` otherwise.
   * @return the value contained
   */
  def get: A

  /**
   *
   * @usecase def getOrElse(default: => A): A
   * @inheritdoc
   * @param default the default value
   * @tparam A1 the `Good` element type
   * @return
   */
  def getOrElse[A1 >: A](default: => A1): A1 = ???

  /**
   *
   * @usecase def foreach(f: A => Unit): Unit
   * @inheritdoc
   * @param f
   * @tparam U
   */
  def foreach[U](f: A => U): Unit = ???

  /**
   *
   * @param f
   * @tparam C
   * @return
   */
  def map[C](f: A => C): Or[C, B] = this match {
    case Bad(_)  => this.asInstanceOf[Or[C, B]]
    case Good(v) => Good(f(v))
  }

  /**
   *
   * @param f
   * @tparam C
   * @tparam D
   * @return
   */
  def flatMap[C, D >: B](f: A => Or[C, D]): Or[C, D] = this match {
    case Bad(_)  => this.asInstanceOf[Or[C, D]]
    case Good(v) => f(v)
  }

  /**
   * Returns `this` value if this is a `Good[_]`; produce a new `Bad` value
   * using the given value `v` otherwise.
   *
   * @param v the value used to produce a new `Bad`
   * @tparam D the new `Bad` element type
   * @return
   */
  def orElse[D](v: => D): Or[A, D] =
    if (isGood)
      this.asInstanceOf[Or[A, D]]
    else
      Bad(v)

  /**
   * Returns a `Just` value with the element contained in a `Good`;
   * it simply return `None` otherwise.
   *
   * @usecase def toMaybe: Maybe[A]
   * @inheritdoc
   * @tparam A1
   * @return
   */
  def toMaybe[A1 >: A]: Maybe[A1] = ???
}

object Or {
  def safeOp[A](op: => A): Or[A, Throwable] = {
    try {
      val res = op
      Good(res)
    } catch {
      case ex: Throwable => Bad(ex)
    }
  }
}

case class Good[A, B](value: A) extends Or[A, B] {
  def isBad = false
  def isGood = true
  def get = value
}

case class Bad[A, B](value: B)  extends Or[A, B] {
  def isBad = true
  def isGood = false
  def get: A = throw new NoSuchElementException("Or.get: is empty")
}