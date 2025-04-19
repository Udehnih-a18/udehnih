package id.ac.ui.cs.advprog.udehnihh.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest {

    @Test
    void testItung(){
        Example example = new Example(10);
        assertEquals(101, example.getAngka(), "Sengaja dibuat gagal untuk ngetes CI/CD");

    }

}
