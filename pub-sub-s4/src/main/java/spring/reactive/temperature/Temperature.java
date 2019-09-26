package spring.reactive.temperature;


public class Temperature {
	private double value;
 
	public Temperature(double value) {
		 this.value = value;
	}

	public double getValue() {
		return value;
	}
	
	public void setValue(double v) {
		value=v;
	}
}
