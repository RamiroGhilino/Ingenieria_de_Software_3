package sample.actuator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloWorldServiceTest {

	public static boolean first_time = true;
	
	@Test
	public void gethelloMessageOnce() {
		HelloWorldService helloWorldService = new HelloWorldService();
		
		assertEquals("Hola Hola",helloWorldService.getHelloMessage());
		
		assertEquals("Hello Hello",helloWorldService.getHelloMessage());
	}
}
	

