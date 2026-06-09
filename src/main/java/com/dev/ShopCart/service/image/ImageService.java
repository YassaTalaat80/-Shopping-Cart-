package com.dev.ShopCart.service.image;

import com.dev.ShopCart.dto.ImageDto;
import com.dev.ShopCart.entity.Image;
import com.dev.ShopCart.entity.Product;
import com.dev.ShopCart.exceptions.ResourceNotFoundException;
import com.dev.ShopCart.repository.ImageRepository;
import com.dev.ShopCart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public ImageDto getImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found!"));
        return toDto(image);
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,
                        () -> { throw new ResourceNotFoundException("Image not found!"); });
    }

    @Override
    @Transactional
    @SneakyThrows
    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        List<Image> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes());
            image.setProduct(product);
            image = imageRepository.save(image);
            image.setDownloadUrl("/api/images/" + image.getId());
            imageRepository.save(image);
            savedImages.add(image);
        }
        return savedImages.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found!"));
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setImage(file.getBytes());
        imageRepository.save(image);
    }

    private ImageDto toDto(Image image) {
        return new ImageDto(image.getId(), image.getFileName(), image.getDownloadUrl());
    }
}
