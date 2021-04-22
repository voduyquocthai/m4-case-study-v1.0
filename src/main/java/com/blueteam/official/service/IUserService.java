package com.blueteam.official.service;

import com.blueteam.official.model.User;

public interface IUserService extends IGeneralService<User> {
    User findUserByUserName(    String username);


}
