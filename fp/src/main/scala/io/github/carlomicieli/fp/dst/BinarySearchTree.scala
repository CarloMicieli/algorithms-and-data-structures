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

package io.github.carlomicieli.fp.dst

private[this] sealed trait BinarySearchTree[+K, +V] extends Tree[K, V] {
  def get: (K, V)

  def lookup[K1 >: K](key: K1)(implicit ord: Ordering[K1]): Maybe[V] = {
    this match {
      case EmptyTree                    => None
      case Node(k, v, _, _) if k == key => Just(v)
      case Node(k, _, left, right) =>
        import Ordered._
        if (key < k)
          left.lookup(key)
        else
          right.lookup(key)
    }
  }

  def insert[K1 >: K, V1 >: V](key: K1, value: V1)(implicit ord: Ordering[K1]): Tree[K1, V1] = {
    this match {
      case EmptyTree                           => new Node(key, value)
      case node @ Node(k, _, _, _) if k == key => node.copy(value = value)
      case node @ Node(k, _, left, right) =>
        import Ordered._
        if (key < k)
          node.copy(left = left.insert(key, value))
        else
          node.copy(right = right.insert(key, value))
    }
  }

  def min: Maybe[K] = this match {
    case EmptyTree                => None
    case Node(k, _, EmptyTree, _) => Just(k)
    case Node(_, _, left, _)      => left.min
  }

  def max: Maybe[K] = this match {
    case EmptyTree                => None
    case Node(k, _, _, EmptyTree) => Just(k)
    case Node(_, _, _, right)     => right.max
  }

  def size: Int = this match {
    case EmptyTree               => 0
    case Node(_, _, left, right) => 1 + left.size + right.size
  }

  def toList: List[(K, V)] = this match {
    case EmptyTree               => List.empty[(K, V)]
    case Node(k, v, left, right) => left.toList ++ List((k, v)) ++ right.toList
  }

  def delete[K1 >: K](key: K1)(implicit ord: Ordering[K1]): (Maybe[V], Tree[K1, V]) = {
    this match {
      case EmptyTree => (None, EmptyTree)
      case node @ Node(k, _, left, right) if k != key =>
        import Ordered._
        if (key < k) {
          val (rem, tree2) = left.delete(key)
          (rem, node.copy(left = tree2))
        } else {
          val (rem, tree2) = right.delete(key)
          (rem, node.copy(right = tree2))
        }
      case Node(k, v, left, EmptyTree) if k == key  => (Just(v), left)
      case Node(k, v, EmptyTree, right) if k == key => (Just(v), right)
      case node @ Node(k, v, left, right) =>
        val minKey = right.min.get
        val (minVal, r) = right.delete[K1](minKey)(ord)
        (Just(v), Node(minKey, minVal.get, left, r))
    }
  }

  def isEmpty: Boolean

  def depth: Int = this match {
    case EmptyTree               => 0
    case Node(_, _, left, right) => 1 + math.max(left.depth, right.depth)
  }

  def upsert[K1 >: K, V1 >: V](key: K1, value: V1)(f: (V1) => V1)(implicit ord: Ordering[K1]): Tree[K1, V1] = {
    this match {
      case EmptyTree                           => new Node(key, value)
      case node @ Node(k, v, _, _) if k == key => node.copy(value = f(v))
      case node @ Node(k, _, left, right) =>
        import Ordered._
        if (key < k)
          node.copy(left = left.upsert(key, value)(f))
        else
          node.copy(right = right.upsert(key, value)(f))
    }
  }

  def map[V1](f: V => V1): Tree[K, V1] = {
    this match {
      case EmptyTree => EmptyTree
      case Node(k, v, left, right) =>
        Node(k, f(v), left.map(f), right.map(f))
    }
  }

  def fold[V1 >: V](f: (V1, V1) => V1): V1 = {
    this match {
      case EmptyTree                        => throw new NoSuchElementException("fold: tree is empty")
      case Node(_, v, EmptyTree, EmptyTree) => v
      case Node(_, v, left, EmptyTree)      => f(left.fold(f), v)
      case Node(_, v, EmptyTree, right)     => f(v, right.fold(f))
      case Node(_, v, left, right)          => f(v, f(left.fold(f), right.fold(f)))
    }
  }

  def contains[K1 >: K](key: K1)(implicit ord: Ordering[K1]): Boolean = {
    this match {
      case EmptyTree                    => false
      case Node(k, _, _, _) if k == key => true
      case Node(k, _, left, right) =>
        import Ordered._
        if (key < k)
          left.contains(key)
        else
          right.contains(key)
    }
  }

  override def toString: String = {
    this match {
      case EmptyTree => "-"
      case Node(k, v, l, r) =>
        s"($l [$k->$v] $r)"
    }
  }
}

object BinarySearchTree {

  /** It creates a new empty, binary search tree.
    * @tparam K the key type
    * @tparam V the value type
    * @return an empty `Tree`
    */
  def empty[K, V]: Tree[K, V] = EmptyTree

  /** It creates a binary search tree from the list elements.
    *
    * @param xs the list of elements to insert
    * @param ord the ordering
    * @tparam K the key type
    * @tparam V the value type
    * @return a `Tree`
    */
  def fromList[K, V](xs: List[(K, V)])(implicit ord: Ordering[K]): Tree[K, V] = {
    xs.foldLeft(BinarySearchTree.empty[K, V])((tree, x) => tree.insert(x))
  }
}

private[this] case object EmptyTree extends BinarySearchTree[Nothing, Nothing] {
  def isEmpty = true
  def get = throw new NoSuchElementException("Tree.get: this tree is empty")
}

private[this] case class Node[K, V](key: K, value: V, left: Tree[K, V], right: Tree[K, V]) extends BinarySearchTree[K, V] {

  def this(key: K, value: V) = {
    this(key, value, EmptyTree, EmptyTree)
  }

  def isEmpty = false
  def get: (K, V) = (key, value)
}