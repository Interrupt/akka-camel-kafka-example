package controllers

import actors.{KafkaConsumer, KafkaProducer}
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.camel.CamelMessage
import play.api.mvc._

object Application extends Controller {

  val system = ActorSystem("kafka-test-system")

  val kafkaConsumer = system.actorOf(Props[KafkaConsumer])
  val producer = system.actorOf(Props[KafkaProducer])

  def index = Action {
    producer.tell(CamelMessage("""{ "message": "page viewed" }""", Map("kafka.PARTITION_KEY" -> "testkey")), ActorRef.noSender)
    Ok(views.html.index("Your new application is ready."))
  }

}