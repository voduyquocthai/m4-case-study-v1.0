package com.blueteam.official.service;

import com.blueteam.official.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService extends IGeneralService<User> {
    User findUserByUserName(String username);

    User findUserByEmail(String email);

    Page<User> findAllByKey(String key, Pageable pageable);
}
