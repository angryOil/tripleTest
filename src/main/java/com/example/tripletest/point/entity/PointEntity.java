package com.example.tripletest.point.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "point")
@Builder
public class PointEntity {
    @Id
    @GeneratedValue(generator =  "uuid")
    @GenericGenerator(name = "name=uuid",strategy = "uuid")
    private UUID uuid;
    private int mileage;

}
