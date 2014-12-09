import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef
import akka.actor.ActorRefFactory

object TSA {
  val numLines = 3 //Number of lines in airport for screening
  val numPassengers = 20 //Number of passengers entering airport
  val system = ActorSystem("MySystem")
  var docCheck = system.actorOf(Props[DocumentCheckActor], name = "docCheck")
  var queueArray:Array[ActorRef] = Array.empty[ActorRef]
  var bagArray:Array[ActorRef] = Array.empty[ActorRef]
  var bodyArray:Array[ActorRef] = Array.empty[ActorRef]
  var securityArray:Array[ActorRef] = Array.empty[ActorRef]
  var jail = system.actorOf(Props[JailActor], name = "jail")

  case class Person(name: String)

  class DocumentCheckActor extends Actor {
    var lastLine = 0
    def receive() = {
      case Person(name) =>
        println(name + " arrives at Document Check")
        lastLine = lastLine + 1
        if (lastLine >= numLines) {
          lastLine = 0
        }
        queueArray(lastLine) ! Person(name)
    }
  }

  class QueueActor(queueNumber: Int) extends Actor {
    def receive() = {
      case Person(name) => 
        println("Person" + name + " arrives in queue " + queueNumber)
        bodyArray(queueNumber) ! Person(name)
        bagArray(queueNumber) ! Person(name)
    }
  }

  class BagScanActor(queueNumber: Int) extends Actor {
    def receive() = {
      case Person(name) => 
        println("Person" + name + "'s bag arrives at bag scanner " + queueNumber)
        securityArray(queueNumber) ! Person(name)
    }
  }

  class BodyScanActor(queueNumber: Int) extends Actor {
    def receive() = {
      case Person(name) => 
        println("Person" + name + " has arrived at body scanner " + queueNumber)
        securityArray(queueNumber) ! Person(name)
    }
  }

  class SecurityActor(queueNumber: Int) extends Actor {
    def receive() = {
      case Person(name) => 
        println("Person" + name + " has passed security")
    }
  }

  class JailActor extends Actor {
    def receive() = {
      case Person(name) => 
        println("Person" + name + " has been sent to jail and will not pass go or collect $200")
    }
  }

  def main(args: Array[String]) = {
    queueArray = new Array[ActorRef](numLines)
    bagArray = new Array[ActorRef](numLines)
    bodyArray = new Array[ActorRef](numLines)
    securityArray = new Array[ActorRef](numLines)
    for(i <- 0 to (numLines-1)){
      queueArray(i) = system.actorOf(Props(new QueueActor(i)), name = "queue" + i)
      bagArray(i) = system.actorOf(Props(new BagScanActor(i)), name = "bag" + i)
      bodyArray(i) = system.actorOf(Props(new BodyScanActor(i)), name = "body" + i)
      securityArray(i) = system.actorOf(Props(new SecurityActor(i)), name = "security" + i)
    }
    for(a <- 1 to numPassengers){
      docCheck ! Person(a + "")
    }
  }
}