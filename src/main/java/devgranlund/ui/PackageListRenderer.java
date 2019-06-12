package devgranlund.ui;

import java.util.List;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class PackageListRenderer {
    
    public static String render(List<String> packageNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"en\">\n");
        sb.append("<head>\n");
        sb.append("<meta charset=\"utf-8\">\n");
        sb.append("<title>package-viewer</title>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("<h3>List of packages:</h3>\n");
        for (String packageName : packageNames){
            sb.append("<a href=\"http://localhost:8080/packages/" 
                    + packageName 
                    + "\">"
                    + packageName
                    + "</a><br/>\n");
        }
        sb.append("</body>\n");
        sb.append("</html>");
        return sb.toString();
    }
    
}
