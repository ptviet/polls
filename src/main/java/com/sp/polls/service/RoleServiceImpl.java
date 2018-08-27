package com.sp.polls.service;

import com.sp.polls.exception.AppException;
import com.sp.polls.model.Role;
import com.sp.polls.model.RoleName;
import com.sp.polls.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role findByName(RoleName roleName) {
      return roleRepository.findByName(roleName)
          .orElseThrow(() -> new AppException("User Role not set."));
  }


}
