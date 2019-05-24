package base.appstore.controller;

import base.appstore.controller.dto.UploadFileResponse;
import base.appstore.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EntitiesTest {

    @Test
    public void testApp() throws Exception {
        App a1 = App.builder().title("A").description("A").build();
        App a2 = App.builder().title("B").description("B").build();
        App a3 = App.builder().title("A").description("A").build();

        assertEquals(a1, a1);
        assertEquals(a1, a3);
        assertNotEquals(a1, a2);
        assertEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testComment() throws Exception {
        Comment a1 = new Comment(1L, "A", null, null);
        Comment a2 = new Comment(2L, "B", null, null);
        Comment a3 = new Comment(1L, "A", null, null);

        assertEquals(a1, a1);
        assertEquals(a1, a3);
        assertNotEquals(a1, a2);
        assertEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testLogo() throws Exception {
        Logo a1 = Logo.builder().id(1L).filename("Test").contentType("application/json").build();
        Logo a2 = Logo.builder().id(2L).filename("Test1").contentType("application/json").build();
        Logo a3 = Logo.builder().id(1L).filename("Test").contentType("application/json").build();

        assertEquals(a1, a1);
        assertEquals(a1, a3);
        assertNotEquals(a1, a2);
        assertEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testRating() throws Exception {
        Rating a1 = new Rating(1L, 5, null, null);
        Rating a2 = new Rating(2L, 2, null, null);
        Rating a3 = new Rating(1L, 5, null, null);

        assertEquals(a1, a1);
        assertEquals(a1, a3);
        assertNotEquals(a1, a2);
        assertEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testScreenshot() throws Exception {
        Screenshot a1 = Screenshot.builder().id(1L).filename("Test").contentType("application/json").build();
        Screenshot a2 = Screenshot.builder().id(2L).filename("Test1").contentType("application/json").build();
        Screenshot a3 = Screenshot.builder().id(1L).filename("Test").contentType("application/json").build();

        assertEquals(a1, a1);
        assertEquals(a1, a3);
        assertNotEquals(a1, a2);
        assertEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testTag() throws Exception {
        Tag a1 = new Tag(1L, "Test");
        Tag a2 = new Tag(2L, "Test1");
        Tag a3 = new Tag(1L, "Test");

        assertEquals(a1, a1);
        assertEquals(a1, a3);
        assertNotEquals(a1, a2);
        assertEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testUser() throws Exception {
        User a1 = User.builder().name("UserA").email("a@test.de").build();
        User a2 = User.builder().name("UserB").email("b@test.de").build();
        User a3 = User.builder().name("UserA").email("a@test.de").build();

        assertEquals(a1, a1);
        assertEquals(a1, a3);
        assertNotEquals(a1, a2);
        assertEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a2.hashCode());
    }


    @Test
    public void testUploadResponse() throws Exception{
        UploadFileResponse ufr1 = UploadFileResponse.builder().fileName("test").fileType("png").size(128).build();
        UploadFileResponse ufr2 = UploadFileResponse.builder().fileName("test2").fileType("png").size(128).build();
        UploadFileResponse ufr3 = UploadFileResponse.builder().fileName("test").fileType("png").size(128).build();

        assertEquals(ufr1,ufr1);
        assertNotEquals(ufr1,ufr2);
        assertEquals(ufr1.hashCode(),ufr3.hashCode());
        assertNotEquals(ufr1.hashCode(),ufr2.hashCode());

        assertEquals(ufr1.getFileName(),"test" );
        assertEquals(ufr1.getFileType(),"png");
        assertEquals(ufr1.getSize(),128L);



    }



}
