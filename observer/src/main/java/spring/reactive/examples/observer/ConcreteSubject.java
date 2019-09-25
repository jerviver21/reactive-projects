package spring.reactive.examples.observer;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcreteSubject implements Subject<String> {
	
	private Set<Observer<String>> observers = new CopyOnWriteArraySet<>(); 
	private ExecutorService es = Executors.newCachedThreadPool();

	public void registerObserver(Observer<String> observer) {
		observers.add(observer);
	}

	public void unregisterObserver(Observer<String> observer) {
		observers.remove(observer);
	}

	public void notifyObservers(String event) {
		observers.forEach(obs -> es.submit(() ->  obs.observe(event)));
	}

}
