package base.appstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/apps")
@CrossOrigin
public class AppController {

    @Autowired
    private AppRepository appRepository;


    @GetMapping("/apps")
    public List<App> all() {
        return appRepository.findAll();
    }

    @GetMapping("/apps?search=<>&tag=<>&rating=<>")
    public List<App> filteredApps(@RequestParam String search,@RequestParam String tag,@RequestParam Long rating){
        return all().stream()
                .filter(app->app.getAverageRating()>=rating)
                .filter(app->app.getTags().contains(tag))
                .filter(app->app.getTitle().contains(search))
                //TODO impore search filter
                .collect(Collectors.toList());
    }

    @GetMapping("/apps/{id}")
    public App find(@PathVariable Long id) {
        return appRepository.findById(id).orElseThrow(() -> new AppNotFoundException(id));
    }

    @PostMapping("/apps")
    public App create(@RequestBody App input) {
        return appRepository.save(new App(input.getText(), input.getTags(), input.getTitle()));
    }

    @PutMapping("/apps/{id}")
    public App update(@PathVariable Long id, @RequestBody App input) {
        return appRepository.findById(id).map(app -> {
            app.setId(id);
            app.setTitle(input.getTitle());
            app.setText(input.getText());
            app.setTags(input.getTags());
            return appRepository.save(app);
        }).orElseGet(() -> {
            input.setId(id);
            ;
            return appRepository.save(input);
        });
    }

    @DeleteMapping("/apps/{id}")
    public void delete(@PathVariable Long id) {
        appRepository.deleteById(id);
    }


    @PostMapping("/apps/{id}/ratings")
    public void updateRating(@PathVariable Long id, @RequestBody Long rating) {

        App ratingApp = appRepository.getOne(id);
        //ratingApp.addRating(rating);
    }

    @GetMapping("/apps/{id}/comments")
    public List<Comment> getComments(@PathVariable Long id) {
        App app = appRepository.getOne(id);
        return app.getComments();
    }

    @PostMapping("/apps/{id}/comments")
    public void updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        App app = appRepository.getOne(id);
        app.addComment(comment);
    }

}
