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

import io.github.carlomicieli.test.AbstractSpec

class StackSpec extends AbstractSpec {

  describe("A Stack companion object") {
    describe("empty") {
      it("should create an empty stack") {
        val empty = Stack.empty[Int]
        empty.isEmpty shouldBe true
      }
    }

    describe("fromOps") {
      it("should create a stack from a list of operations") {
        val ops = List(PushOp(1), PushOp(2), PushOp(5))
        val s = Stack.fromOps(ops)
        s.size shouldBe ops.length
      }

      it("should apply operations to the stack") {
        val ops = List(PushOp(1), PopOp, PushOp(2), PushOp(5), PopOp, PopOp)
        val s = Stack.fromOps(ops)
        s.isEmpty shouldBe true
      }

      it("should silently discard invalid operations") {
        val ops = List(PopOp, PopOp)
        val s = Stack.fromOps(ops)
        s.isEmpty shouldBe true
      }
    }
  }
}