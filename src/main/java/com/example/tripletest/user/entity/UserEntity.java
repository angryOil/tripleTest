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
    @GeneratedValue
    private UUID uuid;

    @Column(unique = true)
    private String id;
    private String pw;

    @Builder
    public UserEntity(String id  , String pw) {
        this.id = id;
        this.pw = pw;
    }
}
