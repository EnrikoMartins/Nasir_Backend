package com.copper.Nasir.Entity;

import com.copper.Nasir.Enum.CardCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String imageUrl;

    private Double rating;

    private String releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardCategory category;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    public Card() {}

//    public UUID getId() { return id; }
//    public void setId(UUID id) { this.id = id; }
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//    public String getImageUrl() { return imageUrl; }
//    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
//    public Double getRating() { return rating; }
//    public void setRating(Double rating) { this.rating = rating; }
//    public String getReleaseDate() { return releaseDate; }
//    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
//    public CardCategory getCategory() { return category; }
//    public void setCategory(CardCategory category) { this.category = category; }
//    public String getSynopsis() { return synopsis; }
//    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
}
