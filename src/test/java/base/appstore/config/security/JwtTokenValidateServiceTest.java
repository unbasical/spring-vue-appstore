package base.appstore.config.security;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JwtTokenValidateServiceTest {

    private JwtTokenValidateService jwtTokenValidateServiceUnderTest;
    private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEb25hbGR1cyBNYXhpbXVzIFVsdGltYXR1cyIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE1NjQ1OTg3MTQsImlhdCI6MTU1ODU1MDcxNH0.FBAQ5-no7ycTgbFkOVf4PTbU5KOl_VxgxHRoJ53e-KwhViseqsC5MFQQGiTZRb9L4WRQB_3OqpF_Ic8GENc_HQ";

    @Before
    public void setUp() {

        jwtTokenValidateServiceUnderTest = new JwtTokenValidateService("mySecret");

    }

    @Test
    public void testGetUsernameFromToken() {
        // Setup
        final String expectedResult = "Donaldus Maximus Ultimatus";

        // Run the test
        final String result = jwtTokenValidateServiceUnderTest.getUsernameFromToken(token);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetExpirationDateFromToken() {
        // Setup
        final Date expectedResult = new GregorianCalendar(2019, 6, 31, 20, 45, 14).getTime();

        // Run the test
        final Date result = jwtTokenValidateServiceUnderTest.getExpirationDateFromToken(token);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetAllClaimsFromToken() {

        // Run the test
        final Claims result = jwtTokenValidateServiceUnderTest.getAllClaimsFromToken(token);

        // Verify the results
        assertEquals("{sub=Donaldus Maximus Ultimatus, role=ROLE_USER, exp=1564598714, iat=1558550714}", result.toString());
    }

    @Test
    public void testValidateTokenOpt() {
        // Setup
        final Optional<Boolean> expectedResult = Optional.of(true);

        // Run the test
        final Optional<Boolean> result = jwtTokenValidateServiceUnderTest.validateTokenOpt(token);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testValidateToken() {

        // Run the test
        final Boolean result = jwtTokenValidateServiceUnderTest.validateToken(token);

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void testGetAuthorities() {
        // Setup
        final Collection<GrantedAuthority> expectedResult = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

        // Run the test
        final Collection<GrantedAuthority> result = jwtTokenValidateServiceUnderTest.getAuthorities(token);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
