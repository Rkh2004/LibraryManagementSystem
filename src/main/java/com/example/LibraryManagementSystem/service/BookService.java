package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.exception.ResourceAlreadyExistsException;
import com.example.LibraryManagementSystem.exception.ResourceNotFoundException;
import com.example.LibraryManagementSystem.mapper.BookMapper;
import com.example.LibraryManagementSystem.model.AvailabilityStatus;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books from the database.
     *
     * @return List of BookDto objects
     */
    public List<BookDto> viewAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new book to the database.
     *
     * @param bookDto The book details provided by the user.
     * @return The saved BookDto.
     * @throws IllegalArgumentException If required fields are missing.
     * @throws ResourceAlreadyExistsException If a book with the same bookId already exists.
     */
    public BookDto addBook(BookDto bookDto) {
        validateBookDto(bookDto); // ✅ Extracted validation logic to a separate method

        if (bookRepository.existsBybookId(bookDto.getBookId())) {
            throw new ResourceAlreadyExistsException("Book with ID " + bookDto.getBookId() + " already exists");
        }

        return BookMapper.toDto(bookRepository.save(BookMapper.toEntity(bookDto)));
    }

    /**
     * Searches for a book by bookId or title.
     *
     * @param bookId The bookId provided by the user (optional).
     * @param title The title of the book (optional).
     * @return List of matching books.
     * @throws ResourceNotFoundException If no book is found.
     * @throws IllegalArgumentException If both bookId and title are missing.
     */
    public List<BookDto> searchBooks(String bookId, String title) {
        if (bookId != null) {
            return bookRepository.findByBookId(bookId)
                    .map(BookMapper::toDto)
                    .map(Collections::singletonList) // convert to singletonList because searching by id will give on ly one result
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found with Book ID: " + bookId));
        } else if (title != null) {
            List<BookDto> books = bookRepository.findByTitleContainingIgnoreCase(title)
                    .stream()
                    .map(BookMapper::toDto)
                    .collect(Collectors.toList());

            if (books.isEmpty()) {
                throw new ResourceNotFoundException("No books found with title: " + title);
            }

            return books;
        }
        throw new IllegalArgumentException("At least one parameter (bookId or title) must be provided");
    }

    /**
     * Updates an existing book's details.
     *
     * @param bookId The unique book ID.
     * @param bookDto The new details of the book.
     * @return The updated BookDto.
     * @throws ResourceNotFoundException If the book is not found.
     */
    public BookDto updateBook(String bookId, BookDto bookDto) {
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with Book ID: " + bookId));

        // Only update fields that are provided
        if (bookDto.getTitle() != null && !bookDto.getTitle().isBlank()) {
            book.setTitle(bookDto.getTitle());
        }
        if (bookDto.getAuthor() != null && !bookDto.getAuthor().isBlank()) {
            book.setAuthor(bookDto.getAuthor());
        }
        if (bookDto.getAvailabilityStatus() != null) {
            book.setAvailabilityStatus(AvailabilityStatus.valueOf(bookDto.getAvailabilityStatus().toUpperCase()));
        }

        return BookMapper.toDto(bookRepository.save(book));
    }

    /**
     * Deletes a book by its bookId.
     *
     * @param bookId The unique book ID.
     * @throws ResourceNotFoundException If the book is not found.
     */
    public void deleteBook(String bookId) {
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with Book ID: " + bookId));
        bookRepository.delete(book);
    }

    /**
     * Helper method: Validates a BookDto to ensure required fields are present.
     *
     * @param bookDto The book data to validate.
     * @throws IllegalArgumentException If required fields are missing.
     */
    private void validateBookDto(BookDto bookDto) {

        // Assumption: we assume that Activity Status will be selected via radio button, drop down menu or any similar things so we don't write exception handling for it.

        if (bookDto.getTitle() == null || bookDto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (bookDto.getAuthor() == null || bookDto.getAuthor().isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        if (bookDto.getBookId() == null || bookDto.getBookId().isBlank()) {
            throw new IllegalArgumentException("Book ID cannot be empty");
        }
    }
}
