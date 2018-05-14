package com.example.service;

import java.util.List;

import com.example.model.User;

public interface UserService {
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(long userId);
	
	User getUser(long userId);
	
	User getUserByName(String name);
	
	List<User> getAllUsers();
	
	void deleteAllUsers();
}