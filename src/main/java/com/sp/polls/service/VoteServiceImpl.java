package com.sp.polls.service;

import com.sp.polls.model.ChoiceVoteCount;
import com.sp.polls.model.Vote;
import com.sp.polls.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

  @Autowired
  private VoteRepository voteRepository;

  @Override
  public List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(List<Long> pollIds) {
    return voteRepository.countByPollIdInGroupByChoiceId(pollIds);
  }

  @Override
  public List<ChoiceVoteCount> countByPollIdGroupByChoiceId(Long pollId) {
    return voteRepository.countByPollIdGroupByChoiceId(pollId);
  }

  @Override
  public List<Vote> findByUserIdAndPollIdIn(Long userId, List<Long> pollIds) {
    return voteRepository.findByUserIdAndPollIdIn(userId, pollIds);
  }

  @Override
  public Vote findByUserIdAndPollId(Long userId, Long pollId) {
    return voteRepository.findByUserIdAndPollId(userId, pollId);
  }

  @Override
  public Long countByUserId(Long userId) {
    return voteRepository.countByUserId(userId);
  }

  @Override
  public Page<Long> findVotedPollIdsByUserId(Long userId, Pageable pageable) {
    return voteRepository.findVotedPollIdsByUserId(userId, pageable);
  }
}
