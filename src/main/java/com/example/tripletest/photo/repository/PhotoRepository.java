package com.example.tripletest.photo.repository;

import com.example.tripletest.photo.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<PhotoEntity , UUID> {

}
