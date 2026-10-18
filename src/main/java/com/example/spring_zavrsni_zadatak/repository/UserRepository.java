package com.example.spring_zavrsni_zadatak.repository;

import com.example.spring_zavrsni_zadatak.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInfo, Long> {
    public UserInfo findByUsername(String username);
}