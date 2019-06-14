package devgranlund;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-11.
 */
public class WebServerTest {

    @Test
    public void packageNameCanBeReturnedFromPath() {
        String path = "/packages/test";
        Assert.assertEquals("test", WebServer.getPackageNameFromPath(path));
    }
}
