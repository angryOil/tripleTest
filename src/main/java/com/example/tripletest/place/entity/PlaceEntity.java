package com.example.tripletest.place.entity;

import ch.qos.logback.classic.db.names.ColumnName;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "place",uniqueConstraints = { @UniqueConstraint(columnNames = { "location", "name" }) })
@Builder
public class PlaceEntity {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private UUID uuid;

    private String location;
    private String name;
    private boolean specialFlag;

}
