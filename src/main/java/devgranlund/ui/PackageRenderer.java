package devgranlund.ui;

import devgranlund.domain.InstalledPackage;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-12.
 */
public class PackageRenderer {
    public static String render(InstalledPackage installedPackage) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"en\">\n");
        sb.append("<head>\n");
        sb.append("<meta charset=\"utf-8\">\n");
        sb.append("<title>package-viewer</title>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("<h3>Package:</h3>\n");
        sb.append("Name: " + installedPackage.getName() + "<br/>");
        sb.append("Description: " + installedPackage.getDescription() + "<br/>");
        sb.append("Depends: ");
        for (String packageName : installedPackage.getDepends()){
            sb.append("<a href=\"http://localhost:8080/packages/"
                    + packageName
                    + "\">"
                    + packageName
                    + "</a>&nbsp;");
        }
        sb.append("<br/><a href=\"http://localhost:8080\">All packages</a>");
        sb.append("</body>\n");
        sb.append("</html>");
        return sb.toString();
    }
}
