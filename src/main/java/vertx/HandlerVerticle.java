package vertx;

import vertx.function.Handler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class HandlerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        HttpServerOptions serverOptions = new HttpServerOptions();
        serverOptions.setPort(80);

        vertx.createHttpServer(serverOptions)
                .requestHandler(newRouter()::accept)
                .listen();
    }

    @Override
    public void stop() {

    }

    private Router newRouter() {
        Router router = Router.router(vertx);

        Route route = router.route("/").handler(routingContext -> {
            String payload = routingContext.getBodyAsString();
            Handler handler = new Handler();
            String result = handler.Handle(payload.getBytes());

            routingContext.response()
                    .write(result)
                    .setStatusCode(200);

        });

        return router;
    }
}
