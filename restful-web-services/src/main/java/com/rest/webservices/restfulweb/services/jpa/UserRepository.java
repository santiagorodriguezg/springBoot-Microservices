package com.rest.webservices.restfulweb.services.jpa;

import com.rest.webservices.restfulweb.services.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
