package base.appstore.controller;

import base.appstore.controller.response.UploadFileResponse;
import base.appstore.exceptions.ImageNotStorableException;
import base.appstore.exceptions.ResourceNotFoundException;
import base.appstore.model.App;
import base.appstore.model.Logo;
import base.appstore.model.Screenshot;
import base.appstore.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class FileController {

    @Autowired
    private AppRepository appRepository;

    @PostMapping("{userID}/apps/{appID}/logo")
    public UploadFileResponse uploadLogo(@PathVariable Long userID, @PathVariable Long appID, @RequestParam("file") MultipartFile file) {
        final App match = getAppByIdOrThrow(appID);

        try {
            match.setLogo(Logo.builder()
                    .filename(file.getName())
                    .contentType(file.getContentType())
                    .imageData(file.getBytes())
                    .build());
            appRepository.save(match);
        } catch (IOException e) {
            throw new ImageNotStorableException();
        }

        return new UploadFileResponse(file.getName(), file.getContentType(), file.getSize());
    }

    @PostMapping("{userID}/apps/{appID}/screenshots")
    public UploadFileResponse uploadScreenshot(@PathVariable Long userID, @PathVariable Long appID, @RequestParam("file") MultipartFile file) {
        final App match = getAppByIdOrThrow(appID);

        try {
            match.getScreenshots().add(Screenshot.builder()
                    .filename(file.getName())
                    .contentType(file.getContentType())
                    .imageData(file.getBytes())
                    .build());
            appRepository.save(match);
        } catch (IOException e) {
            throw new ImageNotStorableException();
        }


        return new UploadFileResponse(file.getName(), file.getContentType(), file.getSize());
    }

    @GetMapping(value = "{userID}/apps/{appID}/logo", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadLogo(@PathVariable Long userID, @PathVariable Long appID) {
        final Logo logo = getAppByIdOrThrow(appID).getLogo();
        if (logo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(logo.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + logo.getFilename() + "\"")
                .body(logo.getImageData());
    }

    @GetMapping(value = "{userID}/apps/{appID}/screenshots/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadScreenshot(@PathVariable Long userID, @PathVariable Long appID, @PathVariable Long id) {
        final App app = getAppByIdOrThrow(appID);

        final Screenshot screenshot = app.getScreenshots().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst().orElseThrow(ResourceNotFoundException::new);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(screenshot.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + screenshot.getFilename() + "\"")
                .body(screenshot.getImageData());
    }

    private App getAppByIdOrThrow(Long id) throws ResourceNotFoundException {
        return appRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

}
