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

import org.scalatest.{ Matchers, FlatSpec }

class MaxPQSpec extends FlatSpec with Matchers {

  "A MaxPQ" should "have heap size = 0 when empty" in {
    val pq = MaxPQ[Int](16)
    pq.heapSize should be(0)
    pq.length should be(16)
  }

  "An array" should "be used to create a MaxPQ" in {
    val pq = MaxPQ(Array(4, 1, 3, 2, 16, 9, 10, 14, 8, 7))
    pq.heapSize should be(10)
    pq.toString should be("MaxPQ(16, 14, 10, 8, 7, 9, 3, 2, 4, 1)")
  }

  "Remove from MaxPQ" should "keep valid the max-heap property" in {
    val pq = MaxPQ(Array(4, 1, 3, 2, 16, 9, 10, 14, 8, 7))
    val oldSize = pq.heapSize
    pq.removeMax() should be(16)
    pq.removeMax() should be(14)
    pq.heapSize should be(oldSize - 2)
  }
}