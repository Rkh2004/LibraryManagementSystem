package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.exception.ResourceAlreadyExistsException;
import com.example.LibraryManagementSystem.mapper.BookMapper;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public BookDto addBook(BookDto bookDto){
        // checks if the tile is not empty
        if (bookDto.getTitle() == null || bookDto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        // checks if the author field is not empty
        if (bookDto.getAuthor() == null || bookDto.getAuthor().isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        // checks if the book id does not already exist
        if (bookRepository.existsBybookId(bookDto.getBookId())) {
            throw new ResourceAlreadyExistsException("Book with this ID already exists");
        }

        return BookMapper.toDto(bookRepository.save(BookMapper.toEntity(bookDto)));
    }


}
