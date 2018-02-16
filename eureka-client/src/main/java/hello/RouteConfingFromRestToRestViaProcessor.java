
package hello;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A Camel route in Spring Boot.
 *
 * Notice that we use @Component on the class to make the route automatic
 * discovered by Spring Boot
 *
 * This is a sample route the aim is to take a message, process it via processor
 * and the post to configured http endpoint. (default to system.out)
 *
 * The HTTP endpoint to where the payload should go is specified from property
 * file
 *
 */
@Configuration
public class RouteConfingFromRestToRestViaProcessor {


    @Autowired
    DiscoveryClient discClient;

    @Bean(name = "routeBuilder")
    public RouteBuilder route() {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("direct:callMe")
                .serviceCall().name("EUREKACLIENT").serviceDiscovery("discClient")
                .expression()
                .simple("http4:${header.CamelServiceCallServiceHost}:${header.CamelServiceCallServicePort}/ack").end();
            }
        };
    }
}
