package hello;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Temp {
    @Value("${tomek.prop}")
    String fname;

    @PostConstruct
    public void init() {
        System.out.println("*******************"+fname+"**************************");
    }
}
