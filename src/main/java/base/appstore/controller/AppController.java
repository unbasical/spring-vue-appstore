package base.appstore.controller;

import base.appstore.exceptions.AppNotFoundException;
import base.appstore.model.App;
import base.appstore.model.Comment;
import base.appstore.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/apps")
@CrossOrigin
public class AppController {

    @Autowired
    private AppRepository appRepository;

    @GetMapping()
    public List<App> filteredApps(@RequestParam(required = false) String search, @RequestParam(required = false) String tag, @RequestParam(required = false) Long rating) {
        return appRepository.findAll();
    }

    @GetMapping("{id}")
    public App find(@PathVariable Long id) {
        return appRepository.findById(id).orElseThrow(() -> new AppNotFoundException(id));
    }

    @PostMapping()
    public App create(@RequestBody App input) {
        // Todo fix if app already exists
        return appRepository.save(input);
    }

    @PutMapping("{id}")
    public App update(@PathVariable Long id, @RequestBody App input) {
        return appRepository.findById(id).map(app -> {
            app.setTitle(input.getTitle());
            app.setDescription(input.getDescription());
            app.setTags(input.getTags());
            return appRepository.save(app);
        }).orElseGet(() -> {
            return appRepository.save(input);
        });
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        appRepository.deleteById(id);
    }


    @PostMapping("{id}/ratings")
    public void updateRating(@PathVariable Long id, @RequestBody Long rating) {

        App ratingApp = appRepository.getOne(id);
        //ratingApp.addRating(rating);
    }

    @GetMapping("{id}/comments")
    public List<Comment> getComments(@PathVariable Long id) {
        App app = appRepository.getOne(id);
        return app.getComments();
    }

    @PostMapping("{id}/comments")
    public void updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        App app = appRepository.getOne(id);
        app.addComment(comment);
    }

}
