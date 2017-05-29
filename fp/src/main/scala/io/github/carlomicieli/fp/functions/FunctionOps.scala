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

package io.github.carlomicieli.fp.functions

object FunctionOps {

  /** `flip f` takes its (first) two arguments in the reverse order of `f`.
    *
    * @param f
    * @tparam A
    * @tparam B
    * @tparam C
    * @return
    */
  def flip[A, B, C](f: (A, B) => C): (B, A) => C = (b, a) => f(a, b)

  /** Returns the constant function.
    * @param x
    * @tparam A
    * @return the constant function
    */
  def const[A](x: A): () => A = () => x

  /** Returns the identity function.
    * @tparam A
    * @return the identity function
    */
  def id[A]: A => A = x => x

  /** `until p f` yields the result of applying `f` until `p` holds.
    *
    * @param p the termination condition
    * @param f the function to compute the next value
    * @param z the initial value
    * @tparam A
    * @return the result
    */
  def until[A](p: A => Boolean)(f: A => A)(z: A): A = {
    @annotation.tailrec
    def loop(acc: A): A = {
      if (p(acc)) acc
      else loop(f(acc))
    }

    loop(z)
  }

  /** Returns the error function that fails the program.
    * @param msg the error message
    * @tparam A
    * @return
    */
  def error[A](msg: String): A = throw new Exception(s"*** Exception: $msg")
}
