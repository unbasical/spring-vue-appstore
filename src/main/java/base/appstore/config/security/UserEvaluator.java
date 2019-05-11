package base.appstore.config.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserEvaluator implements PermissionEvaluator {
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
        final Long receivedId = (Long) targetId;
        return receivedId.equals(principal.getId());
    }
}
