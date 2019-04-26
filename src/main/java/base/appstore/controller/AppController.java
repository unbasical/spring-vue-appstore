package base.appstore.controller;

import base.appstore.controller.dto.AppDto;
import base.appstore.controller.dto.CommentDto;
import base.appstore.controller.dto.RatingDto;
import base.appstore.exceptions.ResourceNotFoundException;
import base.appstore.model.App;
import base.appstore.model.Comment;
import base.appstore.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;


@RestController
@RequestMapping("/apps")
@CrossOrigin
public class AppController {

    @Autowired
    private AppRepository appRepository;

    @GetMapping()
    public Stream<AppDto> getAllAppsFiltered(@RequestParam(required = false) String search
            , @RequestParam(required = false) String tag
            , @RequestParam(required = false) Long rating) {
        return appRepository.findAll()
                .stream()
                .map(AppDto::new);
    }

    @GetMapping("{id}")
    public AppDto getApp(@PathVariable Long id) {
        return appRepository.findById(id).map(AppDto::new).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping("{id}")
    public void deleteApp(@PathVariable Long id) {
        appRepository.deleteById(id);
    }

    @PostMapping("{id}/ratings")
    public void updateRating(@PathVariable Long id, @RequestBody RatingDto rating) {

        final App app = appRepository.getOne(id);
        app.addRating(rating.toEntity());
        appRepository.save(app);
    }

    @GetMapping("{id}/raitings")
    public List<Comment> getComments(@PathVariable Long id) {
        App app = appRepository.getOne(id);
        return app.getComments();
    }

    @PostMapping("{id}/comments")
    public void updateComment(@PathVariable Long id, @RequestBody CommentDto comment) {
        App app = appRepository.getOne(id);
        app.addComment(comment.toEntity());
    }

}
