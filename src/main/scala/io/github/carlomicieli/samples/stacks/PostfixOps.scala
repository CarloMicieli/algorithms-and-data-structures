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

import io.github.carlomicieli.dst.immutable.Stack
import io.github.carlomicieli.util.Good

sealed trait Symbol
object Symbol {
  def isOperator(ch: Char): Boolean = operators contains ch
  def isNumber  (ch: Char): Boolean = ch >= '0' && ch <= '9'

  private val operators = List('+', '-', '*', '/')
}
case class Number(n: Int)     extends Symbol
case class Operator(op: Char) extends Symbol {
  def apply(x: Int, y: Int): Int = op match {
    case '+' => x + y
    case '*' => x * y
    case '/' => x / y
    case '-' => x - y
  }
}

object PostfixOps {
  def eval(exp: String): Int = {
    extractSymbols(exp) match {
      case l@(s :: ss) => compute(l)
      case _           => 0
    }
  }

  private def compute(as: List[Symbol]): Int = {
    def step(st: Stack[Int], s: Symbol): Stack[Int] = {
      s match {
        case Number(n)      => st push n
        case op@Operator(_) =>

          st.pop match {
            case Good((x, st1)) =>
              st1.pop match {
                case Good((y, st2)) => st2 push op(x, y)
                case _              => throw new InvalidPostfixExpressionException()
              }
            case _              =>
              throw new InvalidPostfixExpressionException()
          }
      }
    }

    val zero = Stack.empty[Int]
    val st = as.foldLeft(zero)(step)
    st.top.get
  }

  private def extractSymbols(exp: String): List[Symbol] = {
    import Symbol._
    def toSymbol: PartialFunction[Char, Symbol] = {
      case(ch) if isOperator(ch) => Operator(ch)
      case(ch) if isNumber(ch)   => Number(ch.toInt - '0'.toInt)
    }

    exp.toList.collect(toSymbol)
  }
}

final class InvalidPostfixExpressionException extends Exception("Invalid expression")