package com.example.shorturl.shorturl.domain.dto;

import com.example.shorturl.shorturl.domain.entity.ShortLinkEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private String shortUrl;
    private String originalUrl;
    private String hash;
    private LocalDateTime createdAt;

    public static ResponseDto fromEntity(ShortLinkEntity shortLinkEntity){
        return new ResponseDto(
                shortLinkEntity.getShortUrl(),
                shortLinkEntity.getOriginalUrl(),
                shortLinkEntity.getHash(),
                shortLinkEntity.getCreatedAt()
        );
    }

}
