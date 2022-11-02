package com.arjstack.tech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arjstack.tech.models.LoginToken;

@Repository
public interface LoginTokensRepository extends JpaRepository<LoginToken, Long> {

	@Query("select token from LoginToken token where token.user.userName=:userName")
	LoginToken findByUserName(@Param("userName") String userName);

}
