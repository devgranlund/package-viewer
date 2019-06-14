package devgranlund.ui;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-14.
 */
public abstract class Renderer {
    
    protected static String renderPageTop(){
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"en\">\n");
        sb.append("<head>\n");
        sb.append("<meta charset=\"utf-8\">\n");
        sb.append("<title>package-viewer</title>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        return sb.toString();
    }
    
    protected static String renderPageBottom(){
        StringBuilder sb = new StringBuilder();
        sb.append("</body>\n");
        sb.append("</html>");
        return sb.toString();
    }
}
