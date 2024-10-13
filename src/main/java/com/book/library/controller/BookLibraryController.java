/**
 * 
 */
package com.book.library.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.library.model.Books;
import com.book.library.model.Users;
import com.book.library.repository.UserRepository;
import com.book.library.service.BookLibraryService;
import com.book.library.utils.ErrorMessage;
import com.book.library.utils.ResourceNotFoundException;


import ch.qos.logback.core.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * 
 */
@Tag(name = "Book Library", description = "Book Library management APIs")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/book")
public class BookLibraryController {

	@Autowired
	BookLibraryService service;
	
	@Autowired
	UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(BookLibraryController.class);
	ErrorMessage message =new ErrorMessage();
	
	
	@Operation(summary = "Get All books")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Found the book", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = String.class)) }),
			  @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
			    content = @Content), 
			  @ApiResponse(responseCode = "404", description = "Book not found", 
			    content = @Content) })
	@GetMapping("/allbooks")
	public ResponseEntity<?> getAllBooks() {
		
		try {
			List<Books> booksData = new ArrayList<Books>();
			booksData = service.getAllBooks();
			if (booksData.isEmpty()) {
				message.setStatusCode(204);
				message.setTimestamp(new Date());
				message.setMessage("No Content Found");
				return new ResponseEntity<>(message,HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(booksData, HttpStatus.OK);
		} catch (ResourceNotFoundException exp) {
			message.setStatusCode(500);
			message.setTimestamp(new Date());
			message.setMessage("Internal Server Error");
			message.setDescription(exp.getMessage());
			return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{bookname}")
	public ResponseEntity<?> getBookByName(@RequestParam(required = true) String bookname) {
		try {
			
			if(bookname.isEmpty()) {
				message.setStatusCode(400);
				message.setTimestamp(new Date());
				message.setMessage("BookName Should Not Empty");
				return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
			}
			
			Books booksData = new Books();
			booksData = service.getBooksByName(bookname);
			if (booksData!=null && booksData.getId()!=null) {
				return new ResponseEntity<>(booksData, HttpStatus.OK);
				
			}else {
				message.setStatusCode(204);
				message.setTimestamp(new Date());
				message.setMessage("No Book Available given Name");
				return new ResponseEntity<>(message,HttpStatus.NO_CONTENT);
			}
			
		} catch (ResourceNotFoundException exp) {
			message.setStatusCode(500);
			message.setTimestamp(new Date());
			message.setMessage("Internal Server Error");
			message.setDescription(exp.getMessage());
			return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/createBook")
	public ResponseEntity<?> createBook(@RequestBody Books book) {
		try {
			
			
			if(book==null || StringUtil.isNullOrEmpty(book.getName()) || StringUtil.isNullOrEmpty(book.getIsbn())
					||StringUtil.isNullOrEmpty(book.getBookAuthor()) || book.getCopies()==null || StringUtil.isNullOrEmpty(book.getCategory())) {
				
				message.setStatusCode(400);
				message.setTimestamp(new Date());
				message.setMessage("BookName,ISBN,BookAuthor and Copies Should not Empty");
				return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
			}
			
			
			
			Books booksData = service.saveBooks(book);
			if (booksData!=null && booksData.getId()!=null) {
				return new ResponseEntity<>(booksData, HttpStatus.CREATED);
				
			}else {
				message.setStatusCode(400);
				message.setTimestamp(new Date());
				message.setMessage("Please Try Later Something or Correct input Data");
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
			
		} catch (ResourceNotFoundException exp) {
			message.setStatusCode(500);
			message.setTimestamp(new Date());
			message.setMessage("Internal Server Error");
			message.setDescription(exp.getMessage());
			return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody Users user) {
		try {
			
			
			if(user==null || StringUtil.isNullOrEmpty(user.getName()) || StringUtil.isNullOrEmpty(user.getEmail())) {
				message.setStatusCode(400);
				message.setTimestamp(new Date());
				message.setMessage("User's Name and Email Should not Empty");
				return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
			}
			Users userData = userRepository.save(user);
			if (userData!=null && userData.getId()!=null) {
				return new ResponseEntity<>(userData, HttpStatus.CREATED);
				
			}else {
				message.setStatusCode(400);
				message.setTimestamp(new Date());
				message.setMessage("Please Try Later Something or Correct input Data");
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
			
		} catch (ResourceNotFoundException exp) {
			message.setStatusCode(500);
			message.setTimestamp(new Date());
			message.setMessage("Internal Server Error");
			message.setDescription(exp.getMessage());
			return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/borrowedBook")
	public ResponseEntity<?> borrowedBook(@RequestParam(required = true) String bookId,@RequestParam(required = true) String userId ) {
		try {
			
			
			if(StringUtil.isNullOrEmpty(bookId) || StringUtil.isNullOrEmpty(userId)) {
				message.setStatusCode(400);
				message.setTimestamp(new Date());
				message.setMessage("BookId and UserId Should not Empty");
				message.setDescription("BookId and UserId Should not Empty");
				return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
			}
			Boolean result = service.borrowBook(Long.valueOf(bookId), Long.valueOf(userId));
			if (result) {
				return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
				
			}else {
				message.setStatusCode(400);
				message.setTimestamp(new Date());
				message.setMessage("Please Try Later Something or Correct input Data");
				message.setDescription("Please Try Later Something or Correct input Data");
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
			
		} catch (ResourceNotFoundException exp) {
			message.setStatusCode(500);
			message.setTimestamp(new Date());
			message.setMessage("Internal Server Error");
			message.setDescription(exp.getMessage());
			return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/returnBook")
	public ResponseEntity<?> returnBook(@RequestParam(required = true) String bookId,@RequestParam(required = true) String userId ) {
		try {
			
			
			if(StringUtil.isNullOrEmpty(bookId) || StringUtil.isNullOrEmpty(userId)) {
				message.setStatusCode(400);
				message.setTimestamp(new Date());
				message.setMessage("BookId and UserId Should not Empty");
				return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
			}
			Boolean result = service.returnBook(Long.valueOf(bookId), Long.valueOf(userId));
			if (result) {
				return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
				
			}else {
				message.setStatusCode(400);
				message.setTimestamp(new Date());
				message.setMessage("Please Try Later Something or Correct input Data");
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
			
		} catch (ResourceNotFoundException exp) {
			message.setStatusCode(500);
			message.setTimestamp(new Date());
			message.setMessage("Internal Server Error");
			message.setDescription(exp.getMessage());
			return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findStoreById(@PathVariable("id") String id) {

		Optional<Books> bookData = service.getBookById(id);

		if (bookData.isPresent()) {
			return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
}
