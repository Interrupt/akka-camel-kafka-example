package actors

import akka.actor.ActorLogging
import akka.camel.{CamelMessage, Consumer}
import play.api.Play

class KafkaConsumer extends Consumer with ActorLogging {

  private val kafkaHost = Play.current.configuration.getString("kafka.host").get
  private val kafkaPort = Play.current.configuration.getString("kafka.port").get
  private val zookeeperHost = Play.current.configuration.getString("zookeeper.host").get
  private val zookeeperPort = Play.current.configuration.getString("zookeeper.port").get
  private val messageBusURL = s"kafka:$kafkaHost:$kafkaPort?topic=test&zookeeperConnect=$zookeeperHost:$zookeeperPort&groupId=fff"

  override def endpointUri: String = messageBusURL

  override def receive: Receive = {
    case message: CamelMessage => {
      log.info(s"Got message: %s" format message.bodyAs[String])
    }
    case _ => { log.info("Unknown message.") }
  }
}