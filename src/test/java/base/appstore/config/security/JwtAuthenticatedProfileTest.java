package base.appstore.config.security;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class JwtAuthenticatedProfileTest {

    @Mock
    private UserPrincipal mockUser;

    private JwtAuthenticatedProfile jwtAuthenticatedProfileUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        jwtAuthenticatedProfileUnderTest = new JwtAuthenticatedProfile(mockUser, Arrays.asList());
    }

    @Test
    public void testGetCredentials() {
        // Setup
        final Object expectedResult = null;

        // Run the test
        final Object result = jwtAuthenticatedProfileUnderTest.getCredentials();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetDetails() {
        // Setup
        final Object expectedResult = null;

        // Run the test
        final Object result = jwtAuthenticatedProfileUnderTest.getDetails();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPrincipal() {
        // Setup
        final Object expectedResult = this.mockUser;

        // Run the test
        final Object result = jwtAuthenticatedProfileUnderTest.getPrincipal();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testIsAuthenticated() {
        // Setup

        // Run the test
        final boolean result = jwtAuthenticatedProfileUnderTest.isAuthenticated();

        // Verify the results
        assertTrue(result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetAuthenticated() {
        // Setup
        final boolean isAuthenticated = false;

        // Run the test
        jwtAuthenticatedProfileUnderTest.setAuthenticated(isAuthenticated);

    }

    @Test
    public void testGetName() {
        // Setup
        final String expectedResult = "result";
        when(mockUser.getUsername()).thenReturn("result");

        // Run the test
        final String result = jwtAuthenticatedProfileUnderTest.getName();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
