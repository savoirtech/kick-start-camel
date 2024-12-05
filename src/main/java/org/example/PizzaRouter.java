package org.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PizzaRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:{{file.input.directory}}?noop=true")
                .routeId("pizzaOrderFileRoute")
                .log("Reading file: ${header.CamelFileName}")
                .log("Reading body of file: ${body}")
                .split(body().tokenize("\n")).streaming()
                .log("Processing order: ${body}")
                .to("kafka:{{pizza.order.kafka.topic}}?brokers=localhost:9092")
                .end()
                .log("Finished processing file: ${header.CamelFileName}");
    }
}
