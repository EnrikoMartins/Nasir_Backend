package com.copper.Nasir.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
import java.util.Collection;
import java.util.List;
import java.util.UUID;
 
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
 
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
 
    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;
 
    @NotNull
    @Column(nullable = false)
    private String password;
  
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
 
    @Override
    public String getUsername() {
        return email; 
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
}

