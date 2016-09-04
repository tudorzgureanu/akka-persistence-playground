package com.ted.playground.akka.persistence.fixtures

import akka.persistence.PersistentActor
import com.ted.playground.akka.persistence.fixtures.RestartableActor._

trait RestartableActor extends PersistentActor {

  abstract override def receiveCommand = super.receiveCommand orElse {
    case RestartActor => throw RestartActorException
  }
}

object RestartableActor {
  case object RestartActor

  private object RestartActorException extends Exception
}