package devgranlund.ui;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class PackageListRendererTest {

    @Test
    public void htmlIsRenderedCorrectly() {
        List<String> packageNames = new ArrayList<>();
        packageNames.add("java-commons");
        packageNames.add("less");
        packageNames.add("man");
        packageNames.add("python");

        String html = PackageListRenderer.render(packageNames);
        Assert.assertEquals("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>package-viewer</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>List of packages:</h3>\n" +
                "<a href=\"http://localhost:8080/packages/java-commons\">java-commons</a><br/>\n" +
                "<a href=\"http://localhost:8080/packages/less\">less</a><br/>\n" +
                "<a href=\"http://localhost:8080/packages/man\">man</a><br/>\n" +
                "<a href=\"http://localhost:8080/packages/python\">python</a><br/>\n" +
                "</body>\n" +
                "</html>", html);
    }

}
