package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.dto.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void registerUser(User user);

}
