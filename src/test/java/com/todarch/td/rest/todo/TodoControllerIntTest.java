package com.todarch.td.rest.todo;

import com.todarch.security.api.JwtUtil;
import com.todarch.td.Endpoints;
import com.todarch.td.helper.BaseIntTest;
import com.todarch.td.helper.TestUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoControllerIntTest extends BaseIntTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldGetTodoDetails() throws Exception {
    dbHelper.createTestTodo();

    mockMvc.perform(get(Endpoints.TODOS)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$[0].tags").isNotEmpty());
  }
}
