package com.ryhnik.service;

import com.ryhnik.entity.Master;
import com.ryhnik.entity.PortfolioImage;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.exception.NoSuchMasterException;
import com.ryhnik.repository.MasterRepository;
import com.ryhnik.repository.PortfolioImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioImageService {

    private final PortfolioImageRepository imageRepository;
    private final MasterRepository masterRepository;
    private final FileService fileService;

    public PortfolioImageService(PortfolioImageRepository imageRepository,
                                 MasterRepository masterRepository,
                                 FileService fileService) {
        this.imageRepository = imageRepository;
        this.masterRepository = masterRepository;
        this.fileService = fileService;
    }

    public List<PortfolioImage> create(List<MultipartFile> multipartFiles, String username) {
        Master master = masterRepository.findMasterByUsername(username)
                .orElseThrow(() -> new NoSuchMasterException(username));

        List<PortfolioImage> portfolioImages = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String fileName = fileService.save(file);
            String uri = fileService.findByName(fileName).getHttpRequest().getRequestLine().getUri();

            PortfolioImage portfolioImage = new PortfolioImage();
            portfolioImage.setImageUrl(uri);
            portfolioImage.setMaster(master);
            PortfolioImage savedImage = imageRepository.save(portfolioImage);
            portfolioImages.add(savedImage);
        }

        return portfolioImages;
    }

    public List<PortfolioImage> getAllImagesByMasterId(Long masterId) {
        return imageRepository.getAllByMasterId(masterId);
    }

    public void deleteByIds(List<Long> ids) {
        List<PortfolioImage> images = imageRepository.findAllById(ids);
        if (images.isEmpty()) {
            throw ExceptionBuilder.builder(Code.PORTFOLIO_IMAGE_NOT_FOUND)
                    .withMessage("Can't find images with ids = " + ids)
                    .build(MasterClubException.class);
        }

        imageRepository.deleteAll(images);
    }
}
