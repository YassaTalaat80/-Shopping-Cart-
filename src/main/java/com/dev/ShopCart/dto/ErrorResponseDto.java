package com.dev.ShopCart.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto(String path, HttpStatus error, String message, LocalDateTime timestamp) {
}
