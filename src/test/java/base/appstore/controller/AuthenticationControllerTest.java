package base.appstore.controller;

import base.appstore.controller.dto.UserDto;
import base.appstore.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepo;

    private UserDto testUser;

    @Before
    public void prepareUser() {
        testUser = new UserDto();
        testUser.setName("TestUserAuthenthificator");
        testUser.setEmail("Test@User.com");
        testUser.setPassword("Password");
    }

    @After
    public void cleanData() {
        userRepo.deleteAll();
    }

    @Test
    public void registerUser() throws Exception {
        this.mockMvc.perform(post("/api/users")
                .content("{\n" +
                        "\t\"name\": \"" + testUser.getName() + "\",\n" +
                        "\t\"email\": \"" + testUser.getEmail() + "\",\n" +
                        "\t\"password\": \"" + testUser.getPassword() + "\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertTrue(userRepo.findOneByName(testUser.getName()).isPresent());
        assertFalse(userRepo.findOneByName("randomNameWhichDoesNotExist").isPresent());
    }


    @Test
    public void registerUser_andLogin() throws Exception {
        this.mockMvc.perform(post("/api/users")
                .content("{\n" +
                        "\t\"name\": \"" + testUser.getName() + "\",\n" +
                        "\t\"email\": \"" + testUser.getEmail() + "\",\n" +
                        "\t\"password\": \"" + testUser.getPassword() + "\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertTrue(userRepo.findOneByName(testUser.getName()).isPresent());

        this.mockMvc.perform(post("/api/login")
                .content("{\n" +
                        "\t\"username\": \"" + testUser.getName() + "\",\n" +
                        "\t\"password\": \"" + testUser.getPassword() + "\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void registerUser_andLoginWithWrongPassword() throws Exception {
        String wrongPassword = "wrong";
        this.mockMvc.perform(post("/api/users")
                .content("{\n" +
                        "\t\"name\": \"" + testUser.getName() + "\",\n" +
                        "\t\"email\": \"" + testUser.getEmail() + "\",\n" +
                        "\t\"password\": \"" + testUser.getPassword() + "\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertTrue(userRepo.findOneByName(testUser.getName()).isPresent());
        this.mockMvc.perform(post("/api/login")
                .content("{\n" +
                        "\t\"username\": \"" + testUser.getName() + "\",\n" +
                        "\t\"password\": \"" + wrongPassword + "\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());


    }


}