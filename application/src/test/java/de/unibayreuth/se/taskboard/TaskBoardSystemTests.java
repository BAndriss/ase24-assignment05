package de.unibayreuth.se.taskboard;

import de.unibayreuth.se.taskboard.api.dtos.TaskDto;
import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.api.mapper.TaskDtoMapper;
import de.unibayreuth.se.taskboard.api.mapper.UserDtoMapper;
import de.unibayreuth.se.taskboard.business.domain.Task;
import de.unibayreuth.se.taskboard.business.domain.User;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;


public class TaskBoardSystemTests extends AbstractSystemTest {

    @Autowired
    private TaskDtoMapper taskDtoMapper;
    @Autowired
    private UserDtoMapper userDtoMapper;

    @Test
    void getAllCreatedTasks() {
        List<Task> createdTasks = TestFixtures.createTasks(taskService);

        List<Task> retrievedTasks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/tasks")
                .then()
                .statusCode(200)
                .body(".", hasSize(createdTasks.size()))
                .and()
                .extract().jsonPath().getList("$", TaskDto.class)
                .stream()
                .map(taskDtoMapper::toBusiness)
                .toList();

        assertThat(retrievedTasks)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt", "updatedAt") // prevent issues due to differing timestamps after conversions
                .containsExactlyInAnyOrderElementsOf(createdTasks);
    }

    @Test
    void createAndDeleteTask() {
        Task createdTask = taskService.create(
                TestFixtures.getTasks().getFirst()
        );

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .delete("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(400);

    }

    //For creating test I used ChatGPT and JetBrains AI Assistant
    @Test
    void getAllCreatedUser() {
        List<User> createdUsers = TestFixtures.createUsers(userService);

        List<User> retrievedUser = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body(".", hasSize(createdUsers.size()))
                .and()
                .extract().jsonPath().getList("$", UserDto.class)
                .stream()
                .map(userDtoMapper::toBusiness)
                .toList();

        assertThat(retrievedUser)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt") // prevent issues due to differing timestamps after conversions
                .containsExactlyInAnyOrderElementsOf(createdUsers);
    }

    @Test
    void getUserById(){
        User user = userService.create(TestFixtures.getUsers().getFirst());

        UserDto retrievedUserDto = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users/{id}", user.getId())
                .then()
                .statusCode(200)
                .extract().as(UserDto.class);
        User retrievedUser = userDtoMapper.toBusiness(retrievedUserDto);
        assertThat(retrievedUser.getId()).isEqualTo(user.getId());
        assertThat(retrievedUser.getName()).isEqualTo(user.getName());
    }

    @Test
    void createUser() {
        userService.clear();
        String name = "TestUser";
        UserDto requestBody = new UserDto(null, name, LocalDateTime.now());
        UserDto createdUserDto = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .statusCode(200)
                .extract().as(UserDto.class);

        assertThat(createdUserDto.getName()).isEqualTo(name);
        assertThat(createdUserDto.getId()).isNotNull();
    }
}