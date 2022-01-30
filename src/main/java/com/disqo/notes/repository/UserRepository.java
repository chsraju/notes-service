package com.disqo.notes.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.disqo.notes.model.User;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
	User findByEmailAndPassword(String email, String password);
	User findByEmail(String email);
}