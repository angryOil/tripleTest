package com.example.tripletest.user.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(generator =  "uuid")
    @GenericGenerator(name = "name=uuid",strategy = "uuid")
    private UUID uuid;

    @Column(unique = true)
    private String id;
    private String pw;
    private boolean firstReviewFlag;

    @Builder
    public UserEntity(String id  , String pw) {
        this.id = id;
        this.pw = pw;
    }
}
