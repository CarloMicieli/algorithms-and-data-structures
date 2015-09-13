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
package io.github.carlomicieli.dst.immutable

import io.github.carlomicieli.util.{Maybe, Just, None}

/**
 *
 * @tparam A the list element type
 */
sealed trait List[+A] {
  self =>

  /**
   * `O(1)` Returns the first element of a list.
   * @return the first element
   */
  def head: A

  /**
   * `O(1)` Optionally returns the first element of a list.
   * @return `Some(head)` if the list is not empty; `None` otherwise
   */
  def headOption: Maybe[A] = if (isEmpty) None else Just(head)

  /**
   * `O(1)` Returns the elements after the head of a list.
   * @return the elements after the head
   */
  def tail: List[A]

  /**
   * `O(1)` Checks whether this list is empty.
   * @return `true` if the list is empty; `false` otherwise
   */
  def isEmpty: Boolean

  /**
   * `O(1)` Checks whether this list is not empty.
   * @return `true` if the list is not empty; `false` otherwise
   */
  def nonEmpty: Boolean = !isEmpty

  /**
   * Adds an element at the beginning of this list.
   * @param x the element to add
   * @tparam A1 the list element type
   * @return a new list, with the element appended
   * @usecase def +:(x: A): List[A]
   * @inheritdoc
   */
  def +:[A1 >: A](x: A1): List[A1] = new Cons(x, this)

  /**
   * `O(n)` Applies a function `f` to all elements of this list.
   * @param f the function to apply
   * @tparam U
   * @usecase def foreach(f: A => Unit): Unit
   * @inheritdoc
   */
  def foreach[U](f: A => U): Unit = {
    var list = this
    while (list.nonEmpty) {
      val head +: rest = list
      f(head)
      list = rest
    }
  }

  /**
   * `O(n)` Selects all elements of this list which satisfy a predicate.
   * @param p the predicate to match
   * @return a list
   */
  def filter(p: A => Boolean): List[A] = {
    val step = (x: A, xs: List[A]) => if (p(x)) x +: xs else xs
    foldRight(List.empty[A])(step)
  }

  def withFilter(p: A => Boolean): WithFilter = new WithFilter(p)

  //TODO: not implementing WithFilter contract right now!
  class WithFilter(p: A => Boolean) {
    def flatMap[B](f: A => List[B]): List[B] = self filter p flatMap f
    def foreach[U](f: A => U): Unit = self filter p foreach f
    def map[B](f: A => B): List[B] = self filter p map f
    def withFilter(q: A => Boolean): WithFilter = new WithFilter(x => p(x) && q(x))
  }

  /**
   * `O(n)` Selects all elements of this list which do not satisfy a predicate.
   * @param p the predicate
   * @return a list
   */
  def filterNot(p: A => Boolean): List[A] = {
    val notP = (x: A) => !p(x)
    filter(notP)
  }

  /**
   * `O(n)` Returns the number of elements in the list.
   * @return the number of elements
   */
  def length: Int = foldLeft(0)((len, x) => len + 1)

  /**
   * `O(m)` It returns the prefix of this list of length `m`, or the list itself if `m > length`.
   * @param m the number of elements to take
   * @return the list prefix of length `m`
   */
  def take(m: Int): List[A] = this match {
    case Nil => Nil
    case _ if m == 0 => Nil
    case x +: xs => x +: xs.take(m - 1)
  }

  /**
   * `O(m)` It returns the suffix of this list of length `m`, or the empty list if `m > length`.
   * @param m the number of elements to drop
   * @return the list suffix of length `m`
   */
  def drop(m: Int): List[A] = this match {
    case Nil => Nil
    case xs if m == 0 => xs
    case _ +: xs => xs.drop(m - 1)
  }

  /**
   * `O(n)` Displays all elements of this list in a string.
   * @param sep the elements separator
   * @param start the starting element
   * @param end the ending element
   * @return a string
   */
  def mkString(sep: String, start: String = "", end: String = ""): String = {
    val itemsString = if (isEmpty) ""
    else {
      tail.foldLeft(head.toString)((str, x) => str + sep + x)
    }

    s"$start$itemsString$end"
  }

  // Lazy variant for the foldRight function
  // (source: http://voidmainargs.blogspot.de/2011/08/folding-stream-with-scala.html)
  def foldRight[B](continue: (A, => B) => B, z: B)(f: (A, B) => B): B = {
    this match {
      case Nil => z
      case x +: xs => continue(x, f(x, xs.foldRight(continue, z)(f)))
    }
  }
  
  /**
   * `O(n)` Returns the elements of this list in reverse order.
   * @return the list in reversed order
   */
  def reverse: List[A] = foldLeft(List.empty[A])((xs, x) => x +: xs)

  /**
   * `O(n)` Returns the list obtained by applying `f` to each element of this list.
   * @param f the function to apply
   * @tparam B the resulting list elements type
   * @return the list obtained applying `f`
   */
  def map[B](f: A => B): List[B] =
    foldRight(List.empty[B])((x, xs) => f(x) +: xs)

  /**
   * `O(n)` Builds a new list by applying a function to all elements and using the
   * elements of the resulting lists.
   * @param f
   * @tparam B
   * @return
   */
  def flatMap[B](f: A => List[B]): List[B] =
    foldRight(List.empty[B])((x, xs) => f(x) ++ xs)

  /**
   * `O(n)` Returns a new list obtained appending the elements from `that` list to this one.
   *
   * @param that the second list to append
   * @tparam A1 the resulting list type
   * @return a list obtained appending the element of `that` to this list
   * @usecase def ++(that: List[A]): List[A]
   * @inheritdoc
   */
  def ++[A1 >: A](that: List[A1]): List[A1] =
    foldRight(that)((x, xs) => x +: xs)

  /**
   * `O(n)` Takes an element and a list and "intersperses" that element between the elements of the list.
   *
   * Example:
   * {{{
   *   scala> val l = List('a', 'b', 'c', 'd', 'e').intersperse('-')
   *   l: List[Char] = [a, -, b, -, c, -, d, -, e]
   * }}}
   *
   * @param x
   * @tparam A1
   * @return
   *
   * @usecase def intersperse(x: A): List[A]
   * @inheritdoc
   */
  def intersperse[A1 >: A](x: A1): List[A1] =
    if (isEmpty) this
    else {
      val zero: List[A1] = List(head)
      val step = (ys: List[A1], y: A) => y +: x +: ys
      tail.foldLeft(zero)(step).reverse
    }

  /**
   * `O(n)` Applies a binary operator to a start value and all elements of this sequence, going right to left.
   * @param z the start value
   * @param f the binary operator
   * @tparam B the result type of the binary operator
   * @return right-associative fold of this list
   */
  def foldRight[B](z: B)(f: (A, B) => B): B =
    reverse.foldLeft(z)((xs, x) => f(x, xs))

  /**
   * `O(n)` Applies a binary operator to a start value and all elements of this sequence, going left to right.
   * @param z the start value
   * @param f the binary operator
   * @tparam B the result type of the binary operator
   * @return left-associative fold of this list
   */
  def foldLeft[B](z: B)(f: (B, A) => B): B = {
    @annotation.tailrec
    def go(xs: List[A], acc: B): B = xs match {
      case Nil => acc
      case y +: ys   => go(ys, f(acc, y))
    }

    go(this, z)
  }

  /**
   * Converts this list of lists into a list formed by the elements of these lists.
   * @param ev
   * @tparam B
   * @return
   * @usecase def flatten: List[A]
   * @inheritdoc
   */
  def flatten[B](implicit ev: A => List[B]): List[B] =
    foldRight(List.empty[B])((xss, xs) => xss ++ xs)

  override def toString: String = mkString(", ", "[", "]")
}

object List {
  /**
   * Creates a new, empty `List`.
   * @tparam A the list element type
   * @return an empty `List`
   */
  def empty[A]: List[A] = Nil

  /**
   * Creates a list with the specified elements.
   * @param items the list elements
   * @tparam A the list element type
   * @return a list
   */
  def apply[A](items: A*): List[A] =
    if (items.isEmpty) List.empty[A]
    else {
      items.head +: apply(items.tail: _*)
    }

  /**
   * Returns a list of length `n` with `el` the value of every element.
   * @param n the number of elements
   * @param el the only element in the list
   * @tparam A the list element type
   * @return a list
   */
  def replicate[A](n: Int)(el: => A): List[A] = {
    if (n <= 0) Nil
    else {
      val x = el
      (1 to n).foldLeft(List.empty[A])((ys, y) => x +: ys)
    }
  }

  /**
   * This function is a "dual" to `foldRight`: while `foldRight` reduces a list to a summary value,
   * `unfoldRight` builds a list from a seed value.
   *
   * The function takes the element and returns `None` if it is done producing the list or returns
   * `Just (a, b)`, in which case, a is a prepended to the list and b is used as the next element in a
   * recursive call.
   *
   * @param z the initial seed
   * @param f the function to build the list
   * @tparam A the list element type
   * @tparam B the seed element type
   * @return a list
   */
  def unfoldRight[A, B](z: B)(f: B => Maybe[(A, B)]): List[A] = {
    @annotation.tailrec
    def loop(z: B, acc: List[A]): List[A] = f(z) match {
      case None => acc
      case Just((na, nb)) => loop(nb, na +: acc)
    }

    loop(z, List.empty[A])
  }
}

private[this]
case class Cons[A](head: A, tail: List[A]) extends List[A] {
  override def isEmpty: Boolean = false
}

object +: {
  def unapply[A](xs: List[A]): Option[(A, List[A])] =
    if (xs.isEmpty) scala.None
    else Some((xs.head, xs.tail))
}

private[this]
case object Nil extends List[Nothing] {
  override def head = throw new NoSuchElementException("List.head: empty list")
  override def tail = throw new NoSuchElementException("List.tail: empty list")
  override def isEmpty: Boolean = true
}
