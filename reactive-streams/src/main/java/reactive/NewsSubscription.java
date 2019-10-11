package reactive;

import java.util.Iterator;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Stream;
public class NewsSubscription  implements Subscription{
	
	Subscriber<NewsLetter> subscriber;
	private final Iterator<NewsLetter> iterator;
	private final AtomicBoolean isTerminated = new AtomicBoolean(false);
	
	public NewsSubscription(Subscriber<NewsLetter> subscriber, Supplier<Stream<NewsLetter>> streamSupplier) {
		this.subscriber = subscriber;
		this.iterator = streamSupplier.get().iterator();
	}

	@Override
	public void request(long n) {
		for (long l = n; l > 0 && iterator.hasNext() && !isTerminated(); l--) {
            subscriber.onNext(iterator.next());
        }

        if (!iterator.hasNext() && !terminate()) {
            subscriber.onComplete();
        }
	}
	
	@Override
    public void cancel() {
        terminate();
    }

    private boolean terminate() {
        return isTerminated.getAndSet(true);
    }

    private boolean isTerminated() {
        return isTerminated.get();
    }

}
