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
package io.github.carlomicieli.samples.stacks

import io.github.carlomicieli.test.AbstractTestSpec

class PostfixOpsSpec extends AbstractTestSpec {

  it should "evaluate an empty expression to 0" in {
    PostfixOps.eval("") shouldBe 0
  }

  it should "evaluate a simple expression" in {
    PostfixOps.eval("65+") shouldBe 11
    PostfixOps.eval("56-") shouldBe 1
    PostfixOps.eval("26/") shouldBe 3
    PostfixOps.eval("65*") shouldBe 30
  }

  it should "evaluate an expression with two operators" in {
    PostfixOps.eval("234*+") shouldBe 2 + 3 * 4
  }

  it should "evaluate expressions" in {
    PostfixOps.eval("6523 + 8 *+ 3 +*") shouldBe 288
  }

  it should "throw an exception for invalid expressions" in {
    the [InvalidPostfixExpressionException] thrownBy {
      PostfixOps.eval("65*+")
    } should have message "Invalid expression"
  }

  it should "have different precedence for operators" in {
    char2symbol('9').precedence < char2symbol('+').precedence shouldBe true
    char2symbol('(').precedence > char2symbol('*').precedence shouldBe true
    char2symbol('*').precedence > char2symbol('+').precedence shouldBe true
  }

  it should "return the character for the symbol" in {
    char2symbol('9').toChar shouldBe '9'
    char2symbol('(').toChar shouldBe '('
    char2symbol(')').toChar shouldBe ')'
    char2symbol('*').toChar shouldBe '*'
  }
}
