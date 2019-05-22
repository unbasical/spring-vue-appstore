package base.appstore.controller;

import base.appstore.model.Tag;
import base.appstore.repository.TagRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepo;

    @Before
    public void initData() {
        tagRepo.save(new Tag(null, "Test"));
    }

    @After
    public void cleanData() {
        tagRepo.deleteAll();
    }

    @Test
    public void getAllTags() throws Exception {
        //testing thymeleaf sec:authorize with hasPermission
        this.mockMvc.perform(get("/api/tags").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
