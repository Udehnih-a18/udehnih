package id.ac.ui.cs.advprog.udehnihh.model;

import org.springframework.security.core.userdetails.User;

import java.util.List;

public class Course {
    private Long id;
    private String name;
    private String description;
    private User tutor;
    private Double price;
    private List<Section> sections;

    // Getters, setters, constructors
}