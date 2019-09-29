package spring.reactive.temperature;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
public class TemperatureSensor {
	
	private final ApplicationEventPublisher publisher;
	
	private final Random random = new Random();
	
	private final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
	
	public TemperatureSensor(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
	
	@PostConstruct
	public void startProcesing() {
		System.out.println("... starting sensor... ");
		this.ex.schedule(this::probe, 1, TimeUnit.SECONDS);
	}
	
	private void probe() {
		
		double temperature = 16 + random.nextGaussian()*10;
		publisher.publishEvent(new Temperature(temperature));
		ex.schedule(this::probe, 1, TimeUnit.SECONDS);
	}

}
