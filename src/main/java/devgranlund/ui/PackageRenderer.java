package devgranlund.ui;

import java.util.Map;

import devgranlund.domain.InstalledPackage;

/**
 * Render the data of one installed package.
 * 
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-12.
 */
public class PackageRenderer extends Renderer {
    public static String render(Map<String, InstalledPackage> domainModel, InstalledPackage installedPackage) {
        if (installedPackage != null) {
            return renderPackageView(domainModel, installedPackage);
        } else {
            return renderPageTop() +
                    "<h3>Package is not installed!</h3>\n" +
                    "<br/><a href=\"http://localhost:8080\">All packages</a>" +
                    Renderer.renderPageBottom();
        }
    }

    private static String renderPackageView(Map<String, InstalledPackage> domainModel, InstalledPackage installedPackage) {
        StringBuilder sb = new StringBuilder();
        sb.append(renderPageTop());
        sb.append("<h3>Package:</h3>\n");
        sb.append("Name: ").append(installedPackage.getName()).append("<br/>");
        sb.append("Description: ").append(installedPackage.getDescription()).append("<br/>");
        sb.append("Depends: ");
        for (String packageName : installedPackage.getDepends()) {
            sb.append(renderDependency(domainModel, packageName));
        }
        sb.append("<br/>Is dependent by: ");
        for (String packageName : installedPackage.getBidirectionalLinks()) {
            sb.append(renderDependency(domainModel, packageName));
        }
        sb.append("<br/><a href=\"http://localhost:8080\">All packages</a>");
        sb.append(Renderer.renderPageBottom());
        return sb.toString();
    }

    private static String renderDependency(Map<String, InstalledPackage> domainModel, String packageName) {
        StringBuilder sb = new StringBuilder();
        if (domainModel.containsKey(packageName)) {
            sb.append("<a href=\"http://localhost:8080/packages/").append(packageName).append("\">").append(packageName).append("</a>&nbsp;");
        } else {
            sb.append(" <strike>").append(packageName).append("</strike> ");
        }
        return sb.toString();
    }
}
