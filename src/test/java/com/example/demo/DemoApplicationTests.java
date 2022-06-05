package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;


    @Container
    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);


    @Container
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @Test
    void contextLoads() {
        Integer devAppMappedPort = devApp.getMappedPort(8080);
        Integer prodAppMappedPort = prodApp.getMappedPort(8081);

        ResponseEntity<String> forDevAppEntity = restTemplate.getForEntity("http://localhost:"
                + devApp.getMappedPort(8080)
                + "/profile", String.class);
        System.out.println(forDevAppEntity.getBody());

        assertEquals(devAppMappedPort, devApp.getMappedPort(8080));

        ResponseEntity<String> forProdAppEntity = restTemplate.getForEntity("http://localhost:"
                + prodApp.getMappedPort(8081)
                + "/profile", String.class);
        System.out.println(forProdAppEntity.getBody());

        assertEquals(prodAppMappedPort, prodApp.getMappedPort(8081));
    }

}
