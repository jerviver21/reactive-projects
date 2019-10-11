package reactive;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class NewServicePublisher implements Publisher<NewsLetter>{
	
	 private final Supplier<Stream<NewsLetter>> streamSupplier;

    public NewServicePublisher(Supplier<Stream<NewsLetter>> streamSupplier) {
        this.streamSupplier = streamSupplier;
    }

	@Override
	public void subscribe(Subscriber<? super NewsLetter> subscriber) {
		try {
			Subscription subscription = new NewsSubscription((Subscriber<NewsLetter>) subscriber, streamSupplier);
			subscriber.onSubscribe(subscription);
        } catch (Throwable e) {
            subscriber.onError(e);
        }
	}

}
