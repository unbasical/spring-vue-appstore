package base.appstore.controller;

import base.appstore.controller.dto.FeedbackDto;
import base.appstore.repository.FeedbackRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;


@RestController
@RequestMapping("/healthz")
@CrossOrigin
@AllArgsConstructor
public class HealthController {
    @GetMapping()
    public ResponseEntity getHealth() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
