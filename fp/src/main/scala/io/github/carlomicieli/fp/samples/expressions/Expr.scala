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

package io.github.carlomicieli.fp.samples.expressions

sealed trait Expr
case class Add(l: Expr, r: Expr)  extends Expr
case class Mul(l: Expr, r: Expr)  extends Expr
case class Val(value: Int)        extends Expr
case class Var(name: String)      extends Expr

object Expr {
  type Bind = (String, Int)

  def show(exp: Expr, lev: Int = 0): String = {
    def parenses(s: String): String =
      if (lev == 0) s else s"($s)"

    exp match {
      case Add(l, r)  => parenses(s"${show(l, lev + 1)} + ${show(r, lev + 1)}")
      case Mul(l, r)  => parenses(s"${show(l, lev + 1)} * ${show(r, lev + 1)}")
      case Val(v)     => v.toString
      case Var(name)  => name
    }
  }

  def calc(exp: Expr, env: List[Bind]): Int = exp match {
    case Add(l, r)  => calc(l, env) + calc(r, env)
    case Mul(l, r)  => calc(l, env) * calc(r, env)
    case Val(v)     => v
    case Var(name)  =>
      env.filter(b => b._1 == name).map(b => b._2) match {
        case x :: _ => x
        case List()  => throw new NoSuchElementException(s"No bind found for variable '$name'")
      }
  }
}