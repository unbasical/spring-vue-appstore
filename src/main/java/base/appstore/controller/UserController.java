package base.appstore.controller;

import base.appstore.controller.dto.AppDto;
import base.appstore.controller.dto.UserDto;
import base.appstore.exceptions.ResourceExistsException;
import base.appstore.exceptions.ResourceNotFoundException;
import base.appstore.model.App;
import base.appstore.model.User;
import base.appstore.repository.AppRepository;
import base.appstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private AppRepository appRepo;
    @Autowired
    private UserRepository userRepo;

    @PostMapping()
    public UserDto createUser(@RequestBody UserDto input) {
        if (userRepo.findOne(Example.of(User.builder().email(input.getEmail()).build())).isPresent()) {
            throw new ResourceExistsException();
        }

        return new UserDto(userRepo.save(input.toEntity()));
    }

    @GetMapping()
    public Stream<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(UserDto::new);
    }

    @GetMapping("{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userRepo.findById(id).map(UserDto::new).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

    @PostMapping("{userID}/apps")
    public AppDto createApp(@PathVariable Long userID, @RequestBody AppDto input) {
        if (appRepo.findOne(Example.of(input.toEntity())).isPresent()) {
            throw new ResourceExistsException();
        }

        return userRepo.findById(userID).map(user -> {
            final App receivedApp = input.toEntity();
            receivedApp.setUser(user);
            return receivedApp;
        }).map(appRepo::save).map(AppDto::new).orElseThrow(ResourceNotFoundException::new);
    }

    @PutMapping("{userID}/apps/{appID}")
    public AppDto updateApp(@PathVariable Long userID, @PathVariable Long appID, @RequestBody AppDto input) {
        final App receivedApp = input.toEntity();
        return appRepo.findById(appID).map(app -> {
            app.setTitle(receivedApp.getTitle());
            app.setDescription(receivedApp.getDescription());
            app.setTags(receivedApp.getTags());
            return new AppDto(appRepo.save(app));
        }).orElseGet(() -> new AppDto(appRepo.save(receivedApp)));
    }

    @DeleteMapping("{userID}/apps/{appID}")
    public void deleteApp(@PathVariable Long userID, @PathVariable Long appID) {
        appRepo.deleteById(appID);
    }

}
