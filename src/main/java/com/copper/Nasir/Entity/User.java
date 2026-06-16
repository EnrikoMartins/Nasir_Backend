package com.copper.Nasir.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @JsonProperty("username")          // JSON usa "username" para este campo
    @Column(name = "username", nullable = false)
    private String name;               // Lombok gera getName() — sem conflito após o @JsonIgnore abaixo

    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // ─── UserDetails ─────────────────────────────────────────────────────────
    // Todos os métodos da interface UserDetails recebem @JsonIgnore.
    //
    // Sem @JsonIgnore em getUsername(), Jackson enxerga DOIS getters para a
    // propriedade "username": getName() (via @JsonProperty no campo) e
    // getUsername() (inferido pelo nome do método). O conflito faz canRead()
    // retornar false → nenhum converter aceita a requisição → 415.
    //
    // Os demais métodos (getAuthorities, is*) também são ignorados para não
    // vazar dados internos do Spring Security na serialização JSON.

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override @JsonIgnore public boolean isAccountNonExpired()     { return true; }
    @Override @JsonIgnore public boolean isAccountNonLocked()      { return true; }
    @Override @JsonIgnore public boolean isCredentialsNonExpired() { return true; }
    @Override @JsonIgnore public boolean isEnabled()               { return true; }
}