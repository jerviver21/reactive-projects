package reactive.temperature;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TemperatureController {
	
	private final TemperatureSensor sensor;
	
	public TemperatureController(TemperatureSensor s) {
		sensor = s;
	}
	
	@RequestMapping(value="/temperature-stream", method=RequestMethod.GET)
	public RxSseEmmiter events(HttpServletRequest request) {
		System.out.println("... Client request ... ");
		RxSseEmmiter emiter = new RxSseEmmiter();
		sensor.temperatureStream().subscribe(emiter.getSubscriber());
		return emiter;
	}

}
