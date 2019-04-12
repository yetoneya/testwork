package ru.elena.testwork.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elena.testwork.base.DBService;
import ru.elena.testwork.dao.UserRepository;
import ru.elena.testwork.domain.User;

@Service
public class UserService implements DBService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}
