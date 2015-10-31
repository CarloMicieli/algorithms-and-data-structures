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
class BsTree[K, V] extends Tree[K, V] {
  private var root: Node = NIL

  override def inorderWalk(f: KeyValuePair[K, V] => Unit): Unit = {
    def walk(curr: Node): Unit = {
      if (curr.isNIL) ()
      else {
        walk(curr.left)
        f(curr.toKeyValue)
        walk(curr.right)
      }
    }

    walk(root)
  }

  override def successor(key: K)(implicit ord: Ordering[K]): K = {
    var x = findNode(key)
    val next: Node =
      if (x.isNIL)
        x
      else if (x.right.isNotNIL)
        treeMin(x.right)
      else {
        var y = x.parent
        while (y.isNotNIL && x == y.right) {
          x = y
          y = y.parent
        }
        y
      }

    next.keyOrThrow {
      new NoSuchElementException(s"Successor not found for '$key'")
    }
  }

  override def predecessor(key: K)(implicit ord: Ordering[K]): K = {
    var x = findNode(key)
    val prev: Node =
      if (x.isNIL)
        x
      else if (x.left.isNotNIL)
        treeMax(x.left)
      else {
        var y = x.parent
        while (y.isNotNIL && x == y.left) {
          x = y
          y = y.parent
        }
        y
      }

    prev.keyOrThrow {
      new NoSuchElementException(s"Predecessor not found for '$key'")
    }
  }

  override def delete(key: K)(implicit ord: Ordering[K]): Maybe[V] = {
    findNode(key) match {
      case NIL => None
      case z =>
        treeDelete(z)
        z.toMaybe.map(_._2)
    }
  }

  override def min: K = {
    if (isEmpty)
      throw new NoSuchElementException("min: tree is empty")
    else {
      treeMin(root).key
    }
  }

  override def max: K = {
    if (isEmpty)
      throw new NoSuchElementException("max: tree is empty")
    else {
      treeMax(root).key
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
        y.left = new TreeNode(y, key, value)
      else
        y.right = new TreeNode(y, key, value)
    }
  }

  override def search(key: K)(implicit ord: Ordering[K]): Maybe[V] = {
    findNode(key).toMaybe.map { p => p._2 }
  }

  private def treeDelete(z: Node): Unit = {
    if (z.left.isNIL)
      transplant(z, z.right)
    else if (z.right.isNIL)
      transplant(z, z.left)
    else {
      val y = treeMin(z.right)
      if (y.parent != z) {
        transplant(y, y.right)
        y.right = z.right
        y.right.parent = y
      }

      transplant(z, y)
      y.left = z.left
      y.left.parent = y
    }
  }

  private def transplant(u: Node, v: Node): Unit = {
    if (u.parent.isNIL) {
      root = v
    }
    else if (u == u.parent.left) {
      u.parent.left = v
    }
    else {
      u.parent.right == v
    }

    if (v.isNotNIL) {
      v.parent = u.parent
    }
  }

  private def treeMax(node: Node): Node = {
    var x: Node = node
    var y: Node = NIL
    while (x.isNotNIL) {
      y = x
      x = x.right
    }

    y
  }

  private def treeMin(node: Node): Node = {
    var x: Node = node
    var y: Node = NIL
    while (x.isNotNIL) {
      y = x
      x = x.left
    }

    y
  }

  private def findNode(key: K)(implicit ord: Ordering[K]): Node = {
    def iterativeSearch(x: Node, key: K): Node = {
      import Ordered._
      var curr = x
      while (curr.isNotNIL && curr.key != key) {
        curr = if (key < curr.key)
          curr.left
        else
          curr.right
      }
      curr
    }
    iterativeSearch(root, key)
  }

  sealed trait Node {
    def key: K
    def value: V

    var parent: Node
    var left: Node
    var right: Node
    def toPair: (K, V) = (key, value)
    def toKeyValue: KeyValuePair[K, V] = KeyValuePair(key, value)
    def toMaybe: Maybe[(K, V)] = if (isNIL) None else Just(toPair)

    def isNIL: Boolean
    def isNotNIL: Boolean = !isNIL

    def keyOrThrow(ex: => Throwable): K = if (isNotNIL) key else throw ex
  }

  case object NIL extends Node {
    def key = throw new NoSuchElementException("node.key: this node is empty")
    def value = throw new NoSuchElementException("node.value: this node is empty")
    def isNIL = true

    def left: Node = throw new NoSuchElementException("node.left: this node is empty")
    def left_=(n: Node): Unit = {}

    def right: Node = throw new NoSuchElementException("node.right: this node is empty")
    def right_=(n: Node): Unit = {}

    def parent: Node = throw new NoSuchElementException("node.parent: this node is empty")
    def parent_=(n: Node): Unit = {}
  }

  case class TreeNode(var parent: Node, key: K, var value: V, var left: Node, var right: Node) extends Node {

    def this(key: K, value: V) = this(NIL, key, value, NIL, NIL)
    def this(parent: Node, key: K, value: V) = this(parent, key, value, NIL, NIL)

    def isNIL = false
  }
}

object BsTree {

  /**
   * Creates a new empty Binary Search tree.
   * @param ord the keys ordering
   * @tparam K the key type
   * @tparam V the value type
   * @return an empty tree
   */
  def empty[K, V](implicit ord: Ordering[K]): Tree[K, V] = new BsTree[K, V]

  /**
   * Creates a new tree with list elements
   * @param items the elements list
   * @param ord the key ordering
   * @tparam K the key type
   * @tparam V the value type
   * @return a tree
   */
  def fromList[K, V](items: List[(K, V)])(implicit ord: Ordering[K]): Tree[K, V] = {
    items.foldLeft(BsTree.empty[K, V])((tree, p) => { tree.insert(p._1, p._2); tree })
  }

}
