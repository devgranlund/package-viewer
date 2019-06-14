package devgranlund.ui;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-14.
 */
public class PackageRendererTest {
    
    @Test
    public void installedPackageIsNull(){
        String html = PackageRenderer.render(null, null);
        Assert.assertEquals("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>package-viewer</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>Package is not installed!</h3>\n" +
                "<br/><a href=\"http://localhost:8080\">All packages</a></body>\n" +
                "</html>", html);
    }
    
    
    
}
