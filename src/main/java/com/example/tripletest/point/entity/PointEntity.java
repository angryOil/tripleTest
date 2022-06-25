package com.example.tripletest.point.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "point")
@Builder
public class PointEntity {
    @Id
    private UUID uuid;

    private String userId;
    private int mileage;

}
