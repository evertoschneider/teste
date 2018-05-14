package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.PasswordType;
import com.example.model.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private Long contSenhaPref = 0L;
	private Long contSenhaNorm = 0L;
	
	private List<User> usersPref = new ArrayList<>();
	private List<User> usersNorm = new ArrayList<>();
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user, UriComponentsBuilder builder) {
		logger.info("Saving User: {}", user);

		if(user.getTipoSenha().equals(PasswordType.PREFERENCIAL)) {
			user.setSenha("P" + contSenhaPref);
			usersPref.add(user);
			contSenhaPref++;
		} else if (user.getTipoSenha().equals(PasswordType.NORMAL)) {
			user.setSenha("N" + contSenhaNorm);
			usersNorm.add(user);
			contSenhaNorm++;
		}
		
		userService.saveUser(user);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User: {}", id);

		User currentUser = userService.getUser(id);
		
		if (currentUser == null) {
			logger.error("User not found");
			return new ResponseEntity(new CustomErrorType("Error updating. User not found"), HttpStatus.NOT_FOUND);
		}
		
		currentUser.setName(user.getName());
		userService.updateUser(currentUser);
		
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Deleting User: {}", id);
		
		User user = userService.getUser(id);
		
		if(user == null) {
			logger.error("User not found");
			return new ResponseEntity(new CustomErrorType("Error deleting. User not found"), HttpStatus.NOT_FOUND);
		}
		
		userService.deleteUser(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		logger.info("Deleting all users");
		
		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Getting user: {}", id);
		
		User user = userService.getUser(id);
		
		if(user == null) {
			logger.info("User not found");
			return new ResponseEntity(new CustomErrorType("User not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		
		if(users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	public List<User> getUsersPref() {
		return usersPref;
	}

	public List<User> getUsersNorm() {
		return usersNorm;
	}
	
}