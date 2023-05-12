package com.usertask.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usertask.security.binding.UserDto;
import com.usertask.security.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);
	
}
