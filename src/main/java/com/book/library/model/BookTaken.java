package com.book.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="seq", initialValue=3000, allocationSize=100)
public class BookTaken {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	private Long id;
	
	
	private Long user_id;
	
	
	private Long book_id;
	
}
