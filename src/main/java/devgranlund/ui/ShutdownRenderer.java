package devgranlund.ui;

/**
 * Renders HTML page to show after server shutdown.
 * 
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-14.
 */
public class ShutdownRenderer extends Renderer {

    public static String render() {
       return renderPageTop() +
                "<h3>Server shutdown, all hope is lost.</h3>\n" +
                renderPageBottom();
    }
}
