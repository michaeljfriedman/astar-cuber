import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	TestCoordinates.class,
	TestCublets.class,
	TestCube.class
})

public class FullTestSuite {}