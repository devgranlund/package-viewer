package devgranlund;

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
                    // Default handler
                    .addPrefixPath("/", new HttpHandler() {
                        @Override
                        public void handleRequest(HttpServerExchange exchange) throws Exception {
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                            exchange.getResponseSender().send("Package listing... \n");
                        }
                    })
                )
                .build();
        server.start();
    }
}
