package devgranlund;

import static io.undertow.Handlers.path;

import java.util.Map;

import devgranlund.domain.InstalledPackage;
import devgranlund.service.PackageService;
import devgranlund.ui.PackageListRenderer;
import devgranlund.ui.PackageRenderer;
import devgranlund.ui.ShutdownRenderer;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-09.
 */
public class WebServer {

    private static String fileName = "status";
    private static boolean runsInProductionMode = false;
    private static final String HEADER_VALUE = "text/html";
    private static Undertow server;

    public static void main(final String[] args) {
        if (args.length > 0 && args[0] != null && args[0].equals("prod")) {
            fileName = "/var/lib/dpkg/status";
            runsInProductionMode = true;
            System.out.println("prod mode");
        } else {
            System.out.println("dev/test mode");
        }

        server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(path()

                        // Shutdown
                        .addPrefixPath("/shutdown", new HttpHandler() {
                            @Override
                            public void handleRequest(HttpServerExchange exchange) throws Exception {
                                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, HEADER_VALUE);
                                exchange.getResponseSender().send(ShutdownRenderer.render());
                                server.stop();
                                System.exit(0);
                            }
                        })

                        // Handler for selected package
                        .addPrefixPath("/packages", new HttpHandler() {
                            @Override
                            public void handleRequest(HttpServerExchange exchange) throws Exception {
                                String packageName = getPackageNameFromPath(exchange.getRequestPath());
                                Map<String, InstalledPackage> domainModel = PackageService.getDomainModel(fileName, runsInProductionMode);
                                InstalledPackage installedPackage = domainModel.get(packageName);
                                String html = PackageRenderer.render(domainModel, installedPackage);

                                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, HEADER_VALUE);
                                exchange.getResponseSender().send(html);
                            }
                        })

                        // Root handler, show full package list
                        .addPrefixPath("/", new HttpHandler() {
                            @Override
                            public void handleRequest(HttpServerExchange exchange) throws Exception {
                                String html = PackageListRenderer.render(PackageService.getPackageNamesInList(fileName, runsInProductionMode));

                                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, HEADER_VALUE);
                                exchange.getResponseSender().send(html);
                            }
                        })
                )
                .build();
        server.start();
    }

    /**
     * Helper-method to parse package name from full path.
     * Expected format is /packages/{packageName}
     *
     * @param path - full path and url path params
     * @return name - name of the package from path params
     */
    protected static String getPackageNameFromPath(String path) {
        String[] parts = path.split("/");
        return parts[2];
    }
}
