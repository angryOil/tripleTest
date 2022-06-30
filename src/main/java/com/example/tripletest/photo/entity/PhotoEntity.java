package com.example.tripletest.photo.entity;

import com.example.tripletest.review.entity.ReviewEntity;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "유저 uuid")
    private UUID uuid;
    @ApiModelProperty("사진이름")
    private String name;

    @ManyToOne
    @JoinColumn(name = "review_id")
    @ApiModelProperty("리뷰 entity")
    private ReviewEntity reviewEntity;


}
