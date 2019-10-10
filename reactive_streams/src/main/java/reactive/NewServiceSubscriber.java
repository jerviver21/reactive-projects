package reactive;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicInteger;

public class NewServiceSubscriber implements Subscriber<NewsLetter>{
	
	final Queue<NewsLetter> mailbox =  new ConcurrentLinkedQueue<>();
	final int take;
	final AtomicInteger remaining  = new AtomicInteger();
	Subscription subscription;
	
	public NewServiceSubscriber(int take) {
		this.take = take;
	}
 
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		this.subscription.request(take);
	}

	@Override
	public void onNext(NewsLetter item) {
		this.mailbox.offer(item);
	}

	@Override
	public void onError(Throwable throwable) {
		System.out.println("Error ************");
	}

	@Override
	public void onComplete() {
		System.out.println("Completed ************");
	}
	
	
	public Optional<NewsLetter> eventuallyReadDigest() {
		NewsLetter letter = mailbox.poll();
		if(letter == null) {
			if(remaining.decrementAndGet() == 0) {
				subscription.request(take);
				remaining.set(take);
			}
			return Optional.of(letter);
		}
		return Optional.empty();
	}

}
