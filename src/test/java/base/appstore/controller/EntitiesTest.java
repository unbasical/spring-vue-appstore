package base.appstore.controller;

import base.appstore.model.App;
import base.appstore.model.Comment;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EntitiesTest {

    @Test
    public void getTestApp() throws Exception {
        App app1 = App.builder().title("A").description("A").build();
        App app2 = App.builder().title("B").description("B").build();
        App app3 = App.builder().title("A").description("A").build();

        assertEquals(app1, app1);
        assertEquals(app1, app3);
        assertNotEquals(app1, app2);
        assertEquals(app1.hashCode(), app3.hashCode());
        assertNotEquals(app1.hashCode(), app2.hashCode());
    }

    @Test
    public void getTestComment() throws Exception {
        Comment c1 = new Comment(1L, "A", null, null);
        Comment c2 = new Comment(2L, "B", null, null);
        Comment c3 = new Comment(1L, "A", null, null);

        assertEquals(c1, c1);
        assertEquals(c1, c3);
        assertNotEquals(c1, c2);
        assertEquals(c1.hashCode(), c3.hashCode());
        assertNotEquals(c1.hashCode(), c2.hashCode());
    }

}
