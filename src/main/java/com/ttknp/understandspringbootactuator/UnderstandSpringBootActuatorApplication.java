package com.ttknp.understandspringbootactuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// The @SpringBootApplication annotation provides a load of defaults (like the embedded servlet container), ** It also turns on Spring MVC’s @EnableWebMvc annotation
@SpringBootApplication
public class UnderstandSpringBootActuatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(UnderstandSpringBootActuatorApplication.class, args);
    }
}
