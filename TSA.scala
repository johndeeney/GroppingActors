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

  class QueueActor(queueNumber: Int) extends Actor {
    def receive() = {
      case Person(name) => 
		println(name + " arrives in queue " + queueNumber)
		bodyArray(queueNumber) ! Person(name)
		bagArray(queueNumber) ! Person(name)
    }
  }

  class BagScanActor(queueNumber: Int) extends Actor {
    def receive() = {
      case Person(name) => 
		println(name + "'s bag arrives at bag scanner " + queueNumber)
		securityArray(queueNumber) ! Person(name)
    }
  }

  class BodyScanActor(queueNumber: Int) extends Actor {
    def receive() = {
      case Person(name) => 
		println(name + " has arrived at body scanner " + queueNumber)
		securityArray(queueNumber) ! Person(name)
    }
  }

  class SecurityActor(queueNumber: Int) extends Actor {
    def receive() = {
      case Person(name) => 
		println(name + " has passed security")
    }
  }

  class JailActor extends Actor {
    def receive() = {
      case Person(name) => 
		println(name " has been sent to jail and will not pass go or collect $200")
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