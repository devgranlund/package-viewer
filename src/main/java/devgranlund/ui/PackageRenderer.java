package devgranlund.ui;

import devgranlund.domain.InstalledPackage;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-12.
 */
public class PackageRenderer extends Renderer {
    public static String render(InstalledPackage installedPackage) {
        if (installedPackage != null){
            return renderPackageView(installedPackage);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(renderPageTop());
            sb.append("<h3>Package is not installed!</h3>\n");
            sb.append("<br/><a href=\"http://localhost:8080\">All packages</a>");
            sb.append(Renderer.renderPageBottom());
            return sb.toString();
        }
    }
    
    private static String renderPackageView(InstalledPackage installedPackage){
        StringBuilder sb = new StringBuilder();
        sb.append(renderPageTop());
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
        sb.append(Renderer.renderPageBottom());
        return sb.toString();
    }
}
