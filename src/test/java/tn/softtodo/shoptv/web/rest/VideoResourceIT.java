package tn.softtodo.shoptv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.IntegrationTest;
import tn.softtodo.shoptv.domain.Video;
import tn.softtodo.shoptv.repository.VideoRepository;

/**
 * Integration tests for the {@link VideoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VideoResourceIT {

    private static final String DEFAULT_VIDEO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_RATING = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_RATING = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_PUBLIC_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_PUBLIC_URL = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_VISIBLE_FOR = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_VISIBLE_FOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/videos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideoMockMvc;

    private Video video;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Video createEntity(EntityManager em) {
        Video video = new Video()
            .videoName(DEFAULT_VIDEO_NAME)
            .videoDuration(DEFAULT_VIDEO_DURATION)
            .videoSize(DEFAULT_VIDEO_SIZE)
            .videoCategory(DEFAULT_VIDEO_CATEGORY)
            .videoRating(DEFAULT_VIDEO_RATING)
            .videoStatus(DEFAULT_VIDEO_STATUS)
            .videoPublicUrl(DEFAULT_VIDEO_PUBLIC_URL)
            .videoPath(DEFAULT_VIDEO_PATH)
            .videoVisibleFor(DEFAULT_VIDEO_VISIBLE_FOR);
        return video;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Video createUpdatedEntity(EntityManager em) {
        Video video = new Video()
            .videoName(UPDATED_VIDEO_NAME)
            .videoDuration(UPDATED_VIDEO_DURATION)
            .videoSize(UPDATED_VIDEO_SIZE)
            .videoCategory(UPDATED_VIDEO_CATEGORY)
            .videoRating(UPDATED_VIDEO_RATING)
            .videoStatus(UPDATED_VIDEO_STATUS)
            .videoPublicUrl(UPDATED_VIDEO_PUBLIC_URL)
            .videoPath(UPDATED_VIDEO_PATH)
            .videoVisibleFor(UPDATED_VIDEO_VISIBLE_FOR);
        return video;
    }

    @BeforeEach
    public void initTest() {
        video = createEntity(em);
    }

    @Test
    @Transactional
    void createVideo() throws Exception {
        int databaseSizeBeforeCreate = videoRepository.findAll().size();
        // Create the Video
        restVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isCreated());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeCreate + 1);
        Video testVideo = videoList.get(videoList.size() - 1);
        assertThat(testVideo.getVideoName()).isEqualTo(DEFAULT_VIDEO_NAME);
        assertThat(testVideo.getVideoDuration()).isEqualTo(DEFAULT_VIDEO_DURATION);
        assertThat(testVideo.getVideoSize()).isEqualTo(DEFAULT_VIDEO_SIZE);
        assertThat(testVideo.getVideoCategory()).isEqualTo(DEFAULT_VIDEO_CATEGORY);
        assertThat(testVideo.getVideoRating()).isEqualTo(DEFAULT_VIDEO_RATING);
        assertThat(testVideo.getVideoStatus()).isEqualTo(DEFAULT_VIDEO_STATUS);
        assertThat(testVideo.getVideoPublicUrl()).isEqualTo(DEFAULT_VIDEO_PUBLIC_URL);
        assertThat(testVideo.getVideoPath()).isEqualTo(DEFAULT_VIDEO_PATH);
        assertThat(testVideo.getVideoVisibleFor()).isEqualTo(DEFAULT_VIDEO_VISIBLE_FOR);
    }

    @Test
    @Transactional
    void createVideoWithExistingId() throws Exception {
        // Create the Video with an existing ID
        video.setId(1L);

        int databaseSizeBeforeCreate = videoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isBadRequest());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVideos() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList
        restVideoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(video.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoName").value(hasItem(DEFAULT_VIDEO_NAME)))
            .andExpect(jsonPath("$.[*].videoDuration").value(hasItem(DEFAULT_VIDEO_DURATION)))
            .andExpect(jsonPath("$.[*].videoSize").value(hasItem(DEFAULT_VIDEO_SIZE)))
            .andExpect(jsonPath("$.[*].videoCategory").value(hasItem(DEFAULT_VIDEO_CATEGORY)))
            .andExpect(jsonPath("$.[*].videoRating").value(hasItem(DEFAULT_VIDEO_RATING)))
            .andExpect(jsonPath("$.[*].videoStatus").value(hasItem(DEFAULT_VIDEO_STATUS)))
            .andExpect(jsonPath("$.[*].videoPublicUrl").value(hasItem(DEFAULT_VIDEO_PUBLIC_URL)))
            .andExpect(jsonPath("$.[*].videoPath").value(hasItem(DEFAULT_VIDEO_PATH)))
            .andExpect(jsonPath("$.[*].videoVisibleFor").value(hasItem(DEFAULT_VIDEO_VISIBLE_FOR)));
    }

    @Test
    @Transactional
    void getVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get the video
        restVideoMockMvc
            .perform(get(ENTITY_API_URL_ID, video.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(video.getId().intValue()))
            .andExpect(jsonPath("$.videoName").value(DEFAULT_VIDEO_NAME))
            .andExpect(jsonPath("$.videoDuration").value(DEFAULT_VIDEO_DURATION))
            .andExpect(jsonPath("$.videoSize").value(DEFAULT_VIDEO_SIZE))
            .andExpect(jsonPath("$.videoCategory").value(DEFAULT_VIDEO_CATEGORY))
            .andExpect(jsonPath("$.videoRating").value(DEFAULT_VIDEO_RATING))
            .andExpect(jsonPath("$.videoStatus").value(DEFAULT_VIDEO_STATUS))
            .andExpect(jsonPath("$.videoPublicUrl").value(DEFAULT_VIDEO_PUBLIC_URL))
            .andExpect(jsonPath("$.videoPath").value(DEFAULT_VIDEO_PATH))
            .andExpect(jsonPath("$.videoVisibleFor").value(DEFAULT_VIDEO_VISIBLE_FOR));
    }

    @Test
    @Transactional
    void getNonExistingVideo() throws Exception {
        // Get the video
        restVideoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        int databaseSizeBeforeUpdate = videoRepository.findAll().size();

        // Update the video
        Video updatedVideo = videoRepository.findById(video.getId()).get();
        // Disconnect from session so that the updates on updatedVideo are not directly saved in db
        em.detach(updatedVideo);
        updatedVideo
            .videoName(UPDATED_VIDEO_NAME)
            .videoDuration(UPDATED_VIDEO_DURATION)
            .videoSize(UPDATED_VIDEO_SIZE)
            .videoCategory(UPDATED_VIDEO_CATEGORY)
            .videoRating(UPDATED_VIDEO_RATING)
            .videoStatus(UPDATED_VIDEO_STATUS)
            .videoPublicUrl(UPDATED_VIDEO_PUBLIC_URL)
            .videoPath(UPDATED_VIDEO_PATH)
            .videoVisibleFor(UPDATED_VIDEO_VISIBLE_FOR);

        restVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVideo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVideo))
            )
            .andExpect(status().isOk());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
        Video testVideo = videoList.get(videoList.size() - 1);
        assertThat(testVideo.getVideoName()).isEqualTo(UPDATED_VIDEO_NAME);
        assertThat(testVideo.getVideoDuration()).isEqualTo(UPDATED_VIDEO_DURATION);
        assertThat(testVideo.getVideoSize()).isEqualTo(UPDATED_VIDEO_SIZE);
        assertThat(testVideo.getVideoCategory()).isEqualTo(UPDATED_VIDEO_CATEGORY);
        assertThat(testVideo.getVideoRating()).isEqualTo(UPDATED_VIDEO_RATING);
        assertThat(testVideo.getVideoStatus()).isEqualTo(UPDATED_VIDEO_STATUS);
        assertThat(testVideo.getVideoPublicUrl()).isEqualTo(UPDATED_VIDEO_PUBLIC_URL);
        assertThat(testVideo.getVideoPath()).isEqualTo(UPDATED_VIDEO_PATH);
        assertThat(testVideo.getVideoVisibleFor()).isEqualTo(UPDATED_VIDEO_VISIBLE_FOR);
    }

    @Test
    @Transactional
    void putNonExistingVideo() throws Exception {
        int databaseSizeBeforeUpdate = videoRepository.findAll().size();
        video.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, video.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(video))
            )
            .andExpect(status().isBadRequest());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVideo() throws Exception {
        int databaseSizeBeforeUpdate = videoRepository.findAll().size();
        video.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(video))
            )
            .andExpect(status().isBadRequest());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVideo() throws Exception {
        int databaseSizeBeforeUpdate = videoRepository.findAll().size();
        video.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVideoWithPatch() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        int databaseSizeBeforeUpdate = videoRepository.findAll().size();

        // Update the video using partial update
        Video partialUpdatedVideo = new Video();
        partialUpdatedVideo.setId(video.getId());

        partialUpdatedVideo.videoDuration(UPDATED_VIDEO_DURATION).videoRating(UPDATED_VIDEO_RATING).videoPath(UPDATED_VIDEO_PATH);

        restVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideo))
            )
            .andExpect(status().isOk());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
        Video testVideo = videoList.get(videoList.size() - 1);
        assertThat(testVideo.getVideoName()).isEqualTo(DEFAULT_VIDEO_NAME);
        assertThat(testVideo.getVideoDuration()).isEqualTo(UPDATED_VIDEO_DURATION);
        assertThat(testVideo.getVideoSize()).isEqualTo(DEFAULT_VIDEO_SIZE);
        assertThat(testVideo.getVideoCategory()).isEqualTo(DEFAULT_VIDEO_CATEGORY);
        assertThat(testVideo.getVideoRating()).isEqualTo(UPDATED_VIDEO_RATING);
        assertThat(testVideo.getVideoStatus()).isEqualTo(DEFAULT_VIDEO_STATUS);
        assertThat(testVideo.getVideoPublicUrl()).isEqualTo(DEFAULT_VIDEO_PUBLIC_URL);
        assertThat(testVideo.getVideoPath()).isEqualTo(UPDATED_VIDEO_PATH);
        assertThat(testVideo.getVideoVisibleFor()).isEqualTo(DEFAULT_VIDEO_VISIBLE_FOR);
    }

    @Test
    @Transactional
    void fullUpdateVideoWithPatch() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        int databaseSizeBeforeUpdate = videoRepository.findAll().size();

        // Update the video using partial update
        Video partialUpdatedVideo = new Video();
        partialUpdatedVideo.setId(video.getId());

        partialUpdatedVideo
            .videoName(UPDATED_VIDEO_NAME)
            .videoDuration(UPDATED_VIDEO_DURATION)
            .videoSize(UPDATED_VIDEO_SIZE)
            .videoCategory(UPDATED_VIDEO_CATEGORY)
            .videoRating(UPDATED_VIDEO_RATING)
            .videoStatus(UPDATED_VIDEO_STATUS)
            .videoPublicUrl(UPDATED_VIDEO_PUBLIC_URL)
            .videoPath(UPDATED_VIDEO_PATH)
            .videoVisibleFor(UPDATED_VIDEO_VISIBLE_FOR);

        restVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideo))
            )
            .andExpect(status().isOk());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
        Video testVideo = videoList.get(videoList.size() - 1);
        assertThat(testVideo.getVideoName()).isEqualTo(UPDATED_VIDEO_NAME);
        assertThat(testVideo.getVideoDuration()).isEqualTo(UPDATED_VIDEO_DURATION);
        assertThat(testVideo.getVideoSize()).isEqualTo(UPDATED_VIDEO_SIZE);
        assertThat(testVideo.getVideoCategory()).isEqualTo(UPDATED_VIDEO_CATEGORY);
        assertThat(testVideo.getVideoRating()).isEqualTo(UPDATED_VIDEO_RATING);
        assertThat(testVideo.getVideoStatus()).isEqualTo(UPDATED_VIDEO_STATUS);
        assertThat(testVideo.getVideoPublicUrl()).isEqualTo(UPDATED_VIDEO_PUBLIC_URL);
        assertThat(testVideo.getVideoPath()).isEqualTo(UPDATED_VIDEO_PATH);
        assertThat(testVideo.getVideoVisibleFor()).isEqualTo(UPDATED_VIDEO_VISIBLE_FOR);
    }

    @Test
    @Transactional
    void patchNonExistingVideo() throws Exception {
        int databaseSizeBeforeUpdate = videoRepository.findAll().size();
        video.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, video.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(video))
            )
            .andExpect(status().isBadRequest());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVideo() throws Exception {
        int databaseSizeBeforeUpdate = videoRepository.findAll().size();
        video.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(video))
            )
            .andExpect(status().isBadRequest());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVideo() throws Exception {
        int databaseSizeBeforeUpdate = videoRepository.findAll().size();
        video.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(video)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        int databaseSizeBeforeDelete = videoRepository.findAll().size();

        // Delete the video
        restVideoMockMvc
            .perform(delete(ENTITY_API_URL_ID, video.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
