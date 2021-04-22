package com.blueteam.official.repository;

import com.blueteam.official.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from case_study_m4.users where username like ?1", nativeQuery = true)
    User findUserByUserName(@Param("username") String username);
}
