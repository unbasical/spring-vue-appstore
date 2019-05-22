package base.appstore.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@SuppressWarnings("serial")
public class JwtAuthenticatedProfile implements Authentication {

    private final UserPrincipal user;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtAuthenticatedProfile(UserPrincipal user, Collection<? extends GrantedAuthority> authorities) {

        this.user = user;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        // This method is not needed.
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}

