package org.appsugar.account.repository;

import org.appsugar.account.condition.RoleCondition;
import org.appsugar.account.entity.Role;
import org.appsugar.data.jpa.repository.IdEntityRepository;

/**
 * 
 * @author NewYoung
 * 2016年2月23日下午6:39:45
 */
public interface RoleRepository extends IdEntityRepository<Role, RoleCondition> {
}