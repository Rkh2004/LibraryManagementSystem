package com.example.LibraryManagementSystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.exception.ResourceAlreadyExistsException;
import com.example.LibraryManagementSystem.exception.ResourceNotFoundException;
import com.example.LibraryManagementSystem.model.AvailabilityStatus;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;  // Mock repository

    @InjectMocks
    private BookService bookService;  // Inject mocks into service

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setBookId("001");
        book.setTitle("Hello");
        book.setAuthor("Rana Khunti");
        book.setGenre("Programming");
        book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

        bookDto = new BookDto("001", "Hello", "Rana Khunti", "Programming", "AVAILABLE");
    }

    // Test: Successfully retrieving all books
    @Test
    void testViewAllBooks() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        List<BookDto> books = bookService.viewAllBooks();

        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals("Java Basics", books.get(0).getTitle());
    }

    //  Test: Successfully adding a book
    @Test
    void testAddBook_Success() {
        when(bookRepository.existsBybookId(bookDto.getBookId())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto savedBook = bookService.addBook(bookDto);

        assertNotNull(savedBook);
        assertEquals("001", savedBook.getBookId());
    }

    // Test: Adding a duplicate book should throw exception
    @Test
    void testAddBook_DuplicateBookId() {
        when(bookRepository.existsBybookId(bookDto.getBookId())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> bookService.addBook(bookDto));
    }

    // Test: Adding a book with missing title should throw exception
    @Test
    void testAddBook_MissingTitle() {
        bookDto.setTitle(null);

        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(bookDto));
    }

    // Test: Searching a book by ID
    @Test
    void testSearchBookById_Success() {
        when(bookRepository.findByBookId("001")).thenReturn(Optional.of(book));

        List<BookDto> result = bookService.searchBooks("001", null);

        assertEquals(1, result.size());
        assertEquals("Java Basics", result.get(0).getTitle());
    }

    // Test: Searching a book that does not exist
    @Test
    void testSearchBookById_NotFound() {
        when(bookRepository.findByBookId("001")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.searchBooks("999", null));
    }

    // Test: Updating an existing book
    @Test
    void testUpdateBook_Success() {
        when(bookRepository.findByBookId("001")).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto updatedBook = new BookDto("001", "Advanced Java", "Rana Khunti", "Programming", "AVAILABLE");
        BookDto result = bookService.updateBook("001", updatedBook);

        assertEquals("Advanced Java", result.getTitle());
    }

    // Test: Updating a book that does not exist
    @Test
    void testUpdateBook_NotFound() {
        when(bookRepository.findByBookId("999")).thenReturn(Optional.empty());

        BookDto updatedBook = new BookDto("999", "Advanced Java", "Rana Khunti", "Programming", "AVAILABLE");

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook("999", updatedBook));
    }

    // Test: Deleting an existing book
    @Test
    void testDeleteBook_Success() {
        when(bookRepository.findByBookId("001")).thenReturn(Optional.of(book));

        bookService.deleteBook("001");

        verify(bookRepository, times(1)).delete(book);
    }

    // Test: Deleting a book that does not exist
    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.findByBookId("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook("999"));
    }
}

