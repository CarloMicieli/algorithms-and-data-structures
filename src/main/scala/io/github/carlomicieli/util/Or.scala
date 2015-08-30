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

sealed trait Or[+A, +B] {
  def isBad: Boolean
  def isGood: Boolean

  def get: A

  def map[C](f: A => C): Or[C, B] = this match {
    case Bad(_)  => this.asInstanceOf[Or[C, B]]
    case Good(v) => Good(f(v))
  }

  def flatMap[C, D >: B](f: A => Or[C, D]): Or[C, D] = this match {
    case Bad(_)  => this.asInstanceOf[Or[C, D]]
    case Good(v) => f(v)
  }

  def orElse[D](v: => D): Or[A, D] =
    if (isGood)
      this.asInstanceOf[Or[A, D]]
    else
      Bad(v)
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