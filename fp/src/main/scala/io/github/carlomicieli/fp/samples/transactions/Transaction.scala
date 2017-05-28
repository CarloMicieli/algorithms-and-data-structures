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

package io.github.carlomicieli.fp.samples.transactions

sealed trait Transaction {
  def id: Long
}
case class AccountCreated(id: Long, firstName: String, lastName: String) extends Transaction
case class AccountClosed(id: Long) extends Transaction
case class Deposit(id: Long, amount: Double) extends Transaction
case class Withdrawal(id: Long, amount: Double) extends Transaction

object Transaction {
  private val log = List(
    AccountCreated(1, "John", "Doe"),
    Deposit(1, 50.0),
    AccountCreated(2, "Jane", "Doe"),
    Deposit(1, 25.0),
    Withdrawal(1, 45.0),
    AccountClosed(1)
  )

  def accountStatus(id: Long): Option[AccountState] = {
    val next = step(id) _
    log.foldLeft(Option.empty[AccountState])(next)
  }

  private def step(accountId: Long)(state: Option[AccountState], ts: Transaction): Option[AccountState] = {
    state map { as =>
      if (accountId == ts.id) {
        ts match {
          case AccountCreated(id, _, _) => newAccountState(id)
          case AccountClosed(id)        => AccountState(id, Closed, as.balance)
          case Deposit(id, amount)      => as.copy(balance = as.balance + amount)
          case Withdrawal(id, amount)   => as.copy(balance = as.balance - amount)
        }
      } else {
        as
      }
    } orElse {
      ts match {
        case AccountCreated(id, _, _) if id == accountId => Some(newAccountState(id))
        case _ => None
      }
    }
  }

  private def newAccountState(id: Long): AccountState = AccountState(id, Open, 0)
}