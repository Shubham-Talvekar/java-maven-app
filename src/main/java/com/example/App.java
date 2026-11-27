
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public static int add(int a, int b) {
        return a + b;
    }

    @GetMapping("/")
    public String home() {

        int result = add(10, 20);

        return """
                <html>
                <head>
                    <title>Jenkins Maven Project</title>
                </head>
                <body style="font-family: Arial; text-align: center; padding: 50px;">
                    <h1>Hello from Spring Boot!</h1>
                    <h2>Maven Jenkins Project-3</h2>
                    <h3>Addition result: %d</h3>
                    <p>Successfully deployed using Jenkins and Docker!</p>
                </body>
                </html>
                """.formatted(result);
    }
}
