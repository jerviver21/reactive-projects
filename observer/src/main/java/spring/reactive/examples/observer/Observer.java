package spring.reactive.examples.observer;

public interface Observer<T> {
	void observe(T event);
}
