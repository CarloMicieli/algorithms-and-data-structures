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
package io.github.carlomicieli.fp.random

import io.github.carlomicieli.fp.typeclasses.Enum
import scala.annotation.implicitNotFound

/**
  * With a source of random number supply in hand, the [[Random]] class allows the programmer to
  * extract pseudo-random values of a variety of types.
  *
  * @tparam A the type from which extract the random values
  */
@implicitNotFound("Pseudo-random generator not found for ${A}.")
trait Random[A] {
  /**
    * Takes a range `(lo, hi)` and a random number generator, and returns a random
    * value uniformly distributed in the closed interval `[lo,hi]`, together with a new generator.
    *
    * @usecase def randomR(lo: A, hi: A): (A, RandomGen)
    * @param lo the lowest value of the range
    * @param hi the highest value of the range
    * @param rGen the random generator in use
    * @return
    */
  def randomR(lo: A, hi: A)(implicit rGen: RandomGen): (A, RandomGen)

  /**
    *
    * @usecase def random: (A, RandomGen)
    * @param rGen a generator
    * @param enum the `Enum` instance for the type `A`
    * @return
    */
  def random(implicit rGen: RandomGen, enum: Enum[A]): (A, RandomGen)

  /**
    *
    * @usecase def randomStream: Stream[A]
    * @param rGen a generator
    * @param enum the `Enum` instance for the type `A`
    * @return
    */
  def randomStream(implicit rGen: RandomGen, enum: Enum[A]): Stream[A] = {
    val (nextSeed, nextRnd) = random(rGen, enum)
    nextSeed #:: randomStream(nextRnd, enum)
  }
}

object Random {
  implicit val intRandom: Random[Int] = new Random[Int] {
    override def random(implicit prng: RandomGen, enum: Enum[Int]): (Int, RandomGen) = prng.next
    override def randomR(lo: Int, hi: Int)(implicit prng: RandomGen): (Int, RandomGen) = ???
  }
}