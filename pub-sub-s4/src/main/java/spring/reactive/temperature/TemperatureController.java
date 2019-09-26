package spring.reactive.temperature;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class TemperatureController {
	
	private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();
	
	@RequestMapping(value="/temperature-stream", method=RequestMethod.GET)
	public SseEmitter events(HttpServletRequest request) {
		System.out.println("... Client request ... ");
		SseEmitter emiter = new SseEmitter();
		clients.add(emiter);
		emiter.onTimeout(() -> clients.remove(emiter));
		emiter.onCompletion(() -> clients.remove(emiter));
		return emiter;
	}
	
	@Async
	@EventListener
	public void handleMessage(Temperature temperature) {
		List<SseEmitter> deadEmiters = new ArrayList<>();
		clients.forEach(e -> {
			try {
				System.out.println("... Receiving data from sensor ... "+temperature.getValue());
				e.send(temperature, MediaType.APPLICATION_JSON);
			}catch (Exception ex) {
				deadEmiters.add(e);
			}
		});
		
		clients.removeAll(deadEmiters);
	}

}
