package tn.softtodo.shoptv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.domain.Video;
import tn.softtodo.shoptv.repository.VideoRepository;
import tn.softtodo.shoptv.service.VideoService;

/**
 * Service Implementation for managing {@link Video}.
 */
@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    private final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);

    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public Video save(Video video) {
        log.debug("Request to save Video : {}", video);
        return videoRepository.save(video);
    }

    @Override
    public Video update(Video video) {
        log.debug("Request to update Video : {}", video);
        return videoRepository.save(video);
    }

    @Override
    public Optional<Video> partialUpdate(Video video) {
        log.debug("Request to partially update Video : {}", video);

        return videoRepository
            .findById(video.getId())
            .map(existingVideo -> {
                if (video.getVideoName() != null) {
                    existingVideo.setVideoName(video.getVideoName());
                }
                if (video.getVideoDuration() != null) {
                    existingVideo.setVideoDuration(video.getVideoDuration());
                }
                if (video.getVideoSize() != null) {
                    existingVideo.setVideoSize(video.getVideoSize());
                }
                if (video.getVideoCategory() != null) {
                    existingVideo.setVideoCategory(video.getVideoCategory());
                }
                if (video.getVideoRating() != null) {
                    existingVideo.setVideoRating(video.getVideoRating());
                }
                if (video.getVideoStatus() != null) {
                    existingVideo.setVideoStatus(video.getVideoStatus());
                }
                if (video.getVideoPublicUrl() != null) {
                    existingVideo.setVideoPublicUrl(video.getVideoPublicUrl());
                }
                if (video.getVideoPath() != null) {
                    existingVideo.setVideoPath(video.getVideoPath());
                }
                if (video.getVideoVisibleFor() != null) {
                    existingVideo.setVideoVisibleFor(video.getVideoVisibleFor());
                }

                return existingVideo;
            })
            .map(videoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Video> findAll(Pageable pageable) {
        log.debug("Request to get all Videos");
        return videoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Video> findOne(Long id) {
        log.debug("Request to get Video : {}", id);
        return videoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Video : {}", id);
        videoRepository.deleteById(id);
    }
}
