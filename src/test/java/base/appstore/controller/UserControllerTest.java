package base.appstore.controller;

import base.appstore.config.security.JwtAuthenticatedProfile;
import base.appstore.config.security.UserPrincipal;
import base.appstore.model.App;
import base.appstore.model.Role;
import base.appstore.model.User;
import base.appstore.repository.AppRepository;
import base.appstore.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AppRepository appRepo;

    private User testUser;
    private App testApp;

    @Before
    public void initData() {
        testUser = userRepo.save(User.builder()
                .name("Test User")
                .email("test.user@test.de")
                .role(Role.DEVELOPER)
                .build());

        testApp = appRepo.save(App.builder()
                .title("TestApp")
                .description("Fancy description")
                .build());

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        UserPrincipal applicationUser = UserPrincipal.builder().build();
        Authentication authentication = new JwtAuthenticatedProfile(applicationUser, "Test");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @After
    public void cleanData() {
        userRepo.deleteAll();
        appRepo.deleteAll();
    }

    @Test
    @WithMockUser(roles = "DEVELOPER")
    public void createUser() throws Exception {
        //testing thymeleaf sec:authorize with hasPermission
        this.mockMvc.perform(post("/api/users").content("{\n" +
                "\t\"name\": \"Donaldus Maximus Ultimatus\",\n" +
                "\t\"email\": \"donaldus.bene.maximus@hm.edu\",\n" +
                "\t\"password\": \"test\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createApp() throws Exception {
        //testing thymeleaf sec:authorize with hasPermission
        this.mockMvc.perform(post(String.format("/api/users/%d/apps", testUser.getId())).content("{\n" +
                "\t\"title\": \"Test Title1\",\n" +
                "\t\"description\": \"My first description\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteUser() throws Exception {
        //testing thymeleaf sec:authorize with hasPermission
        this.mockMvc.perform(delete("/api/users/" + testUser.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
