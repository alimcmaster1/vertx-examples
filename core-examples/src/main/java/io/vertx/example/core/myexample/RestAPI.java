
ckage io.vertx.example.myexamples;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.function.Consumer;

/**
 * Created by alistair on 02/02/18.
 */
public class RestAPI extends AbstractVerticle {

    public static void main(String [] args)
    {
        VertxOptions options = new VertxOptions();
        String verticle = "io.vertx.example.myexamples.RestAPI";

        Consumer<Vertx> runner = vertx -> {
            try {
                vertx.deployVerticle(verticle);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };
        Vertx vertx = Vertx.vertx(options);
        runner.accept(vertx);
    }

    @Override
    public void start() throws Exception{

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/param/:paramOne/:paramTwo").handler(this::handlerOne);
        vertx.createHttpServer().requestHandler(router::accept).listen(9090);

    }

    private void handlerOne(RoutingContext routingContext)
    {
        String paramOne = routingContext.request().getParam("paramOne");
        String paramTwo = routingContext.request().getParam("paramTwo");

        System.out.println(paramOne);
        HttpServerResponse response = routingContext.response();

        response.putHeader("content-type", "text/plain");
        response.end(String.format("param: %s, param: %s", paramOne, paramTwo));


    }

}

