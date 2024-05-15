package com.example.shorturl.shorturl.domain.controller;

import com.example.shorturl.shorturl.domain.dto.RequestDto;
import com.example.shorturl.shorturl.domain.dto.ResponseDto;
import com.example.shorturl.shorturl.domain.entity.ShortLinkEntity;
import com.example.shorturl.shorturl.domain.service.ShortLinkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/short-links")
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    public ShortLinkController(ShortLinkService shortLinkService) {
        this.shortLinkService = shortLinkService;
    }

    @PostMapping()
    public ResponseEntity<?> createShortUrl(@RequestBody RequestDto requestDto) {
        System.out.println(requestDto.getOriginalUrl());

        try {
            ResponseDto responseDto = shortLinkService.createShortUrl(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            e.printStackTrace(); // 예외의 스택 트레이스를 출력
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Url 저장에 실패했습니다.");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getShortUrls(Pageable pageable) {
        try {
            Page<ResponseDto> shortLinkPage = shortLinkService.getShortUrls(pageable);
            return ResponseEntity.ok(shortLinkPage.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Url 조회에 실패했습니다.");
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteShortUrl(@RequestBody RequestDto requestDto) {
        try {
            shortLinkService.deleteShortUrl(requestDto.getOriginalUrl());
            return ResponseEntity.ok("Url 삭제에 성공했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Url 삭제에 실패했습니다.");
        }
    }
}