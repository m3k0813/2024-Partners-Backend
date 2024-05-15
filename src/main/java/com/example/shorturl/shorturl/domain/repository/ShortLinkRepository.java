package com.example.shorturl.shorturl.domain.repository;

import com.example.shorturl.shorturl.domain.entity.ShortLinkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ShortLinkRepository extends JpaRepository<ShortLinkEntity, Long>{
    Optional<ShortLinkEntity> findByOriginalUrl(String originalUrl);
    Page<ShortLinkEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
