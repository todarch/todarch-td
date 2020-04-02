package com.todarch.td;

import com.todarch.td.application.todo.TodoCommandManager;
import com.todarch.td.application.todo.TodoDto;
import com.todarch.td.application.todo.TodoQueryManager;
import com.todarch.td.domain.todo.TodoStatus;
import com.todarch.td.helper.TestTodo;
import com.todarch.td.helper.TestUser;
import cz.jirutka.rsql.parser.RSQLParserException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

/**
 * Base test class for golang cli contracts.
 *
 * @author selimssevgi
 */
// @RunWith(SpringRunner.class)
@SpringBootTest(classes = TdApplication.class)
@AutoConfigureMockMvc
public abstract class BaseContractTestHttp {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TodoCommandManager todoCommandManager;

  @MockBean
  private TodoQueryManager todoQueryManager;

  /**
   * Populates test database with the test td item.
   */
  // @Before
  public void setup() {
    mockTodoCommandManager();
    mockTodoQueryManager();
    // RestAssuredMockMvc.mockMvc(mockMvc);
  }

  private void mockTodoQueryManager() {
    var todoDto = new TodoDto();
    todoDto.setId(TestTodo.ID.value());
    todoDto.setPriority(TestTodo.PRIORITY.value());
    todoDto.setCreatedAtEpoch(Instant.now().toEpochMilli());
    todoDto.setTitle(TestTodo.TITLE);
    todoDto.setDescription(TestTodo.DESC);
    todoDto.setStatus(TodoStatus.INITIAL.name());
    todoDto.setUserId(TestUser.ID);
    todoDto.setDoneDateEpoch(0L);
    todoDto.setTimeNeededInMin(45);

    Mockito.doReturn(Optional.of(todoDto))
        .when(todoQueryManager).getUserTodoById(63L, TestUser.ID);

    final long notExistentTodoId = 99L;
    Mockito.doReturn(Optional.empty())
        .when(todoQueryManager).getUserTodoById(notExistentTodoId, TestUser.ID);

    Mockito.doThrow(new RSQLParserException(new IllegalArgumentException("invalid rsql")))
        .when(todoQueryManager).searchByRsqlQuery("invalidrsql", TestUser.ID);

    Mockito.doReturn(Collections.emptyList())
        .when(todoQueryManager).searchByRsqlQuery("validrsql", TestUser.ID);

    Mockito.doReturn(List.of(todoDto, todoDto))
        .when(todoQueryManager).getAllTodosByUserId(TestUser.ID);
  }

  private void mockTodoCommandManager() {
    Mockito.doReturn(123L)
        .when(todoCommandManager).createTodo(any());

    Mockito.doNothing()
        .when(todoCommandManager).changeStatus(any());

    Mockito.doNothing()
        .when(todoCommandManager).updateTodoFully(any());
  }

  protected String getJwt() {
    return TestUser.PREFIXED_TOKEN;
  }
}
