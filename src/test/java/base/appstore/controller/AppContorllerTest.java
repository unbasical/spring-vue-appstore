package base.appstore.controller;

import base.appstore.model.App;
import base.appstore.model.Role;
import base.appstore.model.User;
import base.appstore.repository.AppRepository;
import base.appstore.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class AppContorllerTest {

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
    public void getTestApp() throws Exception {
        this.mockMvc.perform(get("/api/apps/" + testApp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is(testApp.getTitle())))
        .andExpect(jsonPath("description",is(testApp.getDescription())));
    }

    @Test
    public void appNotFoundbecauseDeleted() throws Exception {
        App secondApp = appRepo.save(App.builder()
                .title("TestApp")
                .description("Fancy description")
                .build());
        long secondAppid = secondApp.getId();
        appRepo.delete(secondApp);
        this.mockMvc.perform(get("/api/apps/" + secondAppid)).andExpect(status().isNotFound());
    }

    @Test
    public void appNotFound() throws Exception {
        long wrongAppId = (testApp.getId() + 1);
        this.mockMvc.perform(get("/api/apps/" + wrongAppId)).andExpect(status().isNotFound());
    }


    @Test
    public void createComment() throws Exception {
        this.mockMvc.perform(post("/api/apps/"+testApp.getId()+"/comments").content("{\n" +
                "\t\"text\": \"TestComment\",\n" +
                "\t\"author\": {" +
                "\t\"name\": \""+testUser.getName()+"\",\n" +
                "\t\"email\": \""+testUser.getEmail()+"\",\n" +
                "\t}\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
/*
        this.mockMvc.perform(get("/api/apps/" + testApp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("comment", is("TestComponent")));
                */

    }

    @Test
    public void createRating() throws Exception {
        this.mockMvc.perform(post("/api/apps/"+testApp.getId()+"/ratings").content("{\n" +
                "\t\"stars\": 5,\n" +
                "\t\"author\": {" +
                "\t\"name\": \""+testUser.getName()+"\",\n" +
                "\t\"email\": \""+testUser.getEmail()+"\",\n" +
                "\t}\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/apps/" + testApp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("rating", is(5)));
    }

}
