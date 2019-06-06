package base.appstore.controller;

import base.appstore.controller.dto.FeedbackDto;
import base.appstore.repository.FeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;


@RestController
@RequestMapping("/api/feedback")
@CrossOrigin
@AllArgsConstructor
public class FeedbackController {

    private FeedbackRepository feedbackRepo;

    @PostMapping()
    public FeedbackDto createFeedback(@RequestBody FeedbackDto input) {
        return new FeedbackDto(feedbackRepo.save(input.toEntity()));
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public Stream<FeedbackDto> getAllFeedback() {
        return feedbackRepo.findAll().stream().map(FeedbackDto::new);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public void deleteFeedback(@PathVariable Long id) {
        feedbackRepo.deleteById(id);
    }

}
