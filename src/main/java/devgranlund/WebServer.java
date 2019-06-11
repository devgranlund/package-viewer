package devgranlund;

import devgranlund.service.PackageService;
import devgranlund.ui.PackageListRenderer;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import static io.undertow.Handlers.path;

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
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                            exchange.getResponseSender().send("Server shutting down... \n");
                            server.stop();
                        }
                    })
                        
                    // Handler for selected package
                    .addPrefixPath("/packages", new HttpHandler() {
                        @Override
                        public void handleRequest(HttpServerExchange exchange) throws Exception {
                            String packageName = getPackageNameFromPath(exchange.getRequestPath());
                            
                            // 1. package service palauttaa mapin domain olioita :)
                            // 2. HTML-renderer luo sivun valitun domain olion tiedoista
                            
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                            exchange.getResponseSender().send("Package: " + packageName + " \n");

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
