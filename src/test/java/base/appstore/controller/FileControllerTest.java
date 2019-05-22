package base.appstore.controller;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    }

    @After
    public void cleanData() {
        userRepo.deleteAll();
        appRepo.deleteAll();
    }


    @Test
    @WithMockUser(roles = "DEVELOPER")
    public void testUploadLogoAsDeveloper() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/users/" + this.testUser.getId() + "/apps/" + this.testApp.getId() + "/logo")
                .file("file", firstFile.getBytes()))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUploadLogoAsAdmin() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/users/" + this.testUser.getId() + "/apps/" + this.testApp.getId() + "/logo")
                .file("file", firstFile.getBytes()))
                .andExpect(status().is(200));
    }

    @Test
    public void testUploadLogoUnauthorized() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/users/" + this.testUser.getId() + "/apps/" + this.testApp.getId() + "/logo")
                .file("file", firstFile.getBytes()))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser(roles = "DEVELOPER")
    public void testUploadScreenshotAsDeveloper() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/users/" + this.testUser.getId() + "/apps/" + this.testApp.getId() + "/screenshots")
                .file("file", firstFile.getBytes()))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUploadScreenshotAsAdmin() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/users/" + this.testUser.getId() + "/apps/" + this.testApp.getId() + "/screenshots")
                .file("file", firstFile.getBytes()))
                .andExpect(status().is(200));
    }

    @Test
    public void testUploadScreenshotUnauthorized() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/users/" + this.testUser.getId() + "/apps/" + this.testApp.getId() + "/screenshots")
                .file("file", firstFile.getBytes()))
                .andExpect(status().is(401));
    }

    @Test
    public void downloadNotExistingLogo() throws Exception {
        mockMvc.perform(get("/api/apps/" + this.testApp.getId() + "/logo"))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void downloadExistingLogo() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/users/" + this.testUser.getId() + "/apps/" + this.testApp.getId() + "/screenshots")
                .file("file", firstFile.getBytes()))
                .andExpect(status().is(200));

        mockMvc.perform(get("/api/apps/" + this.testApp.getId() + "/screenshots/"))
                .andExpect(status().is(200));
    }

}
