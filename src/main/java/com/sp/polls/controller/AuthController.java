package com.sp.polls.controller;

import com.sp.polls.payload.response.ApiResponse;
import com.sp.polls.payload.request.LoginRequest;
import com.sp.polls.payload.request.SignUpRequest;
import com.sp.polls.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    return ResponseEntity.ok(userService.signIn(loginRequest));

  }

  @PostMapping("/signup")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userService.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
          HttpStatus.BAD_REQUEST);
    }

    if (userService.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
          HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.created(userService.signUp(signUpRequest))
        .body(new ApiResponse(true, "User registered successfully"));
  }

}
