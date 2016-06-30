package org.appsugar.repository.account;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.appsugar.BaseJpaDaoTestCase;
import org.appsugar.account.condition.UserCondition;
import org.appsugar.account.entity.User;
import org.appsugar.account.repository.UserRepository;
import org.appsugar.account.specification.UserSpecification;
import org.appsugar.common.repository.HibernateContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * user repository test case
 * @author NewYoung
 * 2016年1月28日下午6:04:19
 */
public class UserRepositoryTest extends BaseJpaDaoTestCase {

	@Autowired
	private UserRepository repository;

	@Autowired
	private HibernateContext context;

	@Test
	public void testFindByName() throws Exception {
		String name = "admin";
		User user = repository.findByLoginName(name);
		logger.debug("testFindByName  result is : {}", user);
		Assert.assertNotNull(user);
	}

	@Test
	public void testFindByCondition() {
		UserCondition condition = new UserCondition();
		String name = "管理员";
		condition.setName(name);
		List<User> userList = repository.findAll(new UserSpecification(condition));
		logger.debug("testFindByCondition  name {}  result {}", name, userList);
		Assert.assertTrue(CollectionUtils.isNotEmpty(userList));
	}

	@Test
	public void testDeleteRoleRelationship() {
		Long roleId = -9999l;
		repository.deleteRoleRelationship(roleId);
	}
}
