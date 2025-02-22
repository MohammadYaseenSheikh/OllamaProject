package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.config;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.UserService;
import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.service.impl.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserServiceImplementation userService;

    CustomOAuth2UserService(UserServiceImplementation userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Get user details from Google
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // ✅ Use existing service to process Google login
        userService.processOAuthPostLogin(email, name);

        return oAuth2User;
    }

}
