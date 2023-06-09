package tn.softtodo.shoptv.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import tn.softtodo.shoptv.domain.Video;
import tn.softtodo.shoptv.repository.VideoRepository;
import tn.softtodo.shoptv.service.VideoService;
import tn.softtodo.shoptv.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.softtodo.shoptv.domain.Video}.
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class VideoResource {

    private final Logger log = LoggerFactory.getLogger(VideoResource.class);

    private static final String ENTITY_NAME = "video";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoService videoService;

    private final VideoRepository videoRepository;

    public VideoResource(VideoService videoService, VideoRepository videoRepository) {
        this.videoService = videoService;
        this.videoRepository = videoRepository;
    }

    /**
     * {@code POST  /videos} : Create a new video.
     *
     * @param video the video to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new video, or with status {@code 400 (Bad Request)} if the video has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/videos")
    public ResponseEntity<Video> createVideo(@RequestBody Video video) throws URISyntaxException {
        log.debug("REST request to save Video : {}", video);
        if (video.getId() != null) {
            throw new BadRequestAlertException("A new video cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Video result = videoService.save(video);
        return ResponseEntity
            .created(new URI("/api/videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /videos} : execute command line nexrender.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new video, or with status {@code 400 (Bad Request)} if the video has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/execute-cli-nexrender")
    public ResponseEntity<String> executeCliNexrender(@RequestBody Map<String, Object> json)
        throws URISyntaxException, JSONException, IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonNew = new JSONObject(json);
        // Access the output field
        String output = jsonNew
            .getJSONObject("actions")
            .getJSONArray("postrender")
            .getJSONObject(1) // Assuming you want the output from the second object in the postrender array
            .getString("output");
        try {
            // Specify the file path and name
            String filePath = "C:\\Users\\Oussema\\Desktop\\test\\mainAuto.json";
            String content = objectMapper.writeValueAsString(json);
            // Create a File object
            File file = new File(filePath);
            // Create the file
            boolean created = file.createNewFile();
            // Write content to the file
            objectMapper.writeValue(file, content);
            String newContent = Files.readString(Path.of(filePath));
            newContent.replace("\\", "");
            StringBuilder sbStr = new StringBuilder(newContent);
            sbStr.deleteCharAt(0);
            sbStr.substring(0, sbStr.length() - 1);
            sbStr.toString().replace("\\", "");
            String modifiedContent = sbStr.toString().replace("\\", "");
            modifiedContent.substring(0, modifiedContent.length() - 1);
            StringBuilder sb = new StringBuilder(modifiedContent);
            System.out.println(sb.toString().substring(0, sb.toString().length() - 1));
            Files.write(Paths.get(filePath), sb.toString().substring(0, sb.toString().length() - 1).getBytes(StandardCharsets.UTF_8));
            if (created) {
                System.out.println("File created successfully.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error occurred while creating the file: " + e.getMessage());
        }

        final Process p = Runtime
            .getRuntime()
            .exec("cmd /c nexrender-cli-win64.exe --file mainAuto.json", null, new File("C:\\Users\\Oussema\\Desktop\\test"));

        new Thread(
            new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = null;
                    try {
                        while ((line = input.readLine()) != null) System.out.println(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        )
            .start();
        p.waitFor();
        return ResponseEntity.ok().body(output);
    }

    @GetMapping("/updateFile")
    public String updateFile() throws IOException, JSONException {
        String res = "";
        String filePath = "C:\\workspace\\Springboot\\shop-tv-jhipster\\test.json";

        String testing =
            "{\"id\":10,\"name\":\"\",\"duration\":\"00:00:13\",\"preview\":\"/assets/video/video7_m.mp4\",\"category\":\"category\",\"rating\":2.8,\"assets\":[{\"id\":1,\"name\":\"like\",\"type\":\"text\",\"value\":\"\",\"valueUrl\":\"\"},{\"id\":1,\"name\":\"pexels-artem-podrez-4492216.jpg\",\"type\":\"image\",\"value\":\"\",\"valueUrl\":\"\"}]}";

        Path path = Paths.get(filePath);
        String fileContent = Files.readString(path);
        fileContent = fileContent.substring(0, fileContent.length() - 1);
        fileContent = fileContent + "," + testing + "]";
        System.out.println("File Content:" + fileContent);
        JSONArray jsonArray = new JSONArray(fileContent);

        /*String output = jsonNew
            .getJSONObject("actions")
            .getJSONArray("postrender")
            .getJSONObject(1) // Assuming you want the output from the second object in the postrender array
            .getString("output");*/

        System.out.println(jsonArray);
        JSONObject test = (JSONObject) jsonArray.get(5);

        System.out.println(test);

        return "done";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return "done";
        // Check if the file is empty
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while uploading the file");
        /*if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            // Save the file to a specific location
            String filePath = "/" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            // Return the file path
            return ResponseEntity.ok(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while uploading the file");
        }*/
    }

    @PostMapping("/myendpoint")
    public ResponseEntity<String> processObject(@RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("namge");

        return ResponseEntity.ok("Object processed successfully " + name);
    }

    /**
     * {@code PUT  /videos/:id} : Updates an existing video.
     *
     * @param id the id of the video to save.
     * @param video the video to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated video,
     * or with status {@code 400 (Bad Request)} if the video is not valid,
     * or with status {@code 500 (Internal Server Error)} if the video couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/videos/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Video video)
        throws URISyntaxException {
        log.debug("REST request to update Video : {}, {}", id, video);
        if (video.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, video.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Video result = videoService.update(video);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, video.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /videos/:id} : Partial updates given fields of an existing video, field will ignore if it is null
     *
     * @param id the id of the video to save.
     * @param video the video to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated video,
     * or with status {@code 400 (Bad Request)} if the video is not valid,
     * or with status {@code 404 (Not Found)} if the video is not found,
     * or with status {@code 500 (Internal Server Error)} if the video couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/videos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Video> partialUpdateVideo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Video video)
        throws URISyntaxException {
        log.debug("REST request to partial update Video partially : {}, {}", id, video);
        if (video.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, video.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Video> result = videoService.partialUpdate(video);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, video.getId().toString())
        );
    }

    /**
     * {@code GET  /videos} : get all the videos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videos in body.
     */
    @GetMapping("/videos")
    public ResponseEntity<List<Video>> getAllVideos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Videos");
        Page<Video> page = videoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /videos/:id} : get the "id" video.
     *
     * @param id the id of the video to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the video, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/videos/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable Long id) {
        log.debug("REST request to get Video : {}", id);
        Optional<Video> video = videoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(video);
    }

    /**
     * {@code DELETE  /videos/:id} : delete the "id" video.
     *
     * @param id the id of the video to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        log.debug("REST request to delete Video : {}", id);
        videoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
