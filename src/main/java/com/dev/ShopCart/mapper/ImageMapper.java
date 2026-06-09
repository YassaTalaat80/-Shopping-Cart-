package com.dev.ShopCart.mapper;

import com.dev.ShopCart.dto.ImageDto;
import com.dev.ShopCart.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageDto toDto(Image image) {
        return new ImageDto(image.getId(), image.getFileName(), image.getDownloadUrl());
    }
}
