package base.appstore.config.security;

import base.appstore.exceptions.JwtAuthenticationException;
import base.appstore.model.User;
import base.appstore.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private final JwtTokenValidateService jwtService;

    @Autowired
    private UserRepository userRepo;

    public JwtAuthenticationProvider() {
        this(null);
    }

    @Autowired
    public JwtAuthenticationProvider(JwtTokenValidateService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        try {
            String token = (String) authentication.getCredentials();
            String username = jwtService.getUsernameFromToken(token);
            final User user = userRepo.findOneByName(username).orElseThrow(() -> new JwtAuthenticationException("JWT Token validation failed"));
            Collection<? extends GrantedAuthority> authorities;
            authorities = jwtService.getAuthorities(token);

            return jwtService.validateTokenOpt(token)
                    .map(aBoolean -> new JwtAuthenticatedProfile(new UserPrincipal(user), authorities))
                    .orElseThrow(() -> new JwtAuthenticationException("JWT Token validation failed"));

        } catch (JwtException ex) {
            log.error(String.format("Invalid JWT Token: %s", ex.getMessage()));
            throw new JwtAuthenticationException("Failed to verify token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticatedProfile.class.equals(authentication);
    }

    public JwtTokenValidateService getJwtService() {
        return jwtService;
    }
}
