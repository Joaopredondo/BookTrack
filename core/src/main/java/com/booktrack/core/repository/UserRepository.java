package com.booktrack.core.repository;

import com.booktrack.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
