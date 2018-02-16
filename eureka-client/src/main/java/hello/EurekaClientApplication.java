package hello;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = { "hello" })
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
}

@RestController
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping(value = "/initialInput", produces = { "application/json" })
    public String postMe(@RequestBody String input) {


        String httpresponse = producerTemplate.requestBody("direct:callMe",input, String.class);
        return httpresponse;

    }

    @PostMapping(value = "/ack", produces = { "application/json" })
    public String ack(@RequestBody String inputToBeAcked) {
        System.out.println("Message acknowleged ok:" + inputToBeAcked);
        return "Acking: "+inputToBeAcked;
    }
}
