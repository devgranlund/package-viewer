package devgranlund.ui;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-14.
 */
public abstract class Renderer {

    protected static String renderPageTop() {
        String sb = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>package-viewer</title>\n" +
                "</head>\n" +
                "<body>\n";
        return sb;
    }

    protected static String renderPageBottom() {
        String sb = "</body>\n" +
                "</html>";
        return sb;
    }
}
