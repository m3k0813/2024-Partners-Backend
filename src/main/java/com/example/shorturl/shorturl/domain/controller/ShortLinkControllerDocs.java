package com.example.shorturl.shorturl.domain.controller;

import com.example.shorturl.shorturl.domain.dto.RequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "ShortLink", description = "ShortLink API")
public interface ShortLinkControllerDocs {

    @Parameters(value = {
            @Parameter(name = "originalUrl", description = "Original URL")
    })
    @Operation(summary = "ShortLink", description = "원본 주소를 입력하여 ShortLink를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ShortLink 저장 성공")
    })
    public ResponseEntity<?> createShortUrl(@RequestBody RequestDto requestDto);

    @Operation(summary = "ShortLink", description = "ShortLinks를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ShortLinks 조회 성공")
    })
    public ResponseEntity<?> getShortUrls(Pageable pageable);

    @Parameters(value = {
            @Parameter(name = "originalUrl", description = "Original URL")
    })
    @Operation(summary = "ShortLink", description = "ShortLink를 삭제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ShortLink 삭제 성공")
    })
    public ResponseEntity<?> deleteShortUrl(@RequestBody RequestDto requestDto);


    @Operation(summary = "ShortLink", description = "ShortLink를 통해 Redirect 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ShortLink redirect 성공")
    })
    public RedirectView redirect(@PathVariable String hash);
}
