package com.poc.stream;

import java.util.concurrent.CompletionStage;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import akka.Done;
import akka.actor.ActorSystem;
import akka.kafka.ProducerSettings;
import akka.kafka.javadsl.Producer;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Source;

public class KafkStreamDemo {

	public static void main(String ...args){
		ActorSystem system = ActorSystem.create("kafka-stream");
		ActorMaterializer materializer = ActorMaterializer.create(system);
		final ProducerSettings<byte[], String> producerSettings = ProducerSettings
				  .create(system, new ByteArraySerializer(), new StringSerializer())
				  .withBootstrapServers("localhost:9092");
		final CompletionStage<Done> done =
				  Source.range(1, 10)
				    .map(n -> n.toString()).map(elem -> new ProducerRecord<byte[], String>("topic1", elem))
				    .runWith(Producer.plainSink(producerSettings), materializer);
		done.thenAccept(s->System.out.println(s));
	}
}
