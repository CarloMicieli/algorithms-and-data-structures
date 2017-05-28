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

package io.github.carlomicieli.fp.random

import scala.annotation.implicitNotFound

/**
  * The class [[RandomGen]] provides a common interface to random number generators.
  */
@implicitNotFound("No pseudo-random generator is available.")
trait RandomGen {
  /**
    * The next operation returns an Int that is uniformly distributed in the range
    * (including both end points), and a new [[RandomGen]].
    * @return a pair, with an Int and a new [[RandomGen]].
    */
  def next: (Int, RandomGen)
}

object RandomGen {
  private val initSeed = System.nanoTime & Int.MaxValue
  implicit val stdGen: RandomGen = RandomGen.getStdGen(initSeed.toInt)

  /**
    * Returns a linear congruential generator, initialized with the provided `seed`.
    * @param seed the generator `seed`
    * @return a generator
    */
  def getStdGen(seed: Int): RandomGen = new StdGen(seed)

  private class StdGen(seed: Int) extends RandomGen {
    def next: (Int, StdGen) = {
      val newSeed = generate(seed)
      (newSeed, new StdGen(newSeed))
    }

    private val a: Int = 1103515245
    private val m: Int = Int.MaxValue
    private val c: Int = 12345

    private def generate(x: Int): Int = {
      (a * x + c) & m
    }
  }
}