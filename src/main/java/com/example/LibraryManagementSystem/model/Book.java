package com.example.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "books")
public class Book {

    // auto increment id for database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unique user-defined book id
    @Column(nullable = false, unique = true)
    private String bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String genre;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

}
