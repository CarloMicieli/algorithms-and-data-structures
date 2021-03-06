/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
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

package io.github.carlomicieli.oop.dst

/** It represents a mutable linked list.
  * @tparam A the list element type
  */
trait LinkedList[A] {

  /** `O(1)` Returns (but does not remove) the first element (if any) from this list
    * @return the list head
    */
  def head: A = {
    headOption.getOrElse(throw new EmptyLinkedListException("head"))
  }

  /** `O(1)` Optionally returns the first element (if any) from this list
    * @return the list head
    */
  def headOption: Option[A]

  /** `O(1)` Returns (but does not remove) the last element (if any) from this list
    *
    * This method is assuming the linked list implementation will store a pointer to the last element.
    * @return the list tail
    */
  def last: A = {
    lastOption.getOrElse(throw new EmptyLinkedListException("last"))
  }

  /** `O(1)` Optionally returns the last element (if any) from this list.
    *
    * This method is assuming the linked list implementation will store a pointer to the last element.
    * @return the list tail
    */
  def lastOption: Option[A]

  /** `O(1)` Remove the front element from the list
    * @param orElse the alternative value when the list is empty
    * @return the removed element, or `orElse` value when the list is empty
    */
  def removeHead(orElse: => A): A = {
    if (isEmpty) {
      orElse
    } else {
      val h = this.head
      remove(h)
      h
    }
  }

  /** `O(1)` Insert a new element in the front of the list.
    * @param key the element to be inserted
    */
  @inline def +=(key: A): Unit = append(key)

  /** Append the given elements to the list back
    * @param elements the elements to add
    */
  def ++=(elements: A*): Unit = {
    elements foreach append
  }

  /** `O(n)` Remove the element with the given `key` from the list, if exists.
    * @param key the key to be removed
    */
  @inline def -=(key: A): Boolean = remove(key)

  /** `O(1)` Checks whether this list is empty.
    * @return `true` if the list does not contain any element, `false` otherwise.
    */
  def isEmpty: Boolean

  /** `O(1)` Checks whether this list is not empty.
    * @return `true` if the list does contain elements, `false` otherwise.
    */
  @inline def nonEmpty: Boolean = !isEmpty

  /** `O(1)` Adds a new element to the front of the list.
    * @param el the new element
    */
  def prepend(el: A): Unit

  /** `O(1)` Adds a new element to the end of the list.
    * @param el the new element
    */
  def append(el: A): Unit

  /** `O(n)` Applies the function `f` to all elements of this list.
    * @usecase def foreach(f: A => Unit): Unit
    * @param f the function to apply
    * @tparam U
    */
  def foreach[U](f: A => U): Unit = {
    foldLeft(())((_, x) => { val _ = f(x) })
  }

  /** `O(n)` Returns the number of elements in the list.
    * @return the number of elements.
    */
  def length: Int = {
    foldLeft(0)((len, _) => len + 1)
  }

  /** `O(n)` Applies a binary operator `f` to a start value and all elements of this list, going left to right.
    * @param z the start value
    * @param f the binary operator
    * @tparam B the output type
    * @return the result of inserting `op` between consecutive elements
    */
  def foldLeft[B](z: B)(f: (B, A) => B): B

  /** `O(n)` Applies a binary operator `f` to a start value and all elements of this list, going right to left.
    * @param z the start value
    * @param f the binary operator
    * @tparam B the output type
    * @return the result of inserting `op` between consecutive elements
    */
  def foldRight[B](z: B)(f: (A, B) => B): B

  /** Displays all elements of this list in a string.
    * @param sep the string separator
    * @param start the string prefix
    * @param end the string suffix
    * @return the string with the list elements
    */
  def mkString(sep: String, start: String = "", end: String = ""): String

  /** Returns an `Iterable` for this list elements.
    * @return the elements `Iterable`
    */
  def elements: Iterable[A]

  /** `O(n)` Checks whether the given key is contained in the list.
    * @param key the key to find
    * @return `true` if the list contains `key`; `false` otherwise.
    */
  @inline def contains(key: A): Boolean = {
    find(_ == key).isDefined
  }

  /** `O(n)` Finds the first element of this list satisfying a predicate, if any.
    * @param p the predicate used to test elements
    * @return optionally the first element in the list that satisfies `p`
    */
  def find(p: A => Boolean): Option[A]

  /** `O(n)` Remove the element with the given `key` from the list, if exists.
    *
    * In case more element with the same `key` are in the list, this method
    * will remove only the first occurrence.
    *
    * @param key the key to remove
    * @return `true` if an element was removed; `false` otherwise.
    */
  def remove(key: A): Boolean

  /** `O(n)` Update the list element, when the list contains pairs.
    * @param key the key to update
    * @param ev
    * @tparam B
    * @tparam C
    * @return
    */
  def update[B, C](key: A)(implicit ev: A => (B, C)): Boolean

  /** Remove all elements from this list.
    */
  def clear(): Unit

  def zip[B](that: LinkedList[B]): Iterable[(A, B)] = {
    this.elements.zip(that.elements)
  }

  /** `O(n)` Inserts the element into the list at the first position where it
    * is less than or equal to the next element.
    *
    * @usecase def insert(key: A): Unit
    * @inheritdoc
    * @param key the element to insert
    * @param ord
    * @return
    */
  def insert(key: A)(implicit ord: Ordering[A]): Unit

  override def toString: String = mkString(", ", "[", "]")
}

object LinkedList {
  /** Create an empty, singly linked list.
    * @tparam A the element type
    * @return an empty list
    */
  def empty[A]: LinkedList[A] = new SinglyLinkedList[A]

  /** Create a singly linked list with the provided values
    *
    * @param items the initial elements for the list
    * @tparam A the element type
    * @return a list containing the elements
    */
  def apply[A](items: A*): LinkedList[A] = {
    val newList = LinkedList.empty[A]
    for (el <- items) {
      newList.append(el)
    }
    newList
  }

  def unapplySeq[A](list: LinkedList[A]): Option[Seq[A]] = {
    if (list.isEmpty) {
      None
    } else {
      val seq = list.foldRight(Seq.empty[A])((x, xs) => x +: xs)
      Some(seq)
    }
  }

  /** Returns an unmodifiable view of the specified linked list.
    * @param list the list for which the view is returned
    * @tparam A the list element
    * @return an unmodifiable view of the list
    */
  def unmodifiableList[A](list: LinkedList[A]): LinkedList[A] =
    new UnmodifiableLinkedList[A](list)
}

class EmptyLinkedListException(op: String) extends Exception(s"LinkedList.$op: this list is empty")