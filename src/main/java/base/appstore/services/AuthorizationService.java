package base.appstore.services;


import base.appstore.config.security.UserPrincipal;
import base.appstore.exceptions.UnauthorizedException;
import base.appstore.model.App;
import base.appstore.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthorizationService {

    @Autowired
    private AppRepository appRepository;


    public void throwIfUnauthorized(Authentication auth, App app) {
        final UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        if (!app.getUser().getId().equals(principal.getId())) {
            throw new UnauthorizedException();
        }
    }

    public Optional<App> getAppIfAuthorized(Authentication auth, Long appID) {
        final Optional<App> app = appRepository.findById(appID);
        app.ifPresent(value -> this.throwIfUnauthorized(auth, value));
        return app;
    }

    public Optional<App> getAppIfAuthorized(Authentication auth, Long userID, Long appID) {
        final Optional<App> app = appRepository.findById(appID);
        final UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        if (!userID.equals(principal.getId())) {
            throw new UnauthorizedException();
        }
        app.ifPresent(value -> this.throwIfUnauthorized(auth, value));
        return app;
    }

}
