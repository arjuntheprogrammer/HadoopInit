import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class TestJunit2 {
	String message = "Robert";
	MessageUtil messageUtil = new MessageUtil(message);
	
	@Test
	public void testSaluationMessage() {
		System.out.println("Inside testSaluationMessage()");
		message = "Hi!" + "Robert";
		assertEquals(message, messageUtil.salutationMessage());
	}
}
