package com.electronicstore.service;
import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.dtos.UserDto;
import com.electronicstore.entity.Role;
import com.electronicstore.entity.User;
import com.electronicstore.repository.RoleRepository;
import com.electronicstore.repository.UserRepository;
import com.electronicstore.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper mapper;

    User user;

    Role role;


    String rollId;


    @Autowired
    private UserService userService;

    @BeforeEach
    public void init() {

        role = Role.builder()
                .roleId("abc")
                .roleName("NORMAL").build();

        user = User.builder()
                .name("Ankur singh")
                .email("ankur@123")
                .about("testing create method ")
                .gender("male")
                .imageName("abc.png")
                .password("abcd")
                .roles(Set.of(role))
                .build();
        rollId = "abc";

    }


    @Test
    public void createUser() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(role));

        UserDto user1 = userService.crateUser(mapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Ankur singh", user1.getName());

    }


    //update user test
    @Test
    public void updateUserTest() {
        String userId = "hosdhfosdhvo";
        UserDto userDto = UserDto.builder()
                .name("Durgesh Kumar Tiwari")
                .about("This is updated user about details")
                .gender("Male")
                .imageName("xyz.png")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto, userId);
//        UserDto updatedUser=mapper.map(user,UserDto.class);
        System.out.println(updatedUser.getName());
        System.out.println(updatedUser.getImageName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(), updatedUser.getName(), "Name is not validated !!");
        //multiple assertion are valid..


    }

    //delete user test case
    //delete user test case

    @Test
    public void deleteUserTest() {
        String userid = "userIdabc";
        Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));
        userService.deleteUser(userid);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);


    }

    @Test
    public void getAllUsersTest() {
        User user1 = User.builder()
                .name("Ankit")
                .email("durgesh@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                .roles(Set.of(role))
                .build();
        User user2 = User.builder()
                .name("Uttam")
                .email("durgesh@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                .roles(Set.of(role))
                .build();
        List<User> userList = Arrays.asList(user, user1, user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
        Assertions.assertEquals(3, allUser.getContent().size());


    }


    @Test
    public void getUserByIdTest() {

        String userId = "userIdTest";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //actual call of service method

        UserDto userDto = userService.getUserById(userId);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(), userDto.getName(), "Name not matched !!");


    }

    @Test
    public void getUserByEmailTest() {

        String emailId = "durgesh@gmail.com";
        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserByEmail(emailId);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmail(), userDto.getEmail(), "Email not matched !!");


    }

    @Test
    public void searchUserTest() {

        User user1 = User.builder()
                .name("Ankit Kumar")
                .email("durgesh@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                .roles(Set.of(role))
                .build();

        User user2 = User.builder()
                .name("Uttam Kumar")
                .email("durgesh@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                .roles(Set.of(role))
                .build();

        User user3 = User.builder()
                .name("Pankaj Kumar")
                .email("durgesh@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("abc.png")
                .password("lcwd")
                .roles(Set.of(role))
                .build();

        String keywords = "Kumar";
        Mockito.when(userRepository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user, user1, user2, user3));
        List<UserDto> userDtos = userService.getUserByKeyWord(keywords);
        Assertions.assertEquals(4, userDtos.size(), "size not matched !!");
    }

    @Test
    public void findUserByEmailOptionalTest() {

        String email = "durgeshkumar@gmail.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Optional<User> userByEmailOptional = userService.findUserByEmailOptional(email);
        Assertions.assertTrue(userByEmailOptional.isPresent());
        User user1 = userByEmailOptional.get();
        Assertions.assertEquals(user.getEmail(), user1.getEmail(), "email does not matched ");


    }
}
