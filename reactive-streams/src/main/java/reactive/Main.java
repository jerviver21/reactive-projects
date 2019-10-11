package reactive;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		Stream<NewsLetter> data = Stream.of(
				new NewsLetter("N1"),
				new NewsLetter("N2"),
				new NewsLetter("N3"),
				new NewsLetter("N4"),
				new NewsLetter("N5"),
				new NewsLetter("N6"));
		Supplier<Stream<NewsLetter>> supplier = () -> data ;
		NewServicePublisher publisher = new NewServicePublisher(supplier);
		
		NewServiceSubscriber subscriber = new NewServiceSubscriber(2);
		
		System.out.println("---- Init ----");
		publisher.subscribe(subscriber);
		subscriber.eventuallyReadDigest();
		subscriber.eventuallyReadDigest();
		subscriber.eventuallyReadDigest();
		subscriber.eventuallyReadDigest();
		subscriber.eventuallyReadDigest();
		subscriber.eventuallyReadDigest();
		System.out.println("---- End ----");
		
		
		

	}

}
