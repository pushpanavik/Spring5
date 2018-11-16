package com.bridgelabz.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.bridgelabz.login.model.User;
import com.bridgelabz.login.repository.UserRepository;
import com.bridgelabz.login.utility.GenerateToken;
import com.bridgelabz.login.utility.MailSender;
import com.bridgelabz.login.utility.VerifyJwtToken;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.TopicProcessor;

@Service
public class UserService implements IUserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TopicProcessor<User> userRegister;

	@Override
	public Mono<User> register(Mono<User> monoUser, ServerRequest request) {
		return monoUser.flatMap(user -> {
			return userRepository.findByEmail(user.getEmail()).flatMap(u -> {
				return Mono.<User>error(new RuntimeException("User Wtih this email id exist"));
			}).switchIfEmpty(addUser(user, request));
		});

	}

	private Mono<User> addUser(User user, ServerRequest request) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user).map(u -> {
			userRegister.onNext(u);
			return u;
		});
	}

	@Override
	public Mono<User> activateRegisteredUser(String token) {
		String id = VerifyJwtToken.getId(token);
		Mono<User> user = userRepository.findById(id);
		return user.flatMap(u -> {
			u.setActivationStatus(true);
			return userRepository.save(u);
		});

	}

	@Override
	public Mono<User> resetUserpwd(String token) {
		String id = VerifyJwtToken.getId(token);
		Mono<User> user = userRepository.findById(id);
		return user.map(u -> {
			return u;
		});
	}

	@Override
	public Mono<String> login(Mono<User> monoUser) {
		return monoUser.flatMap(u -> {
			return userRepository.findByEmail(u.getEmail()).map(user -> {
				if (passwordEncoder.matches(u.getPassword(), user.getPassword())) {
					String token = GenerateToken.generateUserToken(user.getFirstname(), user.getLastname(), user.getUserId(),
							user.getEmail());
					return token;
				} else {
					return null;
				}
			}).switchIfEmpty(Mono.error(new Exception("User email or password doesn't match")));
		});

	}

	@Override
	public Mono<User> forgotPassword(Mono<User> monoUser) {
		return monoUser.flatMap(u -> {
			return userRepository.findByEmail(u.getEmail()).map(user -> {
				MailSender.sendMail(user, GenerateToken.generateToken(user.getUserId()));
				return user;
			});
		}).switchIfEmpty(Mono.error(new Exception("Email id not found")));

	}

	public Mono<User> resetPassword(Mono<User> user) {
		return user.flatMap(u -> {
			u.setPassword(passwordEncoder.encode(u.getPassword()));
			return userRepository.save(u);
		});

	}

	@Override
	public Mono<User> resetPassword(Mono<User> user,List<String> list) {

		return user.flatMap(u -> {
			String id = VerifyJwtToken.getId(list.get(0));
			return userRepository.findById(id).flatMap(existingUser -> {
				existingUser.setPassword(passwordEncoder.encode(u.getPassword()));
				return userRepository.save(existingUser);
			});

		});

	}

	@Override
	public Mono<User> getUser(String id) {
		return userRepository.findById(id);
	}

	@Override
	public Flux<User> getAllUsers() {
		Flux<User> user =userRepository.findAll();
		return user;
		
	}

}
