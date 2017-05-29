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

package io.github.carlomicieli.oop.dst

import io.github.carlomicieli.test.AbstractTestSpec

class FixedCapacityStackSpec extends AbstractTestSpec with SampleFixedCapacityStacks {

  "A fixed capacity stack" should "has size equal 0 if empty" in {
    val empty = FixedCapacityStack(16)
    empty.size shouldBe 0
    empty.isEmpty shouldBe true
  }

  "Push an element in a fixed capacity stack" should "change its size" in {
    val st = stack(16)
    st.push(1)
    st.push(2)
    st.size shouldBe 2

    st.top shouldBe Some(2)
    st.isEmpty shouldBe false
    st.size shouldBe 2
  }

  "Push an element into a fixed capacity stack" should "throw an exception if full" in {
    val st = stack(2)
    st.push(1)
    st.push(2)

    val _ = intercept[FullStackException] {
      st.push(-1)
    }
  }

  "Pop an element out of a fixed capacity stack" should "throw an exception if empty" in {
    val st = stack(2)

    val _ = intercept[EmptyStackException] {
      st.pop()
    }
  }

  "Pop an element out of a fixed capacity stack" should "work in LIFO fashion" in {
    val st = stack(2)
    st.push(1)
    st.push(2)

    val k = st.pop()
    k shouldBe 2
  }

  "peek" should "return the top element on the stack, without changing it" in {
    val st = stack(2)
    st.push(1)

    st.top shouldBe Some(1)
    st.size shouldBe 1
  }
}

trait SampleFixedCapacityStacks {
  def stack(n: Int): Stack[Int] = FixedCapacityStack(n)
}