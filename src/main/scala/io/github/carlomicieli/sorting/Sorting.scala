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
package io.github.carlomicieli.sorting

import scala.reflect.ClassTag

trait Sorting {
  def name: String

  def sort[A: ClassTag](array: Array[A])(implicit ord: Ordering[A]): Unit = {
    sort[A](array, 0, array.length)
  }

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)(implicit ord: Ordering[A]): Unit

  def isSorted[A](array: Array[A])(implicit ord: Ordering[A]): Boolean = {
    array match {
      case Array() | Array(_) => true
      case _ => {
        @annotation.tailrec
        def loop(i: Int): Boolean = {
          if (i == array.length) true
          else {
            if (ord.gt(array(i - 1), array(i))) false
            else loop(i + 1)
          }
        }
        loop(1)
      }
    }
  }

  def printArray[A](array: Array[A]): String = {
    array.mkString("[", ", ", "]")
  }

  protected def swap[A](array: Array[A], i: Int, j: Int): Unit = {
    if (i != j) {
      val tmp: A = array(i)
      array(i) = array(j)
      array(j) = tmp
    }
  }
}
