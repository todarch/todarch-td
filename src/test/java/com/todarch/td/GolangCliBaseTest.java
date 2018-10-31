package com.todarch.td;

import com.todarch.td.application.todo.TodoManagerMapper;
import com.todarch.td.domain.todo.TodoFactory;
import com.todarch.td.domain.todo.TodoRepository;
import com.todarch.td.helper.Requests;
import com.todarch.td.helper.TestTodo;
import com.todarch.td.helper.TestUser;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Base test class for golang cli contracts.
 *
 * @author selimssevgi
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class GolangCliBaseTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private TodoManagerMapper mapper;

  /**
   * Populates test database with the test td item.
   */
  @Before
  public void setup() {
    var newTodoReq = Requests.newTodoReq();
    var todoCreationCommand = mapper.toNewTodoCommand(newTodoReq, TestUser.ID);
    var savedTodo = TodoFactory.from(todoCreationCommand, TestTodo.ID);
    todoRepository.save(savedTodo);
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  String getJwt() {
    return TestUser.PREFIXED_TOKEN;
  }
}
