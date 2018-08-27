package com.sp.polls.service;

import com.sp.polls.model.Role;
import com.sp.polls.model.RoleName;


public interface RoleService {
  Role findByName(RoleName roleName);
}
