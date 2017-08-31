package com.tdd.demo;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public class AkkaStreamFlow {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("reactive-tweets");
		final Materializer materializer = ActorMaterializer.create(system);
		final Source<Integer, NotUsed> source = Source.from(Arrays.asList(10,20,30,40,50));
		final Sink<Integer, CompletionStage<Integer>> sink = Sink.<Integer,Integer>fold(0, (agr,nxt)->agr+nxt);
		final CompletionStage<Integer> sum=source.runWith(sink, materializer);
		sum.thenAccept(s->System.out.println(" Total sum of "+s));
	}

}
