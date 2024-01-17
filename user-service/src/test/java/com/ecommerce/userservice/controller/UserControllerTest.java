package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create User - Should Return Created Status")
    public void createUser_ShouldReturnCreatedStatus() throws Exception {
        UserDto userDto = getMockUserDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Get All Users - Should Return List of Users")
    public void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        List<UserDto> users = Arrays.asList(getMockUserDto(), getMockUserDto());
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].userId", containsInAnyOrder(1, 1)))
                .andExpect(jsonPath("$[*].username", containsInAnyOrder("testUsername", "testUsername")));
    }

    @Test
    @DisplayName("Get User By Id - Should Return User")
    public void getUserById_ShouldReturnUser() throws Exception {
        UserDto user = getMockUserDto();
        when(userService.getUserById(anyLong())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.username").value("testUsername"));
    }

    @Test
    @DisplayName("Update User - Should Return OK Status")
    public void updateUser_ShouldReturnOKStatus() throws Exception {
        UserDto userDto = getMockUserDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete User - Should Return No Content")
    public void deleteUser_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    private UserDto getMockUserDto() {
        return UserDto.builder()
                .userId(1L)
                .firstName("testFirstName")
                .lastName("testLastName")
                .username("testUsername")
                .imageUrl("https://testimageurl.com")
                .email("test@email.com")
                .phone("0123456789")
                .addressDtos(Set.of())
                .build();
    }
}