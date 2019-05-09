package base.appstore.services;


import base.appstore.model.JWTTokenResponse;
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
    private UserRepository accountRepository;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository accountRepository, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public JWTTokenResponse generateJWTToken(String username, String password) {
        Optional<User> userAccount = accountRepository.findOneByUsername(username);

        return userAccount.filter(account -> passwordEncoder.matches(password, account.getPassword()))
                .map(account -> new JWTTokenResponse(
                        jwtTokenService.generateToken(userAccount.get())))
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }
}
