import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
//      Result result = JUnitCore.runClasses(TestJunit.class);
//      Result result = JUnitCore.runClasses(JavaTest.class);
//      Result result = JUnitCore.runClasses(JunitTestSuite.class);
//	   Result result = JUnitCore.runClasses(TestEmployeeDetails.class);
//	   Result result = JUnitCore.runClasses(TestAssertions.class);
//	   Result result = JUnitCore.runClasses(JunitAnnotation.class);
	   Result result = JUnitCore.runClasses(TimeTestJUnit.class);

		
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
} 