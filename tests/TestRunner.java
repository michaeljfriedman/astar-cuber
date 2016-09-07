import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

	public static void main(String[] args) {
		System.out.println("Running tests...");
		Result result = JUnitCore.runClasses(FullTestSuite.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure);
		}

		if (result.wasSuccessful()) {
			System.out.println("Tests completed successfully.");
		}
	}
}