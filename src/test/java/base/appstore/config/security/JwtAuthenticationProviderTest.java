package base.appstore.config.security;

import base.appstore.model.User;
import base.appstore.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class JwtAuthenticationProviderTest {

    @Mock
    private JwtTokenValidateService mockJwtService;
    @Mock
    private UserRepository mockRepo;

    private JwtAuthenticationProvider jwtAuthenticationProviderUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        jwtAuthenticationProviderUnderTest = new JwtAuthenticationProvider(mockJwtService, mockRepo);
    }

    @Test
    public void testAuthenticate() {
        // Setup
        final Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn("Bearer 123434");

        final User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getName()).thenReturn("Name");
        when(user.getEmail()).thenReturn("Email");

        final Authentication expectedResult = null;
        when(mockJwtService.getUsernameFromToken("token")).thenReturn("username");
        when(mockRepo.findOneByName(any())).thenReturn(Optional.of(user));
        when(mockJwtService.getAuthorities("token")).thenReturn(Arrays.asList());
        when(mockJwtService.validateTokenOpt(any())).thenReturn(Optional.of(true));

        // Run the test
        final Authentication result = jwtAuthenticationProviderUnderTest.authenticate(authentication);

        // Verify the results
        assertNotNull(result);
        assertEquals("Name", ((UserPrincipal) result.getPrincipal()).getUsername());
        assertEquals("Email", ((UserPrincipal) result.getPrincipal()).getEmail());
    }

    @Test
    public void testSupports() {
        // Setup
        final Class<?> authentication = JwtAuthenticatedProfile.class;

        // Run the test
        final boolean result = jwtAuthenticationProviderUnderTest.supports(authentication);

        // Verify the results
        assertTrue(result);
    }
}
