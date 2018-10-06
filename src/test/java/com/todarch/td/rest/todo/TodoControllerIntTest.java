package com.todarch.td.rest.todo;

import com.todarch.security.api.JwtUtil;
import com.todarch.td.Endpoints;
import com.todarch.td.domain.todo.model.TodoEntity;
import com.todarch.td.domain.todo.model.TodoStatus;
import com.todarch.td.domain.todo.repository.TodoRepository;
import com.todarch.td.helper.BaseIntTest;
import com.todarch.td.helper.Requests;
import com.todarch.td.helper.TestUtil;
import com.todarch.td.rest.TestUser;
import com.todarch.td.rest.todo.model.NewTodoReq;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoControllerIntTest extends BaseIntTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TodoRepository todoRepository;

  @Test
  public void testUserContext() throws Exception {
    mockMvc.perform(get("/todo")
    .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isOk());
  }

  @Test
  public void createTodoInInitialState() throws Exception {
    NewTodoReq newTodoReq = Requests.newTodoReq();

    mockMvc.perform(post(Endpoints.TODOS)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN)
        .content(TestUtil.toJsonBytes(newTodoReq)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists());

    List<TodoEntity> allByUserId = todoRepository.findAllByUserId(TestUser.ID);
    Assertions.assertThat(allByUserId).isNotEmpty();
    TodoEntity createdTodo = allByUserId.get(0);
    Assertions.assertThat(createdTodo.status()).isEqualTo(TodoStatus.INITIAL);
  }

  @Test
  public void getTodo() throws Exception {
    TodoEntity testTodo = dbHelper.createTestTodo();

    mockMvc.perform(get("/api/todos/" + testTodo.id())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.title").exists())
        .andExpect(jsonPath("$.description").exists())
        .andExpect(jsonPath("$.priority").exists())
        .andExpect(jsonPath("$.status").exists());
  }

  @Test
  public void getCurrentUserTodos() throws Exception {
    TodoEntity testTodo = dbHelper.createTestTodo();

    String expectedJson = TestUtil.toJsonString(List.of(testTodo));

    mockMvc.perform(get(Endpoints.TODOS)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedJson));
  }
}
