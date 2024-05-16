package com.example.shorturl.shorturl.domain.service;

import com.example.shorturl.shorturl.domain.config.Base62;
import com.example.shorturl.shorturl.domain.dto.RequestDto;
import com.example.shorturl.shorturl.domain.dto.ResponseDto;
import com.example.shorturl.shorturl.domain.entity.ShortLinkEntity;
import com.example.shorturl.shorturl.domain.repository.ShortLinkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl implements ShortLinkService {

    @Autowired
    private final ShortLinkRepository shortLinkRepository;
    private static final AtomicLong idCounter = new AtomicLong();

    @Override
    @Transactional
    public ResponseDto createShortUrl(RequestDto requestDto) {

        // 디비에 저장되어있는지 체크
        Optional<ShortLinkEntity> shortLinkEntity = shortLinkRepository.findByOriginalUrl(requestDto.getOriginalUrl());
        if(shortLinkEntity.isPresent()){
            System.out.println("이미 저장되어있는 URL");
            return ResponseDto.fromEntity(shortLinkEntity.get());
        }

        UUID id = UUID.randomUUID();
        String shortLink = Base62.encode(id.getMostSignificantBits() & Long.MAX_VALUE);

        ShortLinkEntity newShortLink = new ShortLinkEntity();
        newShortLink.setId(id);
        newShortLink.setOriginalUrl(requestDto.getOriginalUrl());
        newShortLink.setShortUrl(shortLink);
        newShortLink.setHash(shortLink);
        newShortLink.setCreatedAt(LocalDateTime.now());

        shortLinkRepository.save(newShortLink);
        return ResponseDto.fromEntity(newShortLink);

    }

    @Override
    @Transactional
    public void deleteShortUrl(String originalUrl) {

        Optional<ShortLinkEntity> shortLinkEntity = shortLinkRepository.findByOriginalUrl(originalUrl);
        if (!shortLinkEntity.isPresent()) {
            throw new IllegalArgumentException("해당 URL이 존재하지 않습니다.");
        }

        if (shortLinkEntity.get().getIsDeleted()) {
            throw new IllegalArgumentException("이미 삭제된 URL입니다.");
        }

        shortLinkRepository.delete(shortLinkEntity.get());
    }

    @Override
    public Page<ResponseDto> getShortUrls(Pageable pageable) {
        Page<ShortLinkEntity> shortLinkEntities = shortLinkRepository.findAllByOrderByCreatedAtDesc(pageable);
        return shortLinkEntities.map(ResponseDto::fromEntity);
    }

    public String getOriginalUrlByHash(String hash) {
        ShortLinkEntity shortLinkEntity = shortLinkRepository.findByHash(hash);
        if (shortLinkEntity == null || shortLinkEntity.getIsDeleted()) {
            throw new RuntimeException("URL not found or has been deleted");
        }
        return shortLinkEntity.getOriginalUrl();
    }
}
