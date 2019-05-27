package base.appstore.services;


import base.appstore.controller.dto.JWTTokenResponse;
import base.appstore.exceptions.InvalidLoginCredentialsException;
import base.appstore.model.User;
import base.appstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public JWTTokenResponse generateJWTToken(String username, String password) {
        Optional<User> userAccount = userRepository.findOneByName(username);

        return userAccount.filter(account -> {
            boolean passwordMatches = passwordEncoder.matches(password, account.getPassword());
            if(!passwordMatches){
                throw new InvalidLoginCredentialsException();
            }
            return passwordMatches;
        })
                .map(account -> JWTTokenResponse.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .name(account.getName())
                        .token(jwtTokenService.generateToken(account))
                        .build())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }
}
