package devgranlund;

import devgranlund.service.PackageService;
import devgranlund.ui.PackageListRenderer;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import static io.undertow.Handlers.path;

import java.util.List;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-09.
 */
public class WebServer {
    
    private static Undertow server;

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
                        
                    // Root handler, show full package list
                    .addPrefixPath("/", new HttpHandler() {
                        @Override
                        public void handleRequest(HttpServerExchange exchange) throws Exception {
                            // TODO fix filename
                            String html = PackageListRenderer.render(PackageService.getPackageNamesInList("status"));
                            
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
                            exchange.getResponseSender().send(html);
                        }
                    })
                )
                .build();
        server.start();
    }
}
