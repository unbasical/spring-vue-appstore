package base.appstore.controller;

import base.appstore.controller.dto.UploadFileResponse;
import base.appstore.exceptions.ImageNotStorableException;
import base.appstore.exceptions.ResourceNotFoundException;
import base.appstore.model.App;
import base.appstore.model.Logo;
import base.appstore.model.Screenshot;
import base.appstore.repository.AppRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@CrossOrigin
@AllArgsConstructor
public class FileController {

    private AppRepository appRepository;

    @PreAuthorize("isAuthenticated() and (hasRole('DEVELOPER') or hasRole('ADMIN'))")
    @PostMapping("/users/{userID}/apps/{appID}/logo")
    public UploadFileResponse uploadLogo(@PathVariable Long userID, @PathVariable Long appID, @RequestParam("file") MultipartFile file, Authentication auth) {
        final App match = appRepository.findById(appID).orElseThrow(ResourceNotFoundException::new);

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

    @PreAuthorize("isAuthenticated() and (hasRole('DEVELOPER') or hasRole('ADMIN'))")
    @PostMapping("/users/{userID}/apps/{appID}/screenshots")
    public UploadFileResponse uploadScreenshot(@PathVariable Long userID, @PathVariable Long appID, @RequestParam("file") MultipartFile file, Authentication auth) {
        final App match = appRepository.findById(appID).orElseThrow(ResourceNotFoundException::new);

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

    @GetMapping(value = "/apps/{appID}/logo", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadLogo(@PathVariable Long appID) throws IOException {
        Logo logo = getAppByIdOrThrow(appID).getLogo();
        if (logo == null) {
            logo = Logo.builder()
                    .imageData(StreamUtils.copyToByteArray(new ClassPathResource("default_logo.png").getInputStream()))
                    .filename("default.png")
                    .contentType("image/png")
                    .build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(logo.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + logo.getFilename() + "\"")
                .body(logo.getImageData());
    }

    @GetMapping(value = "/apps/{appID}/screenshots/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadScreenshot(@PathVariable Long appID, @PathVariable Long id) {
        final App app = getAppByIdOrThrow(appID);

        final Screenshot screenshot = app.getScreenshots().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst().orElseThrow(ResourceNotFoundException::new);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(screenshot.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + screenshot.getFilename() + "\"")
                .body(screenshot.getImageData());
    }

    @PreAuthorize("isAuthenticated() and (hasRole('DEVELOPER') or hasRole('ADMIN'))")
    @DeleteMapping(value = "/users/{userID}/apps/{appID}/logo")
    public void deleteLogo(@PathVariable Long userID, @PathVariable Long appID) {
        final App app = getAppByIdOrThrow(appID);
        app.setLogo(null);
        appRepository.save(app);
    }

    @PreAuthorize("isAuthenticated() and (hasRole('DEVELOPER') or hasRole('ADMIN'))")
    @DeleteMapping(value = "/users/{userID}/apps/{appID}/screenshots/{id}")
    public void deleteScreenshot(@PathVariable Long userID, @PathVariable Long appID, @PathVariable Long id) {
        final App app = getAppByIdOrThrow(appID);

        app.setScreenshots(app.getScreenshots().stream()
                .filter(s -> !s.getId().equals(id))
                .collect(Collectors.toList())
        );

        appRepository.save(app);
    }


    private App getAppByIdOrThrow(Long id) {
        return appRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

}
