package com.ryhnik.controller;

import com.dropbox.core.DbxException;
import com.ryhnik.dto.portfolioimage.PortfolioImageOutputDto;
import com.ryhnik.entity.PortfolioImage;
import com.ryhnik.mapper.PortfolioImageMapper;
import com.ryhnik.service.DropBoxService;
import com.ryhnik.service.FileService;
import com.ryhnik.service.PortfolioImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/portfolio-images", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "PortfolioImages", description = "API for portfolio images")
public class PortfolioImageController {

    private final FileService fileService;
    private final PortfolioImageService imageService;
    private final PortfolioImageMapper portfolioImageMapper;
    private final DropBoxService dropBoxService;

    public PortfolioImageController(FileService fileService,
                                    PortfolioImageService imageService,
                                    PortfolioImageMapper portfolioImageMapper,
                                    DropBoxService dropBoxService) {
        this.fileService = fileService;
        this.imageService = imageService;
        this.portfolioImageMapper = portfolioImageMapper;
        this.dropBoxService = dropBoxService;
    }

//    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
//    public ResponseEntity<List<PortfolioImageOutputDto>> uploadFile(@RequestBody List<MultipartFile> files,
//                                                                    Principal principal) {
//        List<PortfolioImage> portfolioImages = imageService.create(files, principal.getName());
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(portfolioImageMapper.toPortfolioOutputDto(portfolioImages));
//    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file,
                                                                    Principal principal) throws IOException, DbxException {
        String image = dropBoxService.saveImage(file);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PortfolioImageOutputDto>> getAllByMasterId(@PathVariable Long id) {
        List<PortfolioImage> portfolioImages = imageService.getAllImagesByMasterId(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(portfolioImageMapper.toPortfolioOutputDto(portfolioImages));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIds(@PathVariable Long id, @RequestParam List<Long> ids) {
        imageService.deleteByIds(ids);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
