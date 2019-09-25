package spring.reactive.examples.observer;

public class Main {
	
	public static void main(String[] args) {
		Subject<String> subject = new ConcreteSubject();
		Observer<String> a = new ObserverA();
		Observer<String> b = new ObserverB();
		
		subject.notifyObservers("No Listeners...");
		
		subject.registerObserver(a);
		subject.notifyObservers("First Message...");
		subject.registerObserver(b);
		subject.notifyObservers("Second Message...");
		subject.registerObserver(v -> System.out.println("*C*: "+v));
		subject.notifyObservers("Third Message...");
		
		
	}

}
