package com.copper.Nasir.Service;

import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Exception.UserNotFoundException;
import com.copper.Nasir.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    // Pasta onde os avatars serão salvos dentro do projeto
    // (ajuste conforme o caminho real ou use uma variável de ambiente)
    private static final String UPLOAD_DIR  = "uploads/avatars/";
    private static final String PUBLIC_PATH = "/uploads/avatars/";

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // ── UserDetailsService ───────────────────────────────────────────────────

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário não encontrado: " + email));
    }

    // ── CRUD existente ───────────────────────────────────────────────────────

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

    // ── novos métodos de perfil ──────────────────────────────────────────────

    /**
     * Atualiza o nome e/ou o avatar do usuário.
     * Requer que a entidade User possua o campo {@code avatarUrl} (String).
     * Adicione-o ao User.java caso ainda não exista:
     *
     *   private String avatarUrl;
     */
    public User updateProfile(User user, String username, MultipartFile avatar) {
        if (username != null && !username.isBlank()) {
            user.setName(username);
        }
        if (avatar != null && !avatar.isEmpty()) {
            String url = saveAvatar(user.getId(), avatar);
            user.setAvatarUrl(url);
        }
        return repository.save(user);
    }

    // ── helper de upload ─────────────────────────────────────────────────────

    private String saveAvatar(UUID userId, MultipartFile file) {
        try {
            Path dir = Paths.get(UPLOAD_DIR);
            Files.createDirectories(dir);

            String ext      = extension(file.getOriginalFilename());
            String filename = userId + "." + ext;
            Path   target   = dir.resolve(filename);

            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            return PUBLIC_PATH + filename;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar avatar: " + e.getMessage(), e);
        }
    }

    private String extension(String filename) {
        if (filename == null || !filename.contains(".")) return "jpg";
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}