package com.example.LibraryManagementSystem.dto;

import com.example.LibraryManagementSystem.model.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String bookId;
    private String title;
    private String author;
    private String genre;
    private String availabilityStatus;

}
