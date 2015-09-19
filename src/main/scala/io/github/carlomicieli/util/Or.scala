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

import io.github.carlomicieli.dst.immutable.List

/**
 * It represents a value that is one of two possible types, with one type being `Good[G]` and the other `Bad[B]`.
 * All the methods are biased on the `Good` type.
 *
 * The only way to extract the value encapsulated by a `Bad` is through pattern matching.
 *
 * @tparam G the `Good` element type
 * @tparam B the `Bad` element type
 */
sealed trait Or[+G, +B] {

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
  def get: G

  /**
   * Returns the contained value if `this` is a `Good[_]`; it returns
   * the provided `default` otherwise.
   *
   * @usecase def getOrElse(default: => G): G
   * @inheritdoc
   * @param default the default value
   * @tparam G1 the `Good` element type
   * @return the `Good` value; or the `default` otherwise
   */
  def getOrElse[G1 >: G](default: => G1): G1 = if (isGood) get else default

  /**
   * Returns `true` if this `Or` is a `Good` and the predicate `p` returns
   * `true` when applied to this `Good`'s value.
   *
   * @param p the predicate to match
   * @return the result of applying the passed predicate `p` to the `Good` value, if this is a `Good`; `false` otherwise
   */
  def exists(p: G => Boolean): Boolean = if (isGood) p(get) else false

  /**
   * Applies the given function `f` to the contained value if `this` is a `Good[_]`;
   * does nothing if `this` is a `Bad`.
   *
   * @usecase def foreach(f: G => Unit): Unit
   * @inheritdoc
   * @param f the function to apply
   * @tparam U
   */
  def foreach[U](f: G => U): Unit = if (isGood) {
    val res = f(get)
  } else ()

  /**
   * Maps the given function to this `Or`'s value if it is a `Good` or returns this if it is a `Bad`.
   * @param f the function to apply
   * @tparam G1 the resulting value type
   * @return if this is a `Good`, the result of applying the given function to the contained value
   *         wrapped in a `Good`; a `Bad` otherwise
   */
  def map[G1](f: G => G1): Or[G1, B] = this match {
    case Bad(_)  => this.asInstanceOf[Or[G1, B]]
    case Good(v) => Good(f(v))
  }

  /**
   * Returns the given function applied to the value contained in this `Or` if it is a `Good`,
   * or returns this if it is a `Bad`.
   *
   * @usecase def flatMap[G1](f: G => Or[G1, B]): Or[G1, B]
   * @inheritdoc
   * @param f the function to apply
   * @tparam G1 the good type
   * @tparam B1 the bad type
   * @return if this is a `Good`, the result of applying the given function to the contained value
   *         wrapped in a `Good`; a `Bad` otherwise
   */
  def flatMap[G1, B1 >: B](f: G => Or[G1, B1]): Or[G1, B1] = this match {
    case Bad(_)  => this.asInstanceOf[Or[G1, B1]]
    case Good(v) => f(v)
  }

  /**
   * It accumulate two `Or`s together. If both are `Good`, you'll get a `Good` tuple containing
   * both original `Good` values. Otherwise, you'll get a `Bad` containing a tuple with the
   * original `Bad` values.
   *
   * @param that other `Or` value
   * @tparam G1 the other `Good` type
   * @tparam B1 the other `Bad` type
   * @return a new `Or` value combining the original values
   */
  def zip[G1, B1 >: B](that: Or[G1, B1]): (G, G1) Or List[B1] = {
    (this, that) match {
      case (Good(a), Good(b)) => Good((a, b))
      case (Bad(a), Bad(b)) => Bad(List(a, b))
      case (Bad(x), _) => Bad(List(x))
      case (_, Bad(y)) => Bad(List(y))
    }
  }

  /**
   * Returns `this` value if this is a `Good[_]`; produce a new `Bad` value
   * using the given value `v` otherwise.
   *
   * @usecase def orElse(v: => B): Or[G, B]
   * @inheritdoc
   * @param v the value used to produce a new `Bad`
   * @tparam B1 the new `Bad` element type
   * @return if this is a `Good`, the result will be the current `Or` value; a new `Bad`
   *         containing `v` otherwise
   */
  def orElse[B1](v: => B1): Or[G, B1] =
    if (isGood)
      this.asInstanceOf[Or[G, B1]]
    else
      Bad(v)

  /**
   * Returns an `Or` with the `Good` and `Bad` types swapped: `Bad` becomes `Good` and `Good` becomes `Bad`.
   * @return a swapped `Or` value
   */
  def swap: Or[B, G] = this match {
    case Good(g) => Bad(g)
    case Bad(b) => Good(b)
  }

  /**
   * Returns a `Just` value with the element contained in a `Good`;
   * it simply return `None` otherwise.
   *
   * @usecase def toMaybe: Maybe[G]
   * @inheritdoc
   * @tparam G1 the element type
   * @return a `Just` if this value is good; a `None` otherwise
   */
  def toMaybe[G1 >: G]: Maybe[G1] =
    if (isGood)
      Just(get)
    else
      None
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