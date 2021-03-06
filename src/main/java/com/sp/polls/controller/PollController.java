package com.sp.polls.controller;

import com.sp.polls.model.*;
import com.sp.polls.payload.request.PollRequest;
import com.sp.polls.payload.request.VoteRequest;
import com.sp.polls.payload.response.ApiResponse;
import com.sp.polls.payload.response.PagedResponse;
import com.sp.polls.payload.response.PollResponse;
import com.sp.polls.security.CurrentUser;
import com.sp.polls.security.UserPrincipal;
import com.sp.polls.service.PollService;
import com.sp.polls.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/polls")
public class PollController {

  @Autowired
  private PollService pollService;

  private static final Logger logger = LoggerFactory.getLogger(PollController.class);

  @GetMapping
  public PagedResponse<PollResponse> getPolls(@CurrentUser UserPrincipal currentUser,
                                              @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    return pollService.getAllPolls(currentUser, page, size);
  }

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest) {
    Poll poll = pollService.createPoll(pollRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{pollId}")
        .buildAndExpand(poll.getId()).toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "Poll Created Successfully"));
  }

  @GetMapping("/{pollId}")
  public PollResponse getPollById(@CurrentUser UserPrincipal currentUser,
                                  @PathVariable Long pollId) {
    return pollService.getPollById(pollId, currentUser);
  }

  @PostMapping("/{pollId}/votes")
  @PreAuthorize("hasRole('USER')")
  public PollResponse castVote(@CurrentUser UserPrincipal currentUser,
                               @PathVariable Long pollId,
                               @Valid @RequestBody VoteRequest voteRequest) {
    return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
  }

  @GetMapping("/search")
  public PagedResponse<PollResponse> searchPolls(@CurrentUser UserPrincipal currentUser,
                                                 @RequestParam(value = "term", defaultValue = "") String question,
                                                 @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

    return pollService.findByQuestion(currentUser, question, page, size);
  }



}