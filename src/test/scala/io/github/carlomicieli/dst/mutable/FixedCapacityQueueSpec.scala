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

import io.github.carlomicieli.util.{Just, None}
import io.github.carlomicieli.test.AbstractTestSpec

class FixedCapacityQueueSpec extends AbstractTestSpec {

  "An empty fixed queue" should "have size equals to 0" in {
    val queue = FixedCapacityQueue[Int](16)
    queue.isEmpty shouldBe true
    queue.size shouldBe 0
  }

  "enqueue an item" should "increase the queue size by 1" in {
    val queue = FixedCapacityQueue[Int](16)
    queue.enqueue(1)

    queue.size shouldBe 1
    queue.isEmpty shouldBe false
    queue.peek.get shouldBe 1
  }

  "dequeue an item" should "happen in FIFO fashion in a queue" in {
    val queue = FixedCapacityQueue[Int](16)
    queue.enqueue(1)
    queue.enqueue(2)

    val k = queue.dequeue()

    k shouldBe 1
    queue.size shouldBe 1
    queue.isEmpty shouldBe false
  }

  "A fixed capacity queue" should "reuse the released space" in {
    val queue = FixedCapacityQueue[Int](4)
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    queue.enqueue(4)
    queue.dequeue()
    queue.enqueue(5)

    queue.peek.get shouldBe 2
    queue.size shouldBe 4
  }

  "A fixed capacity queue" should "return its first element" in {
    val queue = FixedCapacityQueue[Int](4)
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)

    queue.peek shouldBe Just(1)
  }

  "An equal number of enqueue and dequeue operations" should "leave the queue empty" in {
    val queue = FixedCapacityQueue[Int](2)
    queue.enqueue(1)
    queue.dequeue()
    queue.enqueue(2)
    queue.dequeue()
    queue.enqueue(3)
    queue.dequeue()

    queue.size shouldBe 0
    queue.isEmpty shouldBe true
    queue.peek shouldBe None
  }
}
