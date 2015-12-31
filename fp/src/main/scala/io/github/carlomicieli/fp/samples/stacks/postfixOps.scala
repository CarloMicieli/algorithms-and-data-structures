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

import io.github.carlomicieli.fp.dst.{Good, Stack}

sealed trait Symbol {
  val precedence: Int
  def toChar: Char
}

object Symbol {
  def isOperator(ch: Char): Boolean = operators contains ch
  def isNumber  (ch: Char): Boolean = ch >= '0' && ch <= '9'
  def isParen   (ch: Char): Boolean = ch == '(' || ch == ')'

  private val operators = List('+', '-', '*', '/')
}

case object OpenParen extends Symbol {
  override val precedence = 5
  override def toChar = '('
}
case object ClosedParen extends Symbol {
  override val precedence = 5
  override def toChar = ')'
}
case class Number(n: Int) extends Symbol {
  override val precedence = 1
  override def toChar = ('0'.toInt + n).toChar
}
class Operator private(op: Char, prec: Int) extends Symbol {
  override val precedence = prec
  override def toChar = op
  def apply(x: Int, y: Int): Int = op match {
    case '+' => x + y
    case '*' => x * y
    case '/' => x / y
    case '-' => x - y
  }
}
object Operator {
  def apply(op: Char): Operator = op match {
    case '+' | '-' => new Operator(op, 2)
    case '*' | '/' => new Operator(op, 3)
  }

  def unapply(op: Operator): Option[Char] = Some(op.toChar)
}

object symbol2char extends (Symbol => Char) {
  override def apply(s: Symbol): Char = s.toChar
}

object char2symbol extends PartialFunction[Char, Symbol] {
  import Symbol._

  override def apply(ch: Char): Symbol = ch match {
    case _ if isOperator(ch) => Operator(ch)
    case _ if isNumber(ch)   => Number(ch.toInt - '0'.toInt)
    case _ if ch == '('      => OpenParen
    case _ if ch == ')'      => ClosedParen
  }

  override def isDefinedAt(x: Char): Boolean = isParen(x) || isNumber(x) || isOperator(x)
}

object infix2postfix {
  def apply(exp: String): String = {
    extractSymbols(exp) match {
      case List()  => exp
      case symbols => convertExpression.andThen(symbolsToString)(symbols)
    }
  }

  private val convertExpression: (List[Symbol] => List[Symbol]) = as => {
    val zero = (Stack.empty[Symbol], Vector(as.head))
    def step(acc: (Stack[Symbol], Vector[Symbol]), s: Symbol): (Stack[Symbol], Vector[Symbol]) = {
      val (ops, output) = acc
      s match {
        case OpenParen      => (ops, output)
        case ClosedParen    => (ops, output)
        case op@Operator(_) => (ops push op, output)
        case sym@Number(n)  =>
          (ops, output :+ sym)
      }
    }

    val (_, symbols) = as.tail.foldLeft(zero)(step)
    symbols.toList
  }

  private val symbolsToString: List[Symbol] => String = _.map(symbol2char).mkString("")
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
        case OpenParen | ClosedParen => st
      }
    }

    val zero = Stack.empty[Int]
    val st = as.foldLeft(zero)(step)
    st.top.get
  }
}

object extractSymbols extends (String => List[Symbol]) {
  def apply(exp: String): List[Symbol] = {
    exp.toList.collect(char2symbol)
  }
}

final class InvalidPostfixExpressionException extends Exception("Invalid expression")