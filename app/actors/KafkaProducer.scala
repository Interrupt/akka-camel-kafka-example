package actors

import akka.camel.javaapi.UntypedProducerActor
import play.api.Play

class KafkaProducer extends UntypedProducerActor {
  private val messageBusURL = Play.current.configuration.getString("bus.test.url").get +
    "&serializerClass=kafka.serializer.StringEncoder"
  override def getEndpointUri(): String = messageBusURL
}
