package reactive.temperature;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import rx.Observable;


@Component
public class TemperatureSensor {
	
	private final Random random = new Random();
	
	private final Observable<Temperature> datastream = Observable
			.range(0, Integer.MAX_VALUE)
			.concatMap(tick -> 
				Observable.just(tick)
				.delay(random.nextInt(5000), TimeUnit.MILLISECONDS)
				.map(value -> this.probe()))
			.publish()
			.refCount();
	
	//Operator delay: solo aplica el delay una vez por una 
	//cantidad de tiempo, antes de emitir todos los valores del stream.
	
	//publish: Permite muchos subscribers se peguen de la misma fuente.. de otra forma
	//es como por cada subscriber tener una sola fuente que puede generar problemas de performance
	
	//refcount: Solo crea la subscripcion en caso de que haya al menos un subscritor.

	
	private Temperature probe() {	
		double temperature = 16 + random.nextGaussian()*10;
		return new Temperature(temperature);
	}
	
	public Observable<Temperature> temperatureStream() {
		return datastream;
	}

}
