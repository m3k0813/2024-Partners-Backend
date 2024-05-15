package com.example.shorturl.shorturl.domain.service;

import com.example.shorturl.shorturl.domain.dto.RequestDto;
import com.example.shorturl.shorturl.domain.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShortLinkService {

    ResponseDto createShortUrl(RequestDto requestDto);

    void deleteShortUrl(String originalUrl);

    Page<ResponseDto> getShortUrls(Pageable pageable);
}
