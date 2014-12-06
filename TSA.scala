import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

case class Person(name: String)

class DocumentCheckActor extends Actor {
  def receive() = {
    case Person(name) => println(name)
  }
}

class QueueActor extends Actor {
  def receive() = {
    case Person(name) => println(name)
  }
}

class BagScanActor extends Actor {
  def receive() = {
    case Person(name) => println(name)
  }
}

class BodyScanActor extends Actor {
  def receive() = {
    case Person(name) => println(name)
  }
}

class SecurityActor extends Actor {
  def receive() = {
    case Person(name) => println(name)
  }
}

class JailActor extends Actor {
  def receive() = {
    case Person(name) => println(name)
  }
}

object Main {
  def main(args: Array[String]) = {
    val system = ActorSystem("MySystem")
    val docCheck = system.actorOf(Props[DocumentCheckActor], name = "docCheck")
    docCheck ! Person("Charlie Parker")
    system.shutdown()
  }
}