package base.appstore.config.security;

import base.appstore.model.User;
import base.appstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component
public class OwnsApp implements PermissionEvaluator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        // I will not implement this method just because I don't needed in this demo.
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        final Long appId = (Long) targetId;
        final Optional<User> user = this.userRepository.findById(principal.getId());

        return user.map(usr -> usr.getApps().stream().anyMatch(app -> app.getId().equals(appId))).orElse(false);
    }
}
