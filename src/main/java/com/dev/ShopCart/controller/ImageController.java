package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.ImageDto;
import com.dev.ShopCart.service.image.IImageService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> getImageById(@Positive(message = "Image ID must be a positive number") @PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImageById(@Positive(message = "Image ID must be a positive number") @PathVariable Long id) {
        imageService.deleteImageById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ImageDto>> saveImages(@Positive(message = "Product ID must be a positive number") @PathVariable Long productId,
                                                     @RequestParam List<MultipartFile> files) {
        return new ResponseEntity<>(imageService.saveImages(productId, files), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateImage(@RequestParam MultipartFile file, @Positive(message = "Image ID must be a positive number") @PathVariable Long id) {
        imageService.updateImage(file, id);
        return ResponseEntity.ok().build();
    }
}
