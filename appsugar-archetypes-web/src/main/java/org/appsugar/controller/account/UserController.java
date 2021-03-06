package org.appsugar.controller.account;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.appsugar.bean.domain.Page;
import org.appsugar.bean.domain.Pageable;
import org.appsugar.entity.account.User;
import org.appsugar.entity.account.condition.UserCondition;
import org.appsugar.service.account.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户controller
 * @author NewYoung
 * 2016年2月29日上午11:32:33
 */
@Controller
@RequestMapping("/account/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@RequiresPermissions(User.permission_view)
	@RequestMapping
	public String list(Model model, UserCondition condition, Pageable pageable) {
		pageable.setPageSize(5);
		model.addAttribute("page", userService.getByCondition(condition, pageable));
		model.addAttribute("condition", condition);
		return "/account/user/list";
	}

	/**
	 * 模拟异步处理
	 * @author NewYoung
	 * 2017年1月9日下午4:03:03
	 */
	@RequestMapping("asyncList")
	@ResponseBody
	public CompletableFuture<Page<User>> asyncList(UserCondition condition, Pageable pageable) {
		//TODO user ->account ->user  stackoverflow
		return CompletableFuture.completedFuture(userService.getByCondition(condition, pageable));
	}

	@ModelAttribute
	public User modelAttribute(Long id) {
		if (Objects.isNull(id)) {
			return new User();
		}
		return userService.get(id);
	}

}
