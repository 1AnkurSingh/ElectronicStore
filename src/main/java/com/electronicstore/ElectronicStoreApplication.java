package com.electronicstore;

import com.electronicstore.entity.Role;
import com.electronicstore.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Value("${normal.role.id}")
	private String role_normal_id;

	@Value("${admin.role.id}")
	private String role_admin_id;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("456")); // temporary only for testing purpose.

		try{

			Role roll_Admin = Role.builder().
					roleId(role_admin_id).
					roleName("ROll_Admin").build();
			Role roll_normal = Role.builder().
					roleId(role_normal_id).
					roleName("ROll_Normal").
					build();
			roleRepository.save(roll_Admin);
			roleRepository.save(roll_normal);

		}catch (Exception e){
			e.printStackTrace();
		}

	}
}

