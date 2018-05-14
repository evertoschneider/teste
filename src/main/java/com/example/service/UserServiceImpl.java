package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteUser(long userId) {
		userRepository.delete(getUser(userId));
	}

	@Override
	public User getUser(long userId) {
		User user = userRepository.findById(userId).get();
		return user;
	}

	@Override
	public User getUserByName(String name) {
		for(User user : getAllUsers()) {
			if(user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	@Override
	public void deleteAllUsers() {
		userRepository.deleteAll();
	}

}