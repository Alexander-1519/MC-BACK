package com.ryhnik.service;

import com.ryhnik.entity.Master;
import com.ryhnik.entity.MasterReview;
import com.ryhnik.entity.User;
import com.ryhnik.exception.*;
import com.ryhnik.repository.MasterRepository;
import com.ryhnik.repository.MasterReviewRepository;
import com.ryhnik.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MasterReviewService {

    private final MasterReviewRepository masterReviewRepository;
    private final UserRepository userRepository;
    private final MasterRepository masterRepository;

    public MasterReviewService(MasterReviewRepository masterReviewRepository,
                               UserRepository userRepository,
                               MasterRepository masterRepository) {
        this.masterReviewRepository = masterReviewRepository;
        this.userRepository = userRepository;
        this.masterRepository = masterRepository;
    }

    public MasterReview create(MasterReview masterReview, String username, Long masterId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(username));

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new NoSuchMasterException(masterId));

        masterReview.setMaster(master);
        masterReview.setUser(user);

        return masterReviewRepository.save(masterReview);
    }

    public MasterReview getById(Long id) {
        return masterReviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchMasterReviewException(id));
    }

    public Page<MasterReview> getAll(Long masterId, Pageable pageable) {
        return masterReviewRepository.findAllByMasterId(masterId, pageable);
    }

    public MasterReview updateMasterReview(Long reviewId, String username, MasterReview masterReview) {
        MasterReview masterReviewFromDb = masterReviewRepository.findByIdAndUserUsername(reviewId, username)
                .orElseThrow(() -> ExceptionBuilder.builder(Code.MASTER_REVIEW_EXCEPTION)
                        .withMessage("Can't find master review with id = " + reviewId)
                        .build(MasterClubException.class));

        masterReviewFromDb.setReview(masterReview.getReview());
        if (masterReview.getRating() != null) {
            masterReviewFromDb.setRating(masterReview.getRating());
        }

        return masterReviewRepository.save(masterReviewFromDb);
    }

    public void deleteById(Long id) {
        boolean existsById = masterReviewRepository.existsById(id);
        if (!existsById) {
            throw new NoSuchMasterReviewException(id);
        }

        masterReviewRepository.deleteById(id);
    }
}