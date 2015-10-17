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

import io.github.carlomicieli.util.Default

import scala.reflect.ClassTag

class ArrayList[A: ClassTag] private(st: Array[A]) extends RandomAccess[A] {

  require(st != null)
  require(st.length != 0)

  private var currentSize: Int = 0
  private var storage = st

  override def update(i: Int, x: A): Unit = {
    if (!isDefinedAt(i))
      throw new IndexOutOfBoundsException(i.toString)

    storage(i) = x
  }

  override def trimToSize(): Unit = ???

  override def ensureCapacity(newCapacity: Int): Unit = ???

  override def clear(implicit default: Default[A]): Unit = ???

  override def size: Int = currentSize

  override def capacity: Int = storage.length

  override def remove(x: A): Boolean = {
    var removed = false
    val arrIndices = 0 until size

    for (i <- arrIndices) {
      if (storage(i) == x) {
        removed = true
      }

      if (removed && i < currentSize) {
        storage(i) = storage(i + 1)
        currentSize = currentSize - 1
      }
    }

    resizeStorage()
    removed
  }

  override def foldRight[B](z: B)(f: (A, B) => B): B = {
    var acc = z
    for (i <- size - 1 downTo 0) {
      acc = f(storage(i), acc)
    }
    acc
  }

  override def foldLeft[B](z: B)(f: (B, A) => B): B = {
    var acc = z
    for (i <- 0 until size) {
      acc = f(acc, storage(i))
    }
    acc
  }

  override def mkString(sep: String, start: String, end: String): String = {
    val itemsString = if (isEmpty) ""
      else {
        var str = storage(0).toString
        for (i <- 1 until size) {
          str = str + s"$sep${storage(i)}"
        }
        str
      }
    s"$start$itemsString$end"
  }

  override def foreach[U](f: (A) => U): Unit = {
    for (i <- 0 until size) {
      f(storage(i))
    }
  }

  override def add(x: A): Unit = {
    resizeStorage()
    storage(currentSize) = x
    currentSize = currentSize + 1
  }

  override def isDefinedAt(i: Int): Boolean = i >= 0 && i < size

  override def apply(i: Int): A = storage(i)

  override def toString(): String = this.mkString(", ", "[", "]")

  override def equals(o: Any): Boolean = o match {
    case that: ArrayList[A] => this.storage sameElements that.storage
    case _                  => false
  }

  private def resizeStorage(): Unit = {
    import ArrayList._
    storage = if (loadFactor > 0.75f) {
      resize(ResizeRatio)(storage)
    }
    //else if (loadFactor < 0.25f) {
    //  resize(1.0f / ResizeRatio)(storage)
    //}
    else {
      storage
    }
  }
}

object ArrayList {
  val InitialCapacity: Int = 16
  val ResizeRatio: Double = 3.0 / 2

  def empty[A: ClassTag]: ArrayList[A] = {
    new ArrayList[A](newArray(InitialCapacity))
  }

  def apply[A: ClassTag](items: A*): ArrayList[A] = {
    val size = (items.length * 1.5).toInt
    val al = new ArrayList[A](newArray(size))

    for (i <- items) {
      al.add(i)
    }

    al
  }

  private def newArray[A: ClassTag](size: Int): Array[A] = new Array[A](size)

  private def resize[A: ClassTag](ratio: Double)(array: Array[A]): Array[A] = {
    val newCapacity = (array.length * ratio).toInt
    val newArr = newArray[A](newCapacity)
    for (i <- 0 until math.min(newCapacity, array.length)) {
      newArr(i) = array(i)
    }
    newArr
  }

}