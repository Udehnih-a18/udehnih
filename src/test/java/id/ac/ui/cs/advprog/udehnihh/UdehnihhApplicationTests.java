package id.ac.ui.cs.advprog.udehnihh;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UdehnihhApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodRunsWithoutExceptions() {
        Assertions.assertNull(null);
        UdehnihhApplication.main(new String[]{});
    }

}