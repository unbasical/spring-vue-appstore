package base.appstore.controller;

import base.appstore.controller.dto.AppDto;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DtoTest {

    @Test
    public void testApp() throws Exception {
        AppDto dto = new AppDto();
        dto.setId(1L);
        dto.setTitle("Test");
        dto.setRating(5);
        dto.setDescription("Test");
        dto.setScreenshots(new ArrayList<>());
        dto.setTags(new ArrayList<>());
        dto.setViews(10);

        assertEquals(1L, dto.getId().longValue());
        assertEquals("Test", dto.getTitle());
        assertEquals(5, dto.getRating(), 1);
    }

}
