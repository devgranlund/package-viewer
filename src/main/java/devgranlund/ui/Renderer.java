package devgranlund.ui;

/**
 * Base class for HTML rendering. 
 * 
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-14.
 */
public abstract class Renderer {
    
    protected static String renderPageTop() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>package-viewer</title>\n" +
                "</head>\n" +
                "<body>\n";
    }

    protected static String renderPageBottom() {
        return"</body>\n" +
                "</html>";
    }
}
