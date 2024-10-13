package com.book.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.book.library.model.Books;



public interface BooksRepository extends JpaRepository<Books, Long> {
	
	Books getBookByName(String bookName);
	
	@Query(value = "select * from books where copies!=0", nativeQuery = true)
	List<Books> getAllBooks();

}
