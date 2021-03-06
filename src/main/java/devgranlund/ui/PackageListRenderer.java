package devgranlund.ui;

import java.util.List;

/**
 * Render list of package names to HTML. 
 * 
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class PackageListRenderer extends Renderer {

    public static String render(List<String> packageNames) {
        StringBuilder sb = new StringBuilder();
        sb.append(renderPageTop());
        sb.append("<h3>List of packages:</h3>\n");
        for (String packageName : packageNames) {
            sb.append("<a href=\"http://localhost:8080/packages/").append(packageName).append("\">").append(packageName).append("</a><br/>\n");
        }
        sb.append(renderPageBottom());
        return sb.toString();
    }

}
