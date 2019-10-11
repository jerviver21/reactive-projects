package reactive;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
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
            Stream<NewsLetter> stream = streamSupplier.get();
            stream.forEach(subscriber::onNext);
            subscriber.onComplete();
        } catch (Throwable e) {
            subscriber.onError(e);
        }
	}

}
