package actors

import akka.actor.ActorLogging
import akka.camel.{CamelMessage, Consumer}
import play.api.Play

class KafkaConsumer extends Consumer with ActorLogging {
  private val messageBusURL = Play.current.configuration.getString("bus.test.url").get

  override def endpointUri: String = messageBusURL

  override def receive: Receive = {
    case message: CamelMessage => {
      log.info(s"Got message: %s" format message.bodyAs[String])
    }
    case _ => { log.info("Unknown message.") }
  }
}