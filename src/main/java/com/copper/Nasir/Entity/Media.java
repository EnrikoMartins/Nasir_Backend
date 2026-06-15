package com.copper.Nasir.Entity;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
 
import java.util.UUID;
 
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Media {
 
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
 
    @NotNull
    @Column(nullable = false)
    private String title;
 
    private String description;
 
    private String type;
}