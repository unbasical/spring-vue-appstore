package base.appstore.controller;

import base.appstore.controller.dto.TagDto;
import base.appstore.model.App;
import base.appstore.model.Role;
import base.appstore.model.Tag;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class AppControllerTest {

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
        Set<Tag> tags = new HashSet<>();
        TagDto firstTag = new TagDto();
        firstTag.setName("test");
        tags.add(firstTag.toEntity());
        TagDto secondTag = new TagDto();
        secondTag.setName("foo");
        tags.add(secondTag.toEntity());

        testUser = userRepo.save(User.builder()
                .name("Test User")
                .email("test.user@test.de")
                .role(Role.DEVELOPER)
                .build());

        testApp = appRepo.save(App.builder()
                .title("TestApp")
                .description("Fancy description")
                .tags(tags)
                .build());
    }


    @After
    public void cleanData() {
        appRepo.deleteAll();
        userRepo.deleteAll();
    }


    @Test
    public void getAllApps() throws Exception {
        this.mockMvc.perform(get("/api/apps/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getAllAppsFilteredByTitle() throws Exception {
        this.mockMvc.perform(get("/api/apps?search=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        this.mockMvc.perform(get("/api/apps?search=none"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getAllAppsFilteredByRating() throws Exception {

        this.mockMvc.perform(post("/api/apps/" + testApp.getId() + "/ratings").content("{\n" +
                "\t\"stars\": 3,\n" +
                "\t\"author\": {" +
                "\t\"id\": " + testUser.getId() + "}\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/apps?rating=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        this.mockMvc.perform(get("/api/apps?rating=4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllAppsFilteredByTag() throws Exception {
        this.mockMvc.perform(get("/api/apps?tag=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        this.mockMvc.perform(get("/api/apps?tag=none"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getTestApp() throws Exception {
        this.mockMvc.perform(get("/api/apps/" + testApp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is(testApp.getTitle())))
                .andExpect(jsonPath("description", is(testApp.getDescription())));
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
    @WithMockUser(roles = "USER")
    public void createComment() throws Exception {
        int expectedInt = Math.toIntExact(testUser.getId());
        this.mockMvc.perform(post("/api/apps/" + testApp.getId() + "/comments").content("{\n" +
                "\t\"text\": \"TestComment\",\n" +
                "\t\"author\": {" +
                "\t\"id\": " + testUser.getId() + "}\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/apps/" + testApp.getId() + "/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].text", is("TestComment")))
                .andExpect(jsonPath("$[0].author.id", is(expectedInt)))
                .andExpect(jsonPath("$[0].author.name", is(testUser.getName())))
                .andExpect(jsonPath("$[0].author.email", is(testUser.getEmail())));
    }


    @Test
    @WithMockUser(roles = "USER")
    public void createRating() throws Exception {
        int expectedInt = Math.toIntExact(testUser.getId());
        this.mockMvc.perform(post("/api/apps/" + testApp.getId() + "/ratings").content("{\n" +
                "\t\"stars\": 5,\n" +
                "\t\"author\": {" +
                "\t\"id\": " + testUser.getId() + "}\n" +
                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/apps/" + testApp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(5.0)));


        this.mockMvc.perform(get("/api/apps/" + testApp.getId() + "/ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

}
