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

    private static final String ADMIN_USER = "swa_student";

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
        Optional<User> admin = userRepo.findOneByName(ADMIN_USER);
      if (!admin.isPresent()) {
        userRepo.save(User.builder()
                .name(ADMIN_USER)
                .email(ADMIN_USER)
                .role(Role.ADMIN)
                .password(passwordEncoder.encode(ADMIN_USER))
                .build());
      }
    };
  }
}
