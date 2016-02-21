package com.dlizarra.startuphub.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dlizarra.startuphub.role.RoleName;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public void create(@Valid @RequestBody final UserDto user) {
		userService.createUser(user, RoleName.ROLE_USER);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable("id") final Integer id, @Valid @RequestBody final UserDto project) {

	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<UserDto> list() {
		return userService.getUsers();
	}
}
