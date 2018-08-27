package com.sp.polls.service;

import com.sp.polls.model.User;
import com.sp.polls.payload.response.JwtAuthenticationResponse;
import com.sp.polls.payload.request.LoginRequest;
import com.sp.polls.payload.request.SignUpRequest;

import java.net.URI;

public interface UserService {
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
  User save(User user);
  User findByUsername(String username);
  URI signUp(SignUpRequest signUpRequest);
  JwtAuthenticationResponse signIn(LoginRequest loginRequest);
}
