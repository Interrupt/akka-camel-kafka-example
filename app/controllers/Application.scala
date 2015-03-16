package controllers

import actors.{KafkaConsumer, KafkaProducer}
import akka.actor.{ActorRef, ActorSystem, Props}
import play.api.mvc._

object Application extends Controller {

  val system = ActorSystem("kafka-test-system")

  val kafkaConsumer = system.actorOf(Props[KafkaConsumer])
  val producer = system.actorOf(Props[KafkaProducer])

  def index = Action {
    producer.tell("""{"message": "PAGE_VIEW", "controller": "index"}""", ActorRef.noSender)
    Ok(views.html.index("Your new application is ready."))
  }

}