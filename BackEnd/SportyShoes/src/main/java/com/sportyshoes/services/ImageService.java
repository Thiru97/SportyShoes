package com.sportyshoes.services;

import com.sportyshoes.repository.ImageRepository;
import com.sportyshoes.model.Image;
import com.sportyshoes.util.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    ImageRepository repository ;

    @Autowired
    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Image uploadImage(MultipartFile file) throws IOException {

        Image image = new Image();

        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImageData(ImageUtil.compressImage(file.getBytes()));

        return repository.save(image);
    }



    public byte[]	downloadImage(int id) {
        Optional<Image> dbImage = repository.findById(id);
        byte[] images = ImageUtil.decompressImage(dbImage.get().getImageData());
        return images;
    }
}
