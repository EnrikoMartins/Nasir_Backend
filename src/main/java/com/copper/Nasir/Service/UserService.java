package com.copper.Nasir.Service;

import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Exception.UserNotFoundException;
import com.copper.Nasir.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {   // ← CORREÇÃO PRINCIPAL

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // Sem isso o AuthenticationManager usa repositório in-memory vazio.
    // Todo login falha porque o Spring Security nunca chega a consultar o banco.
    // Com isso, authenticationManager.authenticate(email, password) chama este
    // método, carrega o User do banco e compara a senha via BCryptPasswordEncoder.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário não encontrado: " + email));
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
        return repository.findById(newUser.getId()).map(user -> {
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            return repository.save(user);
        });
    }

    public void deleteUser(UUID id) {
        repository.findById(id).ifPresentOrElse(
                u  -> repository.deleteById(id),
                () -> { throw new UserNotFoundException("Usuário não encontrado: " + id); }
        );
    }
}