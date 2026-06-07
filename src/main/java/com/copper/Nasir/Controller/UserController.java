
package com.copper.Nasir.Controller;
 
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.UUID;
 
@RestController
@RequestMapping("/users")
public class UserController {
 
    private final UserService service;
 
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }
 
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new com.copper.Nasir.Exception.UserNotFoundException("User not found with id: " + id));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User newUser) {
        newUser.setId(id);
        return service.updateUser(newUser)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new com.copper.Nasir.Exception.UserNotFoundException("User not found with id: " + id));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}