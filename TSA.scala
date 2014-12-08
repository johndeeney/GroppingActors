import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object TSA {
  val numLines = 3
  val numPassengers = 20

  case class Person(name: String)

  class DocumentCheckActor(numLines: Int) extends Actor {
    var lastLine = 0
    def receive() = {
      case Person(name) =>
        println(name + " arrives at Document Check")
        lastLine = lastLine + 1
        if (lastLine > numLines) {
          lastLine = 0
        }
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

  def main(args: Array[String]) = {
    val system = ActorSystem("MySystem")
    val docCheck = system.actorOf(Props(new DocumentCheckActor(numLines)), name = "docCheck")
    for(a <- 1 to numPassengers){
      docCheck ! Person(a + "")
    }
    system.shutdown()
  }
}