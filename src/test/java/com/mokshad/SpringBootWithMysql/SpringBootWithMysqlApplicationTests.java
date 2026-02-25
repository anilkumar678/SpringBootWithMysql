package com.mokshad.SpringBootWithMysql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mokshad.SpringBootWithMysql.entity.User;
import com.mokshad.SpringBootWithMysql.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootWithMysqlApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService; // provided by TestConfiguration

	@Autowired
	private ObjectMapper objectMapper;

	private User testUser;

	@BeforeEach
	void setUp() {
		testUser = new User();
		testUser.setId(1L);
		testUser.setFirstName("John Doe");
		testUser.setEmail("john@example.com");
	}

	@Test
	void testCreateUser_Success() throws Exception {
		when(userService.createUser(any(User.class))).thenReturn(testUser);

		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.firstName").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john@example.com"));

		verify(userService, times(1)).createUser(any(User.class));
	}

	@Test
	void testGetUserById_Success() throws Exception {
		when(userService.getUserById(1L)).thenReturn(testUser);

		mockMvc.perform(get("/api/users/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.firstName").value("John Doe"));

		verify(userService, times(1)).getUserById(1L);
	}

	@Test
	void testGetAllUsers_Success() throws Exception {
		User user2 = new User();
		user2.setId(2L);
		user2.setFirstName("Jane Doe");
		user2.setEmail("jane@example.com");

		List<User> userList = Arrays.asList(testUser, user2);
		when(userService.getAllUsers()).thenReturn(userList);

		mockMvc.perform(get("/api/users/allUsers")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1L))
				.andExpect(jsonPath("$[1].id").value(2L))
				.andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)));

		verify(userService, times(1)).getAllUsers();
	}

	@Test
	void testUpdateUser_Success() throws Exception {
		testUser.setFirstName("Updated Name");
		when(userService.updateUser(any(User.class))).thenReturn(testUser);

		mockMvc.perform(put("/api/users/updateUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Updated Name"));

		verify(userService, times(1)).updateUser(any(User.class));
	}

	@Test
	void testDeleteUserById_Success() throws Exception {
		doNothing().when(userService).deleteUser(1L);

		mockMvc.perform(delete("/api/users/deleteByUserId/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("User sucessfully deleted")));

		verify(userService, times(1)).deleteUser(1L);
	}

	@Test
	void testContextLoads() {
	}

	@TestConfiguration
	static class MockServiceConfig {
		@Bean
		public UserService userService() {
			return Mockito.mock(UserService.class);
		}
	}
}
