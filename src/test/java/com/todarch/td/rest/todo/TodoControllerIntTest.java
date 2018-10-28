package com.todarch.td.rest.todo;

import com.todarch.security.api.JwtUtil;
import com.todarch.td.Endpoints;
import com.todarch.td.domain.todo.TodoEntity;
import com.todarch.td.helper.BaseIntTest;
import com.todarch.td.helper.Requests;
import com.todarch.td.helper.TestUser;
import com.todarch.td.helper.TestUtil;
import com.todarch.td.rest.todo.model.TodoFullUpdateReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoControllerIntTest extends BaseIntTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void createTodoInInitialState() throws Exception {
    var newTodoReq = Requests.newTodoReq();

    mockMvc.perform(post(Endpoints.TODOS)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN)
        .content(TestUtil.toJsonBytes(newTodoReq)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void getTodo() throws Exception {
    TodoEntity testTodo = dbHelper.createTestTodo();

    mockMvc.perform(get("/api/todos/" + testTodo.id().value())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.title").exists())
        .andExpect(jsonPath("$.description").exists())
        .andExpect(jsonPath("$.priority").exists())
        .andExpect(jsonPath("$.status").exists())
        .andExpect(jsonPath("$.timeNeededInMin").exists())
        .andExpect(jsonPath("$.createdAtEpoch").isNumber())
        .andExpect(jsonPath("$.doneDateEpoch").exists());
  }

  @Test
  public void returnNotFoundWhenCannotGetTodo() throws Exception {
    long nonexistentTodoId = Long.MAX_VALUE;

    mockMvc.perform(get("/api/todos/" + nonexistentTodoId)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getCurrentUserTodos() throws Exception {
    TodoEntity testTodo = dbHelper.createTestTodo();

    mockMvc.perform(get(Endpoints.TODOS)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isNotEmpty());
  }

  @Test
  public void changeTodoItemStatus() throws Exception {
    TodoEntity testTodo = dbHelper.createTestTodo();

    mockMvc.perform(put("/api/todos/" + testTodo.id().value() + "/done")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isNoContent());
  }

  @Test
  public void shouldDeleteId() throws Exception {
    TodoEntity testTodo = dbHelper.createTestTodo();

    mockMvc.perform(delete("/api/todos/" + testTodo.id().value())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isNoContent());
  }

  @Test
  public void shouldHandleRsqlQueries() throws Exception {
    dbHelper.createTestTodo();
    String validQuery = "todoStatus==INITIAL;priority<10";
    String queryString = "?q=" + validQuery;

    mockMvc.perform(get("/api/todos/" + queryString)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty());
  }

  @Test
  public void shouldHandleInvalidRsqlQueries() throws Exception {
    dbHelper.createTestTodo();
    String invalidQuery = "somerandomXX???String542342";
    String queryString = "?q=" + invalidQuery;

    mockMvc.perform(get("/api/todos/" + queryString)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldHandleFullUpdateOfTdResource() throws Exception {
    final var testTodo = dbHelper.createTestTodo();

    var todoFullUpdateReq = new TodoFullUpdateReq();
    todoFullUpdateReq.setTitle("NewTitle");
    todoFullUpdateReq.setDescription("NewDescription");
    todoFullUpdateReq.setTimeNeededInMin(30);
    todoFullUpdateReq.setPriority(3);
    todoFullUpdateReq.setTags(List.of("newTag1", "newTag2"));

    mockMvc.perform(put("/api/todos/" + testTodo.id().value())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .header(JwtUtil.AUTH_HEADER, TestUser.PREFIXED_TOKEN)
        .content(TestUtil.toJsonBytes(todoFullUpdateReq)))
        .andExpect(status().isNoContent());
  }

}
