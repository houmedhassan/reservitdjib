package com.safara.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safara.security.entities.UserHistory;



public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer>{

}
