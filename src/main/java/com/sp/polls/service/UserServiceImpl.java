package com.sp.polls.service;

import com.sp.polls.exception.ResourceNotFoundException;
import com.sp.polls.model.Role;
import com.sp.polls.model.RoleName;
import com.sp.polls.model.User;
import com.sp.polls.payload.response.JwtAuthenticationResponse;
import com.sp.polls.payload.request.LoginRequest;
import com.sp.polls.payload.request.SignUpRequest;
import com.sp.polls.repository.UserRepository;
import com.sp.polls.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private RoleService roleService;
  private JwtTokenProvider tokenProvider;
  private AuthenticationManager authenticationManager;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                         RoleService roleService, JwtTokenProvider tokenProvider,
                         AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
    this.tokenProvider = tokenProvider;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public Boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public Boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public URI signUp(SignUpRequest signUpRequest) {

    // Creating new account
    User newUser = new User(signUpRequest.getName(), signUpRequest.getUsername(),
        signUpRequest.getEmail(), signUpRequest.getPassword());

    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

    Role userRole = roleService.findByName(RoleName.ROLE_USER);

    newUser.setRoles(Collections.singleton(userRole));

    User savedUser = userRepository.save(newUser);

    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
        .buildAndExpand(savedUser.getUsername()).toUri();
  }

  @Override
  public JwtAuthenticationResponse signIn(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsernameOrEmail(),
            loginRequest.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);

    return new JwtAuthenticationResponse(jwt);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
  }
}
