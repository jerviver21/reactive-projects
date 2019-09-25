package spring.reactive.examples.observer;

public class ObserverB implements Observer<String> {

	public void observe(String event) {
		System.out.println("*B*: "+event);
	}
}