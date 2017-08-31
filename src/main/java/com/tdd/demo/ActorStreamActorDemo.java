package com.tdd.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import akka.Done;
import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.PatternsCS;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.Timeout;

public class ActorStreamActorDemo {

	public static void main(String...args) {
		final ActorSystem system = ActorSystem.create("reactive-tweets");
		final Materializer materializer = ActorMaterializer.create(system);
		ActorRef actorRef =system.actorOf(Props.create(StreamActor.class, "streamActor"));
		Source<String, NotUsed> words =
				  Source.from(Arrays.asList("hello", "hi","upender"));
				Timeout askTimeout = Timeout.apply(5, TimeUnit.SECONDS);
				List<String> lst =new ArrayList<String>();
				
				final CompletionStage<List<String>>cm=words
						//.actorRef(5, OverflowStrategy.dropBuffer())
				  .mapAsync(5, elem -> PatternsCS.ask(actorRef, elem, askTimeout))
				  .map(elem -> (String) elem)
				  // continue processing of the replies from the actor
				  //.map(elem -> elem.toLowerCase())
				  .runWith(Sink.<String>seq(), materializer);
				cm.thenAccept(s->System.out.println(s));
				
				 
	}
}
class StreamActor  extends AbstractActor{

	public StreamActor(String name) {
		System.out.println(name);
	}
	@Override
	public Receive createReceive() {
		
		return receiveBuilder()
			      .match(String.class, word -> {
			        // ... process message
			        String reply = word.toUpperCase();
			        try {
			        	if (word.equalsIgnoreCase("upender"))
			        	TimeUnit.SECONDS.sleep(1);
			        }catch(Exception e) {
			        	System.out.println(e);
			        }
			        // reply to the ask
			        getSender().tell(reply, getSelf());
			      })
			      .build();
	}
	
}