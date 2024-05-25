package com.example.shorturl.shorturl.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE short_link SET deleted = true WHERE short_link_id = ?")
@Where(clause = "deleted = false")
@Table(name = "short_link")
public class ShortLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "short_link_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Column(name = "hash", nullable = false)
    private String hash;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted", nullable = false, columnDefinition = "bit(1) default 0")
    private Boolean isDeleted = false;

    public void setShortUrl(String hash) {
        this.shortUrl = "http://localhost:8080/short-links/" + hash;
    }
}
