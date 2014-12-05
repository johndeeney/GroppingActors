case class Person(name: String)

class DocumentCheckActor extends Actor with ActorLogging {
  def receive = {
    case Greeting(name) ? log.info(who)
  }
}

class QueueActor extends Actor with ActorLogging {
  def receive = {
    case Greeting(name) ? log.info(who)
  }
}

class BagScanActor extends Actor with ActorLogging {
  def receive = {
    case Greeting(name) ? log.info(who)
  }
}

class BodyScanActor extends Actor with ActorLogging {
  def receive = {
    case Greeting(name) ? log.info(who)
  }
}

class SecurityActor extends Actor with ActorLogging {
  def receive = {
    case Greeting(name) ? log.info(who)
  }
}

class JailActor extends Actor with ActorLogging {
  def receive = {
    case Greeting(name) ? log.info(who)
  }
}