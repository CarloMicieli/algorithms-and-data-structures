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

package io.github.carlomicieli.oop.sorting

import com.typesafe.scalalogging.LazyLogging

import scala.reflect.ClassTag

/**
  * Implements the `shell sort` sorting algorithm.
  */
object Shell extends Sorting with LazyLogging {
  def name: String = "Shell sort"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)
                       (implicit ord: Ordering[A]): Unit = {
    import Ordered._

    val N: Int = end - start
    val incrementValues = increments(incrementFun)
      .takeWhile(p => p._1 < N / 3)
      .map(_._2)
      .reverse
      .toList

    logger.debug(s"Increments are $incrementValues")

    for (h <- incrementValues) {
      var i = 0
      while (i < h && i < N) {
        var j = h + i
        while (j < N) {
          val el = array(j)
          var k = j - h

          while (k >= 0 && array(k) > el) {
            array(k + h) = array(k)
            k = k - h
          }
          array(k + h) = el
          j = j + h
        }
        i = i + 1
      }
    }
  }

  private def increments(f: Int => Int): Stream[(Int, Int)] = {
    def incrementStream(h: Int): Stream[(Int, Int)] = {
      lazy val next = f(h)
      (h, next) #:: incrementStream(next)
    }
    (0, 1) #:: incrementStream(1)
  }

  private val incrementFun: Int => Int = h => 3 * h + 1
}