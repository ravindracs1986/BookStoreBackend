package com.book.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.library.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

}
