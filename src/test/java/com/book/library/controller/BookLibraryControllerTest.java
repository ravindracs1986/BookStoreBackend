package com.book.library.controller;



import com.book.library.model.Books;
import com.book.library.model.Users;
import com.book.library.service.BookLibraryService;
import com.book.library.repository.UserRepository;
import com.book.library.utils.ErrorMessage;
import com.book.library.utils.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookLibraryControllerTest {

    @InjectMocks
    BookLibraryController bookLibraryController;

    @Mock
    BookLibraryService bookLibraryService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooksSuccess() throws ResourceNotFoundException {
        List<Books> booksList = new ArrayList<>();
        Books book = new Books();
        book.setId(1L);
        book.setName("Java Programming");
        booksList.add(book);

        when(bookLibraryService.getAllBooks()).thenReturn(booksList);

        ResponseEntity<?> response = bookLibraryController.getAllBooks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(booksList, response.getBody());
    }

    @Test
    void testGetAllBooksNoContent() throws ResourceNotFoundException {
        when(bookLibraryService.getAllBooks()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = bookLibraryController.getAllBooks();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        ErrorMessage errorMessage = (ErrorMessage) response.getBody();
        assertEquals(204, errorMessage.getStatusCode());
    }

    @Test
    void testGetBookByNameSuccess() throws ResourceNotFoundException {
        Books book = new Books();
        book.setId(1L);
        book.setName("Java Programming");

        when(bookLibraryService.getBooksByName("Java Programming")).thenReturn(book);

        ResponseEntity<?> response = bookLibraryController.getBookByName("Java Programming");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void testGetBookByNameNotFound() throws ResourceNotFoundException {
        when(bookLibraryService.getBooksByName("NonExistingBook")).thenReturn(null);

        ResponseEntity<?> response = bookLibraryController.getBookByName("NonExistingBook");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testCreateBookSuccess() throws ResourceNotFoundException {
        Books book = new Books();
        book.setId(19001l);
        book.setName("Java Programming");
        book.setIsbn("123456789");
        book.setBookAuthor("John Doe");
        book.setCategory("Programming");
        book.setCopies(10);

        when(bookLibraryService.saveBooks(book)).thenReturn(book);

        ResponseEntity<?> response = bookLibraryController.createBook(book);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void testCreateUserSuccess() {
        Users user = new Users();
        user.setId(2001l);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<?> response = bookLibraryController.createUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testBorrowedBookSuccess() throws ResourceNotFoundException {
        when(bookLibraryService.borrowBook(1L, 1L)).thenReturn(true);

        ResponseEntity<?> response = bookLibraryController.borrowedBook("1", "1");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void testReturnBookSuccess() throws ResourceNotFoundException {
        when(bookLibraryService.returnBook(1L, 1L)).thenReturn(true);

        ResponseEntity<?> response = bookLibraryController.returnBook("1", "1");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(true, response.getBody());
    }
}

