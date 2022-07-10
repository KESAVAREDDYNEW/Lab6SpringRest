package com.gl.studentsapi.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gl.studentsapi.model.Role;
import com.gl.studentsapi.model.Student;
import com.gl.studentsapi.model.User;
import com.gl.studentsapi.repository.RoleRepository;
import com.gl.studentsapi.repository.StudentRepository;
import com.gl.studentsapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootStrapAppData implements ApplicationListener<ApplicationReadyEvent>{
	private final PasswordEncoder passwordEncoder;
	private final StudentRepository studentRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// implement faker here
		for(int i=0; i<5; i++) {
			Student student = new Student();
			student.setFirstName("kiran" + i);
			student.setLastName("kumar" + i);
			student.setCountry("Eng" + i);
			student.setCourse("Telcom" + i);
			studentRepository.save(student);
		}
		
		
		Role userRole=new Role();
		userRole.setRoleName("ROLE_USER");
		
		Role adminRole=new Role();
		adminRole.setRoleName("ROLE_ADMIN");
		
		
		User user=new User();
		user.setUserName("ravi");
		user.setPassword(this.passwordEncoder.encode("test"));
		
		User admin=new User();
		admin.setUserName("raja");
		admin.setPassword(this.passwordEncoder.encode("admin"));
				
		admin.addRole(adminRole);
		admin.addRole(userRole);
		
		user.addRole(userRole);
		
		userRepository.save(user);
		userRepository.save(admin);
		roleRepository.save(userRole);
		roleRepository.save(adminRole);
		
	}

}
