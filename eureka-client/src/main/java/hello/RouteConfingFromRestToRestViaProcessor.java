
package hello;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cloud.ServiceDefinition;
import org.apache.camel.cloud.ServiceDiscovery;
import org.apache.camel.impl.cloud.DefaultServiceDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
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
    DiscoveryClient client;

    @Bean
    public ServiceDiscovery getServiceDiscovery(){
        ServiceDiscovery serviceDiscovery = new ServiceDiscovery() {
            @Override
            public List<ServiceDefinition> getServices(String name) {
                List<ServiceInstance> instances = client.getInstances(name);
                List<ServiceDefinition> defintions = new ArrayList<ServiceDefinition>();
                for(ServiceInstance sI: instances){
                    DefaultServiceDefinition aDef = new DefaultServiceDefinition(name, sI.getHost(), sI.getPort());
                    defintions.add(aDef);
                }
                return defintions;
            }
        };
        return serviceDiscovery;
    }

    @Bean(name = "routeBuilder")
    public RouteBuilder route() {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("direct:callMe")
                .   serviceCall().ribbonLoadBalancer().serviceDiscovery(getServiceDiscovery())
                .    name("EUREKACLIENT")
                .expression()
                .simple("http4:${header.CamelServiceCallServiceHost}:${header.CamelServiceCallServicePort}/ack").end();
            }
        };
    }
}
