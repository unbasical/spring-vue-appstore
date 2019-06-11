package base.appstore.controller;

import base.appstore.controller.dto.AppDto;
import base.appstore.controller.dto.UserDto;
import base.appstore.exceptions.ResourceExistsException;
import base.appstore.exceptions.ResourceNotFoundException;
import base.appstore.model.App;
import base.appstore.model.Role;
import base.appstore.model.User;
import base.appstore.repository.AppRepository;
import base.appstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.stream.Stream;


@RestController
@RequestMapping("/api/users")
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private AppRepository appRepo;
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @PostMapping()
    public UserDto createUser(@RequestBody UserDto input) {
        if (userRepo.findOneByName(input.getName()).isPresent()) {
            throw new ResourceExistsException();
        }

        input.setPassword(passwordEncoder.encode(input.getPassword()));
        final User user = input.toEntity();
        user.setRole(Role.USER);

        return new UserDto(userRepo.save(user));
    }

    @PutMapping("{userID}/role")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public void assignRole(@PathVariable Long userID, @RequestBody String role) {
        userRepo.findById(userID).ifPresent(user -> {
            user.setRole(Role.valueOf(role));
            userRepo.save(user);
        });
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public Stream<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(UserDto::new);
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER', 'DEVELOPER', 'ADMIN')")
    public UserDto getUser(@PathVariable Long id) {
        return userRepo.findById(id).map(UserDto::new).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

    @PostMapping("{userID}/apps")
    @PreAuthorize("isAuthenticated() and hasAnyRole('DEVELOPER', 'ADMIN')")
    public AppDto createApp(@PathVariable Long userID, @RequestBody AppDto input) {
        final App app = appRepo.findOne(Example.of(input.toEntity())).orElse(null);

        if (Objects.nonNull(app)) {
            throw new ResourceExistsException();
        }

        return userRepo.findById(userID).map(user -> {
            final App receivedApp = input.toEntity();
            receivedApp.setUser(user);
            return receivedApp;
        }).map(appRepo::save).map(AppDto::new).orElseThrow(ResourceNotFoundException::new);
    }


    @PutMapping("{userID}/apps/{appID}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('DEVELOPER', 'ADMIN')")
    public AppDto updateApp(@PathVariable Long userID, @PathVariable Long appID, @RequestBody AppDto input, Authentication auth) {
        final App receivedApp = input.toEntity();

        return appRepo.findById(appID).map(app -> {
            app.setTitle(receivedApp.getTitle());
            app.setDescription(receivedApp.getDescription());
            app.setTags(receivedApp.getTags());
            return new AppDto(appRepo.save(app));
        }).orElseGet(() -> new AppDto(appRepo.save(receivedApp)));
    }

    @DeleteMapping("{userID}/apps/{appID}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('DEVELOPER', 'ADMIN')")
    public boolean deleteApp(@PathVariable Long userID, @PathVariable Long appID) {
        appRepo.deleteById(appID);
        return true;
    }

}
