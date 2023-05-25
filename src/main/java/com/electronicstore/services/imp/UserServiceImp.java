package com.electronicstore.services.imp;

import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.dtos.UserDto;
import com.electronicstore.entity.Role;
import com.electronicstore.entity.User;
import com.electronicstore.exception.ResourceNotFoundException;
import com.electronicstore.helper.Helper;
import com.electronicstore.repository.RoleRepository;
import com.electronicstore.repository.UserRepository;
import com.electronicstore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Value("${user.profile.image.path}")
    private String imagePath;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${normal.role.id}")
    private String normalRoleId;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    ModelMapper mapper;
    Logger logger= LoggerFactory.getLogger(UserServiceImp.class);
    @Override
    public UserDto crateUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        User user=dtoToEntity(userDto);
        User user = mapper.map(userDto,User.class);


        Role role = roleRepository.findById(normalRoleId).get();
        user.getRoles().add(role);
        System.out.println("++++++++++"+role);
        User savedUser = userRepository.save(user);



        UserDto newDto=entityToDto(savedUser);
        return mapper.map(savedUser, UserDto.class);
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Id Not Found!!"));
        user.setName(userDto.getName());
//      user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        User upadtedUser = userRepository.save(user);

        return entityToDto(upadtedUser);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Id Not Found!!"));


        // delete user profile image

        String fullPath = imagePath + user.getImageName();

        try{

            Path path= Paths.get(fullPath);

            Files.delete(path);

        }catch (IOException ex){
            logger.info("user image not found in folder !!");
            ex.printStackTrace();
        }

        userRepository.delete(user);

    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Id Not Found!!"));

        return entityToDto(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize , String sortBy, String sortDir)
    {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))? (Sort.by(sortBy).descending()): (Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber-1,pageSize,sort);

        Page<User> page = userRepository.findAll(pageable);

        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);

        return pageableResponse;
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("Email Not Found Exception!!!!"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> getUserByKeyWord(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoUsers = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoUsers;
    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }


    private User dtoToEntity(UserDto userDto) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .imageName(userDto.getImageName())
                .gender(userDto.getGender()).build();

        return user;
    }
    private UserDto entityToDto(User savedUser) {
        UserDto build = UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .imageName(savedUser.getImageName())
                .gender(savedUser.getGender()).build();
        return build;
    }



}
