package devgranlund.ui;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-14.
 */
public class ShutdownRenderer extends Renderer {

    public static String render(){
        StringBuilder sb = new StringBuilder();
        sb.append(renderPageTop());
        sb.append("<h3>Server shutdown, all hope is lost.</h3>\n");
        sb.append(renderPageBottom());
        return sb.toString();
    }
    
}
