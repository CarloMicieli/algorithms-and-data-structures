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

sealed trait Maybe[+A] {
  def get: A

  /**
   * Return the contained value if `this` is a `Good` value,
   * otherwise returns the `default` value.
   * @param default the value to be returned when `this` is a `Bad` value
   * @tparam A1
   * @return
   */
  def getOrElse[A1 >: A](default: => A1): A1

  /**
   * It returns the first Option if it’s defined; otherwise, it returns
   * the second Option.
   * @param ob
   * @tparam A1
   * @return
   */
  def orElse[A1 >: A](ob: => Maybe[A1]): Maybe[A1]

  def isDefined: Boolean

  /**
   * Apply the function `f` if `this` is a `Good` value, it doesn't
   * do anything otherwise.
   *
   * @param f
   * @tparam U
   */
  def forEach[U](f: A => U): Unit

  /**
   * Returns a `Good` containing the result of applying `f` to this
   * `Or`'s value if this `Or` is a `Good` value.
   * @param f
   * @tparam A1
   * @return
   */
  def map[A1](f: A => A1): Maybe[A1]

  /**
   * Returns the result of applying `f` to this `Or`'s value if this
   * `Or` is a `Good` value.
   * @param f
   * @tparam A1
   * @return
   */
  def flatMap[A1](f: A => Maybe[A1]): Maybe[A1]

  def filter(p: A => Boolean): Maybe[A]

  def toGood[B](bad: => B): Or[A, B]
}

case class Just[A](get: A) extends Maybe[A] {
  def toGood[B](bad: => B): Or[A, B] = ???

  def getOrElse[A1 >: A](default: => A1): A1 = ???

  def filter(p: (A) => Boolean): Maybe[A] = ???

  def forEach[U](f: (A) => U): Unit = ???

  def isDefined: Boolean = ???

  def orElse[A1 >: A](ob: => Maybe[A1]): Maybe[A1] = ???

  def map[A1](f: (A) => A1): Maybe[A1] = ???

  def flatMap[A1](f: (A) => Maybe[A1]): Maybe[A1] = ???
}

case object None extends Maybe[Nothing] {
  def get = throw new NoSuchElementException("Maybe.get: a value doesn't exist")

  def toGood[B](bad: => B): Or[Nothing, B] = ???

  def getOrElse[A1 >: Nothing](default: => A1): A1 = ???

  def flatMap[A1](f: (Nothing) => Maybe[A1]): Maybe[A1] = ???

  def forEach[U](f: (Nothing) => U): Unit = ???

  def filter(p: (Nothing) => Boolean): Maybe[Nothing] = ???

  def isDefined: Boolean = ???

  def orElse[A1 >: Nothing](ob: => Maybe[A1]): Maybe[A1] = ???

  def map[A1](f: (Nothing) => A1): Maybe[A1] = ???
}
