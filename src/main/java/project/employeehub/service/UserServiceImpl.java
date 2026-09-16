package project.employeehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import project.employeehub.entity.User;
import project.employeehub.repository.UserRepository;
import org.springframework.stereotype.Service;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
  public  User createUser(User user) {
        return userRepository.save(user);
    }

}
