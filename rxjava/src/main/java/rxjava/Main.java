package rxjava;

import rx.Observable;

public class Main {

	public static void main(String[] args) {
		Observable<String> observable = Observable.from(new String[] {"A", "B", "C", "D"});

		observable.subscribe(
				v -> System.out.println(v),
				v -> System.out.println(v),
				() -> System.out.println("Done"));
	}

}
