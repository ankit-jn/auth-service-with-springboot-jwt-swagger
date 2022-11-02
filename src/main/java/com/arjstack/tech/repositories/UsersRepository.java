package com.arjstack.tech.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arjstack.tech.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	@Query("select user from Users user")
	List<Users> getAllUsers();

	@Query("select user from Users user where user.userName=:userName")
	Users findUser(@Param("userName") String userName);

	Users findByUserName(String userName);
}
