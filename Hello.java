
public class Hello {
	public static void main(String[] args) {
		// to take arguments from the command line
		Integer pin = Integer.parseInt(args[0]);
		GPIO gpio = new GPIO();
		
		System.out.println("BBB test 005");
		//
		if(gpio.isAvailable(pin)){
			System.out.println("pin " + pin + " is checked to be available");
			gpio.pinMode(pin, "out");
			gpio.digitalWrite(pin, true);
			
			System.out.println("check settings by");
			System.out.println("cat /sys/class/gpio/gpio" + pin + "/direction");
			System.out.println("cat /sys/class/gpio/gpio" + pin + "/value");
			System.out.println(gpio.digitalRead(pin));
			
			//gpio.unexportPin(pin);
			
		}
		
		
//		CMDLine cmd = new CMDLine();
//		System.out.println("BBB test 006");
//		
//		String sDate = "20121202 2:2:22AM";
//		cmd.setTime(sDate);
	}
}
