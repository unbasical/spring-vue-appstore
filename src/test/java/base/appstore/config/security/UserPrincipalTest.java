package base.appstore.config.security;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserPrincipalTest {

    private UserPrincipal userPrincipalUnderTest;

    @Before
    public void setUp() {
        userPrincipalUnderTest = new UserPrincipal(0L, "username", "email");
    }

    @Test
    public void testSetId() {
        // Setup
        final long id = 0L;

        // Run the test
        userPrincipalUnderTest.setId(id);

        // Verify the results
        assertEquals(id, userPrincipalUnderTest.getId());
    }

    @Test
    public void testSetUsername() {
        // Setup
        final String username = "username";

        // Run the test
        userPrincipalUnderTest.setUsername(username);

        // Verify the results
        assertEquals(username, userPrincipalUnderTest.getUsername());
    }

    @Test
    public void testSetEmail() {
        // Setup
        final String email = "email";

        // Run the test
        userPrincipalUnderTest.setEmail(email);

        // Verify the results
        assertEquals(email, userPrincipalUnderTest.getEmail());
    }

    @Test
    public void testToString() {
        // Setup
        final String expectedResult = "UserPrincipal(id=0, username=username, email=email)";

        // Run the test
        final String result = userPrincipalUnderTest.toString();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testEquals() {
        // Setup
        final Object o = new UserPrincipal(0L, "username", "email");

        // Run the test
        final boolean result = userPrincipalUnderTest.equals(o);

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void testHashCode() {
        // Setup
        final int expectedResult = new UserPrincipal(0L, "username", "email").hashCode();

        // Run the test
        final int result = userPrincipalUnderTest.hashCode();

        // Verify the results
        assertEquals(expectedResult, result);
    }

}
