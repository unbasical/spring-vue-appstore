package base;

import base.appstore.model.Role;
import base.appstore.model.User;
import base.appstore.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class Application {

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  CommandLineRunner init(UserRepository userRepo, PasswordEncoder passwordEncoder) {
    return args -> {
      Optional<User> admin = userRepo.findOneByName("swa_student");
      if (!admin.isPresent()) {
        userRepo.save(User.builder()
                .name("swa_student")
                .email("swa_student")
                .role(Role.ADMIN)
                .password(passwordEncoder.encode("swa_student"))
                .build());
      }
    };
  }
}
