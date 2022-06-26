package com.example.tripletest.review.entity;

import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.user.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "review")
@Builder
public class ReviewEntity {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private UUID uuid;

    private UUID userId;
    private UUID placeId;

    private String content;

    private boolean deleteFlag;

}
