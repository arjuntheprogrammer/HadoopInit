import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeTestJUnit {

	String message = "Robert";
	MessageUtil messageUtil = new MessageUtil(message);
	
	@Test(timeout = 1000)
	public void testPrintMessage() {
		System.out.println("Inside testPrintMessage()");
		messageUtil.printMessage1();
	}
	
	@Test
	public void testSalutationMessage() {
		System.out.println("Inside Saluation message()");
		message = "Hi!" + "Robert";
		assertEquals(message, messageUtil.salutationMessage());
	}
	
	
}
