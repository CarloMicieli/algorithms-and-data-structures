/*
 * Copyright 2014 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the 'License');
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an 'AS IS' BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.carlomicieli

import java.nio.file.{Paths, StandardOpenOption, Files}
import com.carlomicieli.algo.Arrays

import com.carlomicieli.algo.sorting._

/**
 * @author Carlo Micieli
 */
object MyApp extends App {

  //Seq(16, 256, 4096, 32768, 65536, 262144, 524288, 1048576).foreach(createTestData)

  val report = ProblemSet.solveAll(new SelectionSort)

  def createTestData(size: Int): Unit = {
    val numbers = (0 to size * 2).toArray
    Arrays.shuffle(numbers)

    val bytes = numbers.take(size).map(_.toString).mkString("\n").getBytes

    Files.write(Paths.get("/home/carlo/Projects/algorithms-and-data-structures/src/main/resources/test-data/", s"set$size.txt"), bytes,
      StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
  }

}


