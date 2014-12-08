import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object TSA {
  val numLines = 3
  val numPassengers = 20
  val system = ActorSystem("MySystem")
  var docCheck = system.actorOf(Props[DocumentCheckActor], name = "docCheck")
  var queueArray:Array[QueueActor] = Array.empty[QueueActor]
  var bagArray:Array[BagScanActor] = Array.empty[BagScanActor]
  var bodyArray:Array[BodyScanActor] = Array.empty[BodyScanActor]
  var securityArray:Array[SecurityActor] = Array.empty[SecurityActor]
  var jail:JailActor = system.actorOf(Props[JailActor], name = "jail")

  case class Person(name: String)

  class DocumentCheckActor extends Actor {
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
    queueArray = new Array[QueueActor](numLines)
    bagArray = new Array[BagScanActor](numLines)
    bodyArray = new Array[BodyScanActor](numLines)
    securityArray = new Array[SecurityActor](numLines)
    for(i <- 0 to (numLines-1)){
      queueArray(i) = system.actorOf(Props(new QueueActor(i)), name = "queue".concat(i+""))
      bagArray(i) = system.actorOf(Props(new BagScanActor(i)), name = "bag".concat(i+""))
      bodyArray(i) = system.actorOf(Props(new BodyScanActor(i)), name = "body".concat(i+""))
      securityArray(i) = system.actorOf(Props(new SecurityActor(i)), name = "security".concat(i+""))
    }
    for(a <- 1 to numPassengers){
      docCheck ! Person(a + "")
    }
    system.shutdown()
  }
}