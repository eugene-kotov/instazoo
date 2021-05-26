package ru.eugene.instazoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eugene.instazoo.entity.ImageModel;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {

    Optional<ImageModel> findByUserId(Long id);

    Optional<ImageModel> findByPostId(Long id);
}
