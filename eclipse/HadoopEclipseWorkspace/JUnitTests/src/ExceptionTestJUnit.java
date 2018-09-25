import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExceptionTestJUnit {
	String message = "Robert";
	MessageUtil messageUtil = new MessageUtil(message);
	
	@Test(expected = ArithmeticException.class)
	public void testPrintMessage() {
		System.out.println("Inside TestPrintMessage()");
		messageUtil.printMessage2();
	}
	
	@Test
	public void testSalutationMessage() {
		System.out.println("Inside Saluation message()");
		message = "Hi!" + "Robert";
		assertEquals(message, messageUtil.salutationMessage());
	}

}
