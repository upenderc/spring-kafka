package com.tdd.demo;

import java.util.Arrays;


import java.util.List;
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

public class ActorStreamExceptionTwo {

	public static void main(String ... args) {
		//ActorSystem
		final ActorSystem system = ActorSystem.create("Akka_Exception");
		final ActorMaterializer materializer=ActorMaterializer.create(system);
		//Decider
		final Function<Throwable, Supervision.Directive> decider=exc-> {
			if (exc instanceof IllegalArgumentException)
				return Supervision.restart();
			else
				return Supervision.stop();
		};
		//Flow
		final Flow<Integer, Integer,NotUsed> flow =
				Flow.of(Integer.class).scan(0,(nbr, e)->{
					if (e<0) {
						throw new IllegalArgumentException("negative value not allowed");
					} else 
						return nbr+e;
				}).withAttributes(ActorAttributes.withSupervisionStrategy(decider));
		//Source
		final Source<Integer, NotUsed> sourceList = Source.from(Arrays.asList(1,3,-1,5,7)).via(flow);
		//Sink
		final CompletionStage<List<Integer>> stage=sourceList.grouped(10).runWith(Sink.<List<Integer>>head(), materializer);
		stage.thenAccept(s->{s.forEach(System.out::println);});
		
	
	}

}
