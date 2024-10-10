package com.shopbackend.shoppingcard.Service.image;

import com.shopbackend.shoppingcard.Model.Image;
import com.shopbackend.shoppingcard.Model.Product;
import com.shopbackend.shoppingcard.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);
    void deleteImageById(Long id);

    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile file, Long imageId);
}
