import org.junit.*;

public class JunitAnnotation {
	
	@BeforeClass
	public static void beforeClass() {
		System.out.println("in before class");
	}
	
	@AfterClass
	public static void afterClass() {
		System.out.println("in after class");
	}
	
	@Before
	public void before() {
		System.out.println("in before ");
	}
	
	@After
	public void after() {
		System.out.println("in after");
	}
	
	@Test
	public void test1() {
		System.out.println("in test1");
	}
	
	@Test
	public void test2() {
		System.out.println("in test2");
	}
	
	@Ignore
	public void ignore() {
		System.out.println("in ignore test");
	}
	
	
	
}
