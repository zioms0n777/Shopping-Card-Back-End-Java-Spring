package com.shopbackend.shoppingcard.controller;


import com.shopbackend.shoppingcard.Model.Image;
import com.shopbackend.shoppingcard.Service.image.IImageService;
import com.shopbackend.shoppingcard.dto.ImageDto;
import com.shopbackend.shoppingcard.exception.ResourceNotFoundException;
import com.shopbackend.shoppingcard.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

@PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile>files, @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse(imageDtos, "Upload Success!"));
        } catch (Exception e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), "Upload Error!"));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {

    Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachments; filename=\"" + image.getFileName() + "\"").body(resource);
    }



    @PutMapping("/image/{}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {

   try {
       Image image = imageService.getImageById(imageId);
       if (image != null) {
           imageService.updateImage(file, imageId);
           return ResponseEntity.ok().body(new ApiResponse(imageId, "Update Success!"));
       }
   } catch (ResourceNotFoundException e) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), "Image not found!"));
   }
   return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(INTERNAL_SERVER_ERROR, "Image failed to update!"));
    }


    @DeleteMapping("/image/{}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {

        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok().body(new ApiResponse(imageId, "Delete Success!"));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), "Image not found!"));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(INTERNAL_SERVER_ERROR, "Image failed to update!"));
    }

}
