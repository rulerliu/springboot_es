package com.mayikt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootEsApplicationTests {

    @Value("${my.name}")
    private String name;

    @Test
    void contextLoads() {
        System.out.println(name);
    }

}
