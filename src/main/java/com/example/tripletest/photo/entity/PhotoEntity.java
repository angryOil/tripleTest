package com.example.tripletest.photo.entity;

import com.example.tripletest.review.entity.ReviewEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "photo")
@Builder
public class PhotoEntity {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private UUID uuid;

    private String name;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewEntity reviewEntity;


}
