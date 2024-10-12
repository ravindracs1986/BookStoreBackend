package com.book.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.book.library.model.BookTaken;
import com.book.library.model.Users;

public interface BookTakenRepository extends JpaRepository<BookTaken, Long> {

	@Query(value = "select * from book_taken where book_id=?1 and user_id=?2", nativeQuery = true)
	List<BookTaken> getBybookIdandUserId(Long bookId, Long userId);

}
