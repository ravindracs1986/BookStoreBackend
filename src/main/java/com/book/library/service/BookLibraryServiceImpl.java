package com.book.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.library.model.BookTaken;
import com.book.library.model.Books;
import com.book.library.model.Users;
import com.book.library.repository.BookTakenRepository;
import com.book.library.repository.BooksRepository;
import com.book.library.repository.UserRepository;

@Service
public class BookLibraryServiceImpl implements BookLibraryService{

	@Autowired
	public BooksRepository booksRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public BookTakenRepository bookTakenRepository;
	
	@Override
	public List<Books> getAllBooks() {
		
		return booksRepository.getAllBooks();
	}

	@Override
	public Books getBooksByName(String bookName) {
		
		return booksRepository.getBookByName(bookName);
		
	}
	
	 public Books findById(Long id) {
	        return booksRepository.findById(id).orElse(null);
	    }

	 

	    public Users saveUser(Users user) {
	        return userRepository.save(user);
	    }
	    
	    public void deleteById(Long id) {
	    	booksRepository.deleteById(id);
	    }

	    @Override
	    public Boolean borrowBook(Long bookId, Long userId) {
	    	Boolean result=false;
	    	Books book = findById(bookId);
	        Users user = userRepository.findById(userId).orElse(null);

	        if (book != null && book.getCopies()!=0 && user != null && user.getBorrowedLimit()<=2) {
	            
	            book.setCopies(book.getCopies()-1);
	            Books bookDb =saveBooks(book);
	            
	            user.setBorrowedLimit(user.getBorrowedLimit()-1);
	            Users userDb =saveUser(user);
	            if(bookDb.getId()!=null && userDb.getId()!=null) {
	            	result=true;
	            	BookTaken bookTaken=new BookTaken();
	            	bookTaken.setBook_id(bookDb.getId());
	            	bookTaken.setUser_id(userDb.getId());
	            	bookTakenRepository.save(bookTaken);
	            	
	            	
	            }
	            
	           
	        }
	        // Handle errors (e.g., book not found, book already borrowed, user not found)
	        return result;
	    }
	    @Override
	    public Boolean returnBook(Long bookId,Long userId) {
	    	Boolean result=false;
	    	Books book = findById(bookId);
	    	Users user = userRepository.findById(userId).orElse(null);
	        if (book != null && user != null) {
	           
	            book.setCopies(book.getCopies()+1);
	            Books bookDb =saveBooks(book);
	            user.setBorrowedLimit(user.getBorrowedLimit()+1);
	            Users userDb =saveUser(user);
	            if(bookDb.getId()!=null && userDb.getId()!=null) {
	            	
	            	
	            	List<BookTaken> bookTaken=bookTakenRepository.getBybookIdandUserId(bookDb.getId(),userDb.getId());
	            	
	            	if(!bookTaken.isEmpty()) {
	            		bookTakenRepository.delete(bookTaken.get(0));
	            		result=true;
	            	}
	            	
	            	
	            }
	            
	            
	        }
	        // Handle errors (e.g., book not found, book not borrowed)
	        return result;
	    }

		@Override
		public Books saveBooks(Books book) {
			
			return booksRepository.save(book);
		}

		@Override
		public Optional<Books> getBookById(String id) {
			
			return booksRepository.findById(Long.valueOf(id));
		}

}
