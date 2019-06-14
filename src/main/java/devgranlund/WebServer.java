package devgranlund;

import devgranlund.domain.InstalledPackage;
import devgranlund.service.PackageService;
import devgranlund.ui.PackageListRenderer;
import devgranlund.ui.PackageRenderer;
import devgranlund.ui.ShutdownRenderer;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import static io.undertow.Handlers.path;

import java.util.Map;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-09.
 */
public class WebServer {
    
    private static Undertow server;
    
    private static final String LOCAL_FILE_NAME = "status";

    public static void main(final String[] args) {
        server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(path()
                     
                    // Shutdown
                    .addPrefixPath("/shutdown", new HttpHandler() {
                        @Override
                        public void handleRequest(HttpServerExchange exchange) throws Exception {
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
                            exchange.getResponseSender().send(ShutdownRenderer.render());
                            server.stop();
                        }
                    })
                        
                    // Handler for selected package
                    .addPrefixPath("/packages", new HttpHandler() {
                        @Override
                        public void handleRequest(HttpServerExchange exchange) throws Exception {
                            String packageName = getPackageNameFromPath(exchange.getRequestPath());
                            Map<String, InstalledPackage> domainModel = PackageService.getDomainModel(LOCAL_FILE_NAME);
                            InstalledPackage installedPackage = domainModel.get(packageName);
                            String html = PackageRenderer.render(domainModel, installedPackage);
                            
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
                            exchange.getResponseSender().send(html);

                        }
                    })
                        
                    // Root handler, show full package list
                    .addPrefixPath("/", new HttpHandler() {
                        @Override
                        public void handleRequest(HttpServerExchange exchange) throws Exception {
                            String html = PackageListRenderer.render(PackageService.getPackageNamesInList(LOCAL_FILE_NAME));
                            
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
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
     * @param path
     * @return
     */
    protected static String getPackageNameFromPath(String path) {
        String[] parts = path.split("/");
        return parts[2];
    }
}
