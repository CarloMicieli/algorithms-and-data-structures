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

import io.github.carlomicieli.test.AbstractTestSpec

class StackOpSpec extends AbstractTestSpec with SampleStacks {
  "A sequence of valid ops" should "produce valid stacks" in {
    import StackOp._
    val res = sequence(
      emptyStack,
      List(PushOp(1), PushOp(2), PushOp(3), PopOp, PopOp, PushOp(5))
    )

    val Good(finalStack) = res
    finalStack.isEmpty shouldBe false
    finalStack.size shouldBe 2
    finalStack.top shouldBe Just(5)
  }

  "A sequence of invalid operation" should "produce an Bad result" in {
    import StackOp._

    val res = sequence(
      emptyStack,
      List(PushOp(1), PushOp(2), PushOp(3), PopOp, PopOp, PopOp, PopOp, PushOp(5))
    )

    val Bad(invalidOp) = res
    invalidOp.op shouldBe PopOp
    invalidOp.ex.getMessage shouldBe "Stack is empty"
    invalidOp.stack.isEmpty shouldBe true
  }
}

trait SampleStacks {
  def emptyStack: Stack[Int] = Stack.empty[Int]
}