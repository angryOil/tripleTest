package com.example.tripletest.point.entity;

import com.example.tripletest.customEnum.Action;
import com.example.tripletest.customEnum.Type;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "point_log")
@Builder
public class PointLogEntity {
    @Id
    @GeneratedValue
    private UUID uuid;

    private UUID pointId;
    private UUID reviewId;
    private UUID placeId;
    private String action;
    private String type;
    private int pointApply;

}
