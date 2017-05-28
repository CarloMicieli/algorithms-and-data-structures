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

/** A "skew heap" (a representation of priority queues) based upon Chris Okasaki's implementation.
  * Insert operation based upon the John Hughes's implementation.
  *
  * @tparam A
  */
sealed trait SkewHeap[+A] {
  def get: A

  def isEmpty: Boolean

  def credits: Int = this match {
    case EmptyHeap => 0
    case h @ Fork(_, l, r) =>
      l.credits + r.credits + (if (h.isGood) 0 else 1)
  }

  def isGood: Boolean = this match {
    case Fork(_, l, r) => l.weight <= r.weight
    case _             => throw new NoSuchElementException("SkewHeap.isGood: tree is empty")
  }

  def min: Maybe[A] = this match {
    case Fork(x, _, _) => Just(x)
    case _             => None
  }

  def removeMin[A1 >: A](implicit ord: Ordering[A1]): SkewHeap[A1] = {
    this match {
      case EmptyHeap     => EmptyHeap
      case Fork(x, l, r) => l.merge[A1](r)
    }
  }

  def merge[A1 >: A](that: SkewHeap[A1])(implicit ord: Ordering[A1]): SkewHeap[A1] = {
    (this, that) match {
      case (l, EmptyHeap) => l
      case (EmptyHeap, r) => r
      case (l, r) =>
        if (ord.lteq(l.min.get, r.min.get))
          l join r
        else
          r join l
    }
  }

  def join[A1 >: A](that: SkewHeap[A1])(implicit ord: Ordering[A1]): SkewHeap[A1] = {
    (this, that) match {
      case (Fork(x, l, r), h) => Fork(x, r, l merge h)
      case _                  => throw new NoSuchElementException("SkewHeap.join: tree is empty")
    }
  }

  def insert[A1 >: A](x: A1)(implicit ord: Ordering[A1]): SkewHeap[A1] = this match {
    case EmptyHeap => Fork(x, SkewHeap.empty[A1], SkewHeap.empty[A1])
    case Fork(y, l, r) =>
      val (min, max) = minMax(x, y)
      Fork(min, r, l.insert(max))
  }

  def balanced: Boolean = this match {
    case EmptyHeap => true
    case Fork(_, l, r) =>
      val d = r.weight - l.weight
      (d == 0 || d == 1) && l.balanced && r.balanced
  }

  def weight: Int = this match {
    case EmptyHeap     => 0
    case Fork(_, l, r) => 1 + l.weight + r.weight
  }

  def minMax[A1 >: A](x: A1, y: A1)(implicit ord: Ordering[A1]): (A1, A1) = {
    import Ordered._
    if (x < y)
      (x, y)
    else
      (y, x)
  }

  def invariant[A1 >: A](implicit ord: Ordering[A1]): Boolean = this match {
    case EmptyHeap     => true
    case Fork(x, l, r) => smaller[A1](x, l) && smaller[A1](x, r)
  }

  def smaller[A1 >: A](x: A1, t: SkewHeap[A1])(implicit ord: Ordering[A1]): Boolean = {
    t match {
      case EmptyHeap => true
      case Fork(y, l, r) =>
        import Ordered._
        x <= y && t.invariant
    }
  }

  override def toString: String = this match {
    case EmptyHeap     => "Null"
    case Fork(x, l, r) => s"(Fork $x $l $r)"
  }
}

private[this] object SkewHeap {
  def empty[A]: SkewHeap[A] = EmptyHeap

  def fromList[A](xs: List[A])(implicit ev: Ordering[A]): SkewHeap[A] = {
    xs.foldLeft(SkewHeap.empty[A])((tree, x) => tree insert x)
  }
}

private[this] case class Fork[A](get: A, left: SkewHeap[A], right: SkewHeap[A]) extends SkewHeap[A] {
  def isEmpty = false
}

private[this] case object EmptyHeap extends SkewHeap[Nothing] {
  def isEmpty = true
  def get = throw new NoSuchElementException("SkewHeap.get: tree is empty")
}