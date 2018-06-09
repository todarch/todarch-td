package com.todarch.td.rest.todo;

import com.todarch.security.api.JwtUtil;
import com.todarch.td.rest.TestUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoControllerIntTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testUserContext() throws Exception {
    mockMvc.perform(get("/todo")
    .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isOk());
  }
}
