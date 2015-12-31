/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
 *
 * Copyright (c) 2015 the original author or authors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.carlomicieli.fp.samples.stacks

import io.github.carlomicieli.fp.dst.{Good, Just, Stack, None}

// Checking balancing symbols in a string
object balancingSymbols extends (String => Boolean) {

  def apply(str: String): Boolean = {
    val cs: List[Char] = str.toList
    loop(Stack.empty[Char], cs)
  }

  private def loop(stack: Stack[Char], chars: List[Char]): Boolean = {
    chars match {
      case c :: cs if !(symbols contains c) => loop(stack, cs)
      case c :: cs =>
        if (openingSymbols contains c)
          loop(stack push c, cs)
        else {
          stack.top match {
            case None    => false
            case Just(h) =>
              val Good((s, st)) = stack.pop
              if (matchingSymbols(s) != c)
                false
              else
                loop(st, cs)
          }
        }
      case Nil => stack.isEmpty
    }
  }

  private val openingSymbols = List('(', '[', '{')
  private val closingSymbols = List(')', ']', '}')
  private val symbols = openingSymbols ::: closingSymbols
  private val matchingSymbols = (openingSymbols zip closingSymbols).toMap
}