package project.employeehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import project.employeehub.entity.User;
import project.employeehub.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //it is to encrypt or send the hashed password to mysql
    //first, we need to create PasswordEncoder bean in the main class.

    @Override
  public  User createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword())); //this line hashes the password.

        return userRepository.save(user);
    }

}
