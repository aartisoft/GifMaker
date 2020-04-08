package maintestpackage;

import org.junit.Test;
import mainpackage.Main;
import static org.junit.Assert.*;

public class MainTest {
	@Test public void testTheTestMethod() {
        Main classUnderTest = new Main();
//        assertTrue("someLibraryMethod should return 'true'", classUnderTest.testThisMethod());
        assertSame("some message", "arbitrary string", classUnderTest.testThisMethod());
    }
}
