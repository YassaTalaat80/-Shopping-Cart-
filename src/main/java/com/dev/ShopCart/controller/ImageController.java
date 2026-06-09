package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.ImageDto;
import com.dev.ShopCart.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> getImageById(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImageById(@PathVariable Long id) {
        imageService.deleteImageById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/api/products/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ImageDto>> saveImages(@PathVariable Long productId,
                                                     @RequestParam List<MultipartFile> files) {
        return new ResponseEntity<>(imageService.saveImages(productId, files), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateImage(@RequestParam MultipartFile file, @PathVariable Long id) {
        imageService.updateImage(file, id);
        return ResponseEntity.ok().build();
    }
}
