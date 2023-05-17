package com.electronicstore.Controller;
import com.electronicstore.dtos.ApiResponseMessage;
import com.electronicstore.dtos.ImageResponse;
import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.dtos.UserDto;
import com.electronicstore.services.FileService;
import com.electronicstore.services.imp.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    FileService fileService;

    Logger  logger= LoggerFactory.getLogger(UserController.class);

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @PostMapping("/create")
    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUser = userServiceImp.crateUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/update/{UserId}")
    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("UserId")String userId){
        UserDto userDto1 = userServiceImp.updateUser(userDto, userId);
        return new  ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId){
        userServiceImp.deleteUser(userId);

        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("user Deleted Successfully!!")
                .success(true)
                .status(HttpStatus.OK).build();

        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<UserDto>getUserById(@PathVariable("userId")String userId)
    {
        return new ResponseEntity<>(userServiceImp.getUserById(userId),HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public  ResponseEntity<PageableResponse<UserDto>>getAllUser(@RequestParam (value = "pageNumber",defaultValue = "0" , required = false) int pageNumber,
                                                       @RequestParam(value = "pageSize" ,defaultValue = "10",required = false)int pageSize,
                                                       @RequestParam(value = "sortBy" ,defaultValue = "name",required = false)String sortBy,
                                                       @RequestParam(value = "sortDir" ,defaultValue = "asc",required = false)String sortDir
                                                    )
    {
        return new ResponseEntity<>(userServiceImp.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/getByEmail/{userEmail}")
    public ResponseEntity<UserDto>getUserByEmail(@PathVariable("userEmail") String userEmail){
        return new ResponseEntity<>(userServiceImp.getUserByEmail(userEmail),HttpStatus.OK);
    }
    @GetMapping("/getBykeyword/{userKeyword}")
    public ResponseEntity<List<UserDto>>getUserByKeyword(@PathVariable("userKeyword") String userKeyWord){
        return new ResponseEntity<>(userServiceImp.getUserByKeyWord(userKeyWord),HttpStatus.OK);
    }

    // upload user image
    @PostMapping("/uploadImage/{userId}")
    public ResponseEntity<ImageResponse>uploadUserImage(@PathVariable("userId") String userId,@RequestParam("userImage")MultipartFile image) throws IOException {
        String imageName = fileService.uploadImage(image, imageUploadPath);

        UserDto user = userServiceImp.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userServiceImp.updateUser(user, userId);

        ImageResponse imageResponse= ImageResponse.builder().imageName(imageName).message("image upload successfully").success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }




    // serve user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userServiceImp.getUserById(userId);
        logger.info("user image name: {}",user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());

    }

}
