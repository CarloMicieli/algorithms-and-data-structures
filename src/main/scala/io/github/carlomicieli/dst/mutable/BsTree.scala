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
package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.util.{Just, Maybe, None}

private[this]
final class BsTree[K, V] extends Tree[K, V] {
  var root: Node = NIL

  override def inorderWalk(f: ((K, V)) => Unit): Unit = ???

  override def successor(key: K): K = ???

  override def predecessor(key: K): K = ???

  override def delete(key: K)(implicit ord: Ordering[K]): Maybe[V] = ???

  override def min: K = {
    if (isEmpty)
      throw new NoSuchElementException("min: tree is empty")
    else {
      var x: Node = root
      var y: Node = NIL
      while (x.isNotNIL) {
        y = x
        x = x.left
      }

      y.key
    }
  }

  override def max: K = {
    if (isEmpty)
      throw new NoSuchElementException("max: tree is empty")
    else {
      var x: Node = root
      var y: Node = NIL
      while (x.isNotNIL) {
        y = x
        x = x.right
      }

      y.key
    }
  }

  override def size: Int = {
    def count(x: Node): Int = {
      if (x.isNIL) 0 else 1 + count(x.left) + count(x.right)
    }

    count(this.root)
  }

  override def isEmpty: Boolean = root.isNIL

  override def insert(key: K, value: V)(implicit ord: Ordering[K]): Unit = {
    if (isEmpty) {
      root = new TreeNode(key, value)
    } else {
      import Ordered._

      var x: Node = root
      var y: Node = NIL
      while (x.isNotNIL) {
        y = x
        x = if (key < x.key) x.left else x.right
      }

      if (key < y.key)
        y.left = new TreeNode(key, value)
      else
        y.right = new TreeNode(key, value)
    }
  }

  override def search(key: K)(implicit ord: Ordering[K]): Maybe[V] = {
    if (isEmpty)
      None
    else {
      import Ordered._
      def go(x: Node): Maybe[V] = {
        if (x.isNIL || x.key == key) {
          x.toMaybe.map(kv => kv._2)
        } else if (key < x.key) {
          go(x.left)
        } else {
          go(x.right)
        }
      }
      go(this.root)
    }
  }

  sealed trait Node {
    def key: K
    def value: V

    var left: Node
    var right: Node
    def toPair: (K, V) = (key, value)
    def toMaybe: Maybe[(K, V)] = if (isNIL) None else Just(toPair)

    def isNIL: Boolean
    def isNotNIL: Boolean = !isNIL
  }

  case object NIL extends Node {
    def key = throw new NoSuchElementException("node.key: this node is empty")
    def value = throw new NoSuchElementException("node.value: this node is empty")
    def left = throw new NoSuchElementException("node.left: this node is empty")
    def left_=(x: Node): Unit = throw new NoSuchElementException("node.left: this node is empty")
    def right = throw new NoSuchElementException("node.right: this node is empty")
    def right_=(x: Node): Unit = throw new NoSuchElementException("node.right: this node is empty")
    def isNIL = true
  }

  case class TreeNode(key: K, var value: V, var left: Node, var right: Node) extends Node {
    def this(key: K, value: V) = this(key, value, NIL, NIL)

    def isNIL = false
  }
}

object BsTree {
  def empty[K, V](implicit ord: Ordering[K]): Tree[K, V] = new BsTree[K, V]
}
