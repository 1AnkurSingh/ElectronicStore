package com.electronicstore.dtos;
import com.electronicstore.validate.ImageNameValid;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private String userId;
    @Size(min = 2, max = 20,message = "Invalid Name!!")
//    @ApiModelProperty(value = "user-name",name = "userName!",required = true,notes = "name of new user")
    private String name;
    @Email(message = "Invalid User Email !!")
    @NotBlank(message = "Email is required!!")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" ,message = "Invalid user Email")
    private String email;
    @NotBlank(message = "Password is required!!")
    private String password;
    @Size(min = 4, max = 6,message = "Invalid gender")
    private String gender;
    @NotBlank(message = "Write Something !!")
    private String about;
    @ImageNameValid
    private String imageName;
    private Set<RoleDto> roles = new HashSet<>();
}
