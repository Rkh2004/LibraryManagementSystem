package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.model.AvailabilityStatus;
import com.example.LibraryManagementSystem.model.Book;

public class BookMapper {

    public static BookDto toDto(Book book){

        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getAvailabilityStatus().name()
        );

    }
    public static Book toEntity(BookDto bookDto){
        Book book = new Book();
        book.setBookId(bookDto.getBookId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        // convert the string datatype of dto field into enum
        if (bookDto.getAvailabilityStatus() != null) {
            book.setAvailabilityStatus(AvailabilityStatus.valueOf(bookDto.getAvailabilityStatus().toUpperCase()));
        } else {
            book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE); // Default value
        }

        return book;
    }
}
