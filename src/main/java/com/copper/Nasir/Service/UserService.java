package com.copper.Nasir.Service;
 
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Exception.UserNotFoundException;
import com.copper.Nasir.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Optional;
import java.util.UUID;
 
@Service
public class UserService {
 
    private final UserRepository repository;
 
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
 
    public List<User> findAll() {
        return repository.findAll();
    }
 
    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }
 
    public User createUser(User user) {
        return repository.save(user);
    }
 
    public Optional<User> updateUser(User newUser) {
        Optional<User> oldUser = repository.findById(newUser.getId());
 
        oldUser.ifPresent(user -> {
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            repository.save(user);
        });
 
        return oldUser;
    }
 
    public void deleteUser(UUID id) {
        Optional<User> user = repository.findById(id);
 
        if (user.isPresent()) repository.deleteById(id);
        else throw new UserNotFoundException("User not found with id: " + id);
    }
}

