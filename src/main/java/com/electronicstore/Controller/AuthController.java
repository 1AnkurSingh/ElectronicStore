package com.electronicstore.Controller;

import com.electronicstore.dtos.JwtRequest;
import com.electronicstore.dtos.JwtResponse;
import com.electronicstore.dtos.UserDto;
import com.electronicstore.entity.User;
import com.electronicstore.exception.BadApiRequest;
import com.electronicstore.services.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import security.JwtHelper;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Api(value = "AuthController",description = "Api for auth controller!!")
//@CrossOrigin("*")
//@CrossOrigin(
//        origins = "http://localhost:4200",
//        allowedHeaders = {"Authorization"},
//        methods = {RequestMethod.GET,RequestMethod.POST},
//        maxAge = 3600
//)
//we can use same configuration in Security configuration
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper helper;

    @Value("${googleClintId}")
    private String googleClintId;

    @Value("${newPassword}")
    private String newPassword;


    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .user(userDto).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadApiRequest(" Invalid Username or Password  !!");
        }

    }


    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        String name = principal.getName();
        return new ResponseEntity<>(modelMapper.map(userDetailsService.loadUserByUsername(name), UserDto.class), HttpStatus.OK);
    }

    @PostMapping("/google")
    public ResponseEntity<JwtResponse>loginWithGoogle(@RequestBody Map<String,Object> data) throws IOException {

        // get the id token from request
             String idToken=data.get("idToken").toString();

        NetHttpTransport netHttpTransport = new NetHttpTransport();

        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClintId));

        GoogleIdToken googleIdToken=GoogleIdToken.parse(verifier.getJsonFactory(),idToken);

        GoogleIdToken.Payload payload=googleIdToken.getPayload();
        logger.info("Payload:{}",payload);
        String email=payload.getEmail();

        User user=null;
         user = userService.findUserByEmailOptional(email).orElse(null);
         if(user==null){
             // create a new user
             user=this.saveUser(email,data.get("name").toString(),data.get("photoUrl").toString());
         }

        ResponseEntity<JwtResponse> JwtResponseEntity = this.login(JwtRequest.builder().email(user.getEmail()).password(newPassword).build());
         return JwtResponseEntity;
    }

    private User saveUser(String email, String name, String photoUrl) {
        UserDto newUser = UserDto.builder()
                .name(name)
                .email(email)
                .password(newPassword)
                .imageName(photoUrl)
                .roles(new HashSet<>()).build();
        UserDto user = userService.crateUser(newUser);
        return this.modelMapper.map(user, User.class);
    }


}