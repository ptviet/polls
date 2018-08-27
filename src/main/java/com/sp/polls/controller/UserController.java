package com.sp.polls.controller;

import com.sp.polls.model.User;
import com.sp.polls.payload.response.*;
import com.sp.polls.security.UserPrincipal;
import com.sp.polls.service.PollService;
import com.sp.polls.security.CurrentUser;
import com.sp.polls.service.UserService;
import com.sp.polls.service.VoteService;
import com.sp.polls.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private PollService pollService;

  @Autowired
  private VoteService voteService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/user/me")
  @PreAuthorize("hasRole('USER')")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
  }

  @GetMapping("/user/checkUsernameAvailability")
  public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
    Boolean isAvailable = !userService.existsByUsername(username);
    return new UserIdentityAvailability(isAvailable);
  }

  @GetMapping("/user/checkEmailAvailability")
  public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
    Boolean isAvailable = !userService.existsByEmail(email);
    return new UserIdentityAvailability(isAvailable);
  }

  @GetMapping("/users/{username}")
  public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
    User user = userService.findByUsername(username);

    long pollCount = pollService.countByCreatedBy(user.getId());
    long voteCount = voteService.countByUserId(user.getId());

    return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);
  }

  @GetMapping("/users/{username}/polls")
  public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    return pollService.getPollsCreatedBy(username, currentUser, page, size);
  }


  @GetMapping("/users/{username}/votes")
  public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                     @CurrentUser UserPrincipal currentUser,
                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    return pollService.getPollsVotedBy(username, currentUser, page, size);
  }

}