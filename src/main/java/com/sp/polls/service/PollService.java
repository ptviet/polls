package com.sp.polls.service;

import com.sp.polls.model.Poll;
import com.sp.polls.payload.request.PollRequest;
import com.sp.polls.payload.request.VoteRequest;
import com.sp.polls.payload.response.PagedResponse;
import com.sp.polls.payload.response.PollResponse;
import com.sp.polls.security.UserPrincipal;

public interface PollService {
  PagedResponse<PollResponse> getAllPolls(UserPrincipal currentUser, int page, int size);
  PagedResponse<PollResponse> getPollsCreatedBy(String username, UserPrincipal currentUser, int page, int size);
  PagedResponse<PollResponse> getPollsVotedBy(String username, UserPrincipal currentUser, int page, int size);
  Poll createPoll(PollRequest pollRequest);
  Long countByCreatedBy(Long userId);
  PollResponse getPollById(Long pollId, UserPrincipal currentUser);
  PollResponse castVoteAndGetUpdatedPoll(Long pollId, VoteRequest voteRequest, UserPrincipal currentUser);
}
