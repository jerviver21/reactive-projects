package reactive.temperature;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import rx.Subscriber;

public class RxSseEmmiter extends SseEmitter {
	
	static final long SSE_SESION_TIMEOUT = 30*60*1000L;
	
	private final Subscriber<Temperature> subscriber;
	
	public RxSseEmmiter() {
		super(SSE_SESION_TIMEOUT);
		
		this.subscriber = new Subscriber<Temperature>() {
			@Override
			public void onNext(Temperature temp) {	
				try {
					RxSseEmmiter.this.send(temp);
				}catch (Exception e) {
					unsubscribe();
				}			
			}
			
			@Override
			public void onError(Throwable e) {}
			
			@Override
			public void onCompleted() {}
		};
		
		onCompletion(subscriber::unsubscribe);
		
		onTimeout(subscriber::unsubscribe);
	}
	
	public Subscriber<Temperature> getSubscriber() {
		return subscriber;
	}

}
