package ru.elena.testwork.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAPage() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void testFindPage() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/find?data=data")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void testFormsPage() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/forms")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void testStepsPage() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/steps")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

    @Test
    public void testTopPage() throws Exception {
        MediaType HTML_UTF_8 = new MediaType("text", "html", java.nio.charset.Charset.forName("UTF-8"));
        this.mvc.perform(get("/top")).andExpect(status().isOk())
                .andExpect(content().contentType(HTML_UTF_8));
    }

}
