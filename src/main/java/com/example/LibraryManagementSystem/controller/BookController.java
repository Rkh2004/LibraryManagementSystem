package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books API", description = "Manage books in the library")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "View All books", description = "Fetches the list of all books")
    @GetMapping
    ResponseEntity<List<BookDto>> viewAllBooks(){
        return ResponseEntity.ok(bookService.viewAllBooks());
    }

    @Operation(summary = "Add a new book", description = "Adds a book to the library database")
    @PostMapping
    ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto){
        return ResponseEntity.ok(bookService.addBook(bookDto));
    }

    // return type is List of book DTOs because searching by title can give multiple books as result.
    @Operation(summary = "Search books", description = "Find books by Book ID or Title")
    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooks(
            @RequestParam(required = false) String bookId,
            @RequestParam(required = false) String title) {

        List<BookDto> books = bookService.searchBooks(bookId, title);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Update book details", description = "Modify book details")
    @PatchMapping("/{bookId}")
    public ResponseEntity<BookDto> updateBook(@PathVariable String bookId, @RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.updateBook(bookId, bookDto);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Delete a book", description = "Removes a book from the catalog")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok("Book deleted successfully");
    }

}
