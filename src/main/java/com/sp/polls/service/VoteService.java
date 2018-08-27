package com.sp.polls.service;

import com.sp.polls.model.ChoiceVoteCount;
import com.sp.polls.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoteService {
  List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(List<Long> pollIds);
  List<ChoiceVoteCount> countByPollIdGroupByChoiceId(Long pollId);
  List<Vote> findByUserIdAndPollIdIn(Long userId,List<Long> pollIds);
  Vote findByUserIdAndPollId(Long userId, Long pollId);
  Long countByUserId(Long userId);
  Page<Long> findVotedPollIdsByUserId(Long userId, Pageable pageable);
}
