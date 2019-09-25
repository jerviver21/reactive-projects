package spring.reactive.examples.observer;

public class ObserverA implements Observer<String> {

	public void observe(String event) {
		System.out.println("*A*: "+event);
	}
}
