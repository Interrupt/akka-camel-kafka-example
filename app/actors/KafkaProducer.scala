package actors

import akka.actor.ActorLogging
import akka.camel.CamelMessage
import akka.camel.javaapi.UntypedProducerActor
import play.api.Play

class KafkaProducer extends UntypedProducerActor with ActorLogging {

  private val kafkaHost = Play.current.configuration.getString("kafka.host").get
  private val kafkaPort = Play.current.configuration.getString("kafka.port").get
  private val zookeeperHost = Play.current.configuration.getString("zookeeper.host").get
  private val zookeeperPort = Play.current.configuration.getString("zookeeper.port").get
  private val messageBusURL =
    s"kafka:$kafkaHost:$kafkaPort?topic=test&zookeeperConnect=$zookeeperHost:$zookeeperPort&groupId=fff" +
    "&serializerClass=kafka.serializer.StringEncoder"

  private val defaultHeaders = Map("kafka.PARTITION_KEY" -> "")

  override def getEndpointUri(): String = messageBusURL

  override def onTransformOutgoingMessage(msg: AnyRef): AnyRef = {
    msg match {
      case m: String => { CamelMessage(m, defaultHeaders) }
      case camel: CamelMessage => { camel }
      case default: Any => CamelMessage(default.toString, defaultHeaders)
    }
  }
}