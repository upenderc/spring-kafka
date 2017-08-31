package com.tdd.demo;

import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.FiniteDuration;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public class AkkaStreamDemo {

	public static void main(String ...args) {
		final Source<Integer, NotUsed> source = Source.range(1, 100);
		final ActorSystem system = ActorSystem.create("reactive-tweets");
		final Materializer materializer = ActorMaterializer.create(system);
		source.throttle(1, FiniteDuration.apply(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping())
				.runWith(Sink.foreach(a->System.out.println(a)), materializer);
		
	}
}
