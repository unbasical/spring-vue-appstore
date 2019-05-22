package base.appstore.controller;

import base.appstore.model.App;
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
    }

}
