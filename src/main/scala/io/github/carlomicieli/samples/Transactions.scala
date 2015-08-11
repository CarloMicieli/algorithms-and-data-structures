package io.github.carlomicieli.samples

sealed trait State
case object Open extends State
case object Closed extends State
case class AccountState(id: Long, state: State, balance: Double)

sealed trait Transaction {
  def id: Long
}
case class AccountCreated(id: Long, firstName: String, lastName: String) extends Transaction
case class AccountClosed(id: Long) extends Transaction
case class Deposit(id: Long, amount: Double) extends Transaction
case class Withdrawal(id: Long, amount: Double) extends Transaction

object Transactions {
  private val log = List(
    AccountCreated(1, "John", "Doe"),
    Deposit(1, 50.0),
    AccountCreated(2, "Jane", "Doe"),
    Deposit(1, 25.0),
    Withdrawal(1, 45.0),
    AccountClosed(1))

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