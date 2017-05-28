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

package io.github.carlomicieli.fp.dst

/** It represents a "lazy" list where `head` and `tail` are not evaluated eagerly.
  * @tparam A the element type
  */
sealed trait LazyList[+A] {
  def head: A

  def headOption: Maybe[A] = this match {
    case Empty          => None
    case LazyCons(h, _) => Just(h())
  }

  def tail: LazyList[A]

  def isEmpty: Boolean

  def size: Int = this match {
    case Empty          => 0
    case LazyCons(_, t) => 1 + t().size
  }

  def ++[B >: A](that: LazyList[B]): LazyList[B] =
    foldRight(that)((a, b) => LazyCons(() => a, () => b))

  def map[B](f: A => B): LazyList[B] =
    foldRight(LazyList.empty[B])((a, b) => LazyList.cons(f(a), b))

  def flatMap[B](f: A => LazyList[B]): LazyList[B] = ???

  def take(n: Int): LazyList[A] = (this, n) match {
    case (Empty, _)          => Empty
    case (_, 0)              => Empty
    case (LazyCons(h, t), i) => LazyCons(h, () => t().take(n - 1))
  }

  def drop(n: Int): LazyList[A] = (this, n) match {
    case (Empty, _)          => Empty
    case (st, 0)             => st
    case (LazyCons(_, t), i) => t().drop(n - 1)
  }

  def find(p: A => Boolean): Maybe[A] =
    filter(p).headOption

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b)

  def filter(p: A => Boolean): LazyList[A] = {
    this match {
      case Empty => Empty
      case LazyCons(h, t) =>
        lazy val head = h()
        if (p(head))
          LazyCons[A](h, () => t().filter(p))
        else
          t().filter(p)
    }
  }

  def nonFilter(p: A => Boolean): LazyList[A] = ???

  def foldRight[B](z: B)(f: (A, B) => B): B = {
    this match {
      case LazyCons(h, t) => f(h(), t().foldRight(z)(f))
      case Empty          => z
    }
  }

  def foldLeft[B](z: B)(f: (B, A) => B): B = ???

  def zip[B](that: LazyList[B]): LazyList[(A, B)] = (this, that) match {
    case (Empty, _) => Empty
    case (_, Empty) => Empty
    case (LazyCons(ah, at), LazyCons(bh, bt)) =>
      LazyList.cons((ah(), bh()), at() zip bt())
  }

  def toList: List[A] = this match {
    case Empty => List.empty[A]
    case LazyCons(h, t) =>
      lazy val head = h()
      lazy val tail = t()
      head +: tail.toList
  }
}

object LazyList {
  def empty[A]: LazyList[A] = Empty

  def cons[A](hd: => A, tl: => LazyList[A]): LazyList[A] = {
    lazy val head = hd
    lazy val tail = tl
    LazyCons(() => head, () => tail)
  }
}

case class LazyCons[+A](h: () => A, t: () => LazyList[A]) extends LazyList[A] {
  var isEmpty = false

  lazy val head: A = h()
  lazy val tail: LazyList[A] = t()
}

case object Empty extends LazyList[Nothing] {
  val isEmpty = true

  override def head = throw new NoSuchElementException("LazyList.head: is empty")
  override def tail = throw new NoSuchElementException("LazyList.head: is empty")
}