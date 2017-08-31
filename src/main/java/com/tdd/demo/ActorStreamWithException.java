package com.tdd.demo;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.japi.function.Function;
import akka.stream.ActorAttributes;
import akka.stream.ActorMaterializer;
import akka.stream.Supervision;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public class ActorStreamWithException {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("Stream-Exception");
		final ActorMaterializer materializer=ActorMaterializer.create(system);
		final Function<Throwable, Supervision.Directive> decider = ex -> {
			if (ex instanceof ArithmeticException) {
				System.out.println("exception occurred");
			    return Supervision.resume();
			}
			  else
			    return Supervision.stop();
			
		};
		final Flow<Integer, Integer, NotUsed> flow =
			    Flow.of(Integer.class).filter(elem -> 100 / elem < 50).map(elem -> 100 / (5 - elem))
			    .withAttributes(ActorAttributes.withSupervisionStrategy(decider));
		Source<Integer, NotUsed> nbr = Source.from(Arrays.asList(0,1,2,3,4,5)).via(flow);
		final Sink<Integer, CompletionStage<Integer>> fold =
				  Sink.<Integer, Integer> fold(0, (acc, elem) -> acc + elem);
				final CompletionStage<Integer> result = nbr.runWith(fold, materializer);
				result.thenAccept(s->System.out.println(s));

	}
	

}
