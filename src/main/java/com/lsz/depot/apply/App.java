package com.lsz.depot.apply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;


@ServletComponentScan
@SpringBootApplication
@ComponentScan(basePackages = {"com.lsz"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
