package com.copper.Nasir.Spec;

import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Entity.UserCard;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Enum.CardStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class UserCardSpecification {

    public static Specification<UserCard> byUserId(UUID userId) {
        return (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<UserCard> byStatus(CardStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    public static Specification<UserCard> byCategory(CardCategory category) {
        return (root, query, cb) -> {
            Join<UserCard, Card> card = root.join("card");
            return cb.equal(card.get("category"), category);
        };
    }

    public static Specification<UserCard> byMinRating(Double min) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("userRating"), min);
    }

    public static Specification<UserCard> byMaxRating(Double max) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("userRating"), max);
    }
}