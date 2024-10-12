package com.book.library.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.book.library.model.Books;



public interface BookLibraryService {
	
	public List<Books> getAllBooks();
	
	public Books getBooksByName(String bookName);
	
	public Books saveBooks(Books book);
	
	public Boolean borrowBook(Long bookId, Long userId);
	public Boolean returnBook(Long bookId, Long userId);
	
	public Optional<Books> getBookById(String id);
	
	

}
