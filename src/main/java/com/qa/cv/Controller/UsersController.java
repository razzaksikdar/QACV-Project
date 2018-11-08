package com.qa.cv.Controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.activation.*;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qa.cv.Exceptions.ResourceNotFoundException;
import com.qa.cv.Model.CvModel;
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Model.UsersDataModel;
import com.qa.cv.Repositories.DepartmentRepository;
import com.qa.cv.Repositories.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class UsersController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	// Method to Create Users
	@PostMapping("/department/{departmentId}/user")
	public UsersDataModel createuser(@PathVariable(value = "departmentId") Long departmentId,
			@Valid @RequestBody UsersDataModel usersDataModel) {
		return departmentRepository.findById(departmentId).map(departmentModel -> {
			usersDataModel.setDepartment(departmentModel);
			return userRepository.save(usersDataModel);
		}).orElseThrow(() -> new ResourceNotFoundException("Department", "id", usersDataModel));
	}

	// Method to update role
	@PutMapping("/department/{departmentId}/user/{userId}")
	public UsersDataModel updateUserDepartment(@PathVariable(value = "departmentId") Long departmentId, @PathVariable(value = "userId") Long userId) 
	{
		UsersDataModel userModel = userRepository.findById(userId).get();
		return departmentRepository.findById(departmentId).map(departmentModel -> {
			userModel.setDepartment(departmentModel);
			return  userRepository.save(userModel);
		}).orElseThrow(() -> new ResourceNotFoundException("Department", "id", userModel));
	}
	// Method to Post a Cv
		@PostMapping("/user/{userId}/picture")
		@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
		public UsersDataModel uploadPicture(@PathVariable(value = "userId") Long userId,
				@FormParam ("file") MultipartFile file){
			return userRepository.findById(userId).map(userModel -> {
				try {
					userModel.setPicture(new SerialBlob(file.getBytes()));
				} catch (IOException | SQLException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				return userRepository.save(userModel);
			}).orElseThrow(() -> new ResourceNotFoundException("User", "id", null));
		}
		// Method to Get a Cv
		@GetMapping("/user/{userId}/picture/download")
		public byte[] downloadCv(HttpServletResponse response, @PathVariable(value = "userId") Long userId) throws IOException, SQLException {
			Optional<UsersDataModel> user = userRepository.findById(userId);
			UsersDataModel userModel = user.get();
			return userModel.getPicture().getBytes(1l, (int) userModel.getPicture().length());
		}
		// Method to set status/flag of cv 
		@PutMapping("/user/{userId}/prefLocation/{location}")
		public UsersDataModel updateCv(@PathVariable(value = "userId") Long userId
				, @PathVariable(value = "location") String prefLocation){
			return userRepository.findById(userId).map(userModel -> {
					userModel.setPrefLocation(prefLocation);
					userRepository.save(userModel);
					return userRepository.save(userModel);
			}).orElseThrow(() -> new ResourceNotFoundException("User", "id", null));
		}
	
	// Method to get a user
	@GetMapping("/user/{userId}")
	public UsersDataModel getUserByUserId(@PathVariable(value = "userId") Long userId, Pageable pageable) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("UserModel", "id", userId));
	}
	// Method to get a user by name
	@GetMapping("findbyname/{name}&{lastName}")
	public List<UsersDataModel> getAllUsersByName(@PathVariable(value = "name") String name,
			@PathVariable(value = "lastName") String lastName, Pageable pageable) {

		List<UsersDataModel> user = userRepository.findAll().stream().filter((u) -> 
		{
			if(u.getDepartmentId() == 1 ||u.getDepartmentId() == 6)
			{
				return true;
			}
			return false;
		}).collect(Collectors.toList());;
		user = user.stream().filter(u -> {
			if (u.getFirstName().toLowerCase().startsWith(name.toLowerCase()) 
					&& u.getLastName().toLowerCase().startsWith(lastName.toLowerCase())) {
				return true;
			}
			if (u.getDepartment().getRole().toLowerCase().startsWith(name.toLowerCase()) 
					&& u.getDepartment().getRole().toLowerCase().startsWith(lastName.toLowerCase())) {
				return true;
			}
			
			if (lastName.equals("")) {
				if (u.getEmail().toLowerCase().startsWith(name.toLowerCase())) {
					return true;
				}
				if (u.getDepartment().getRole().toLowerCase().startsWith(name.toLowerCase())) {
					return true;
				}

				if (u.getFirstName().toLowerCase().startsWith(name.toLowerCase()) 
						|| u.getLastName().toLowerCase().startsWith(name.toLowerCase())) {
					return true;
				}
			}
			return false;
		}).collect(Collectors.toList());
		return user;
	}

	// Method to get user with email and password (Log in)
	@GetMapping("/login/{email}&{password}")
	public List<UsersDataModel> getAllUsersByEmail(@PathVariable(value = "email") String email,
			@PathVariable(value = "password") String password, Pageable pageable) {

		Page<UsersDataModel> user = userRepository.findByEmail(email, pageable);
		if (!user.getContent().get(0).getPassword().toString().equals(password)) {
			throw new ResourceNotFoundException(email, email, null);
		}
		return user.getContent();
	}

	// Method to Get all Users in a given department
	@GetMapping("/department/{departmentId}/user")
	public Page<UsersDataModel> getAllUsersByDepartmentId(
			@PathVariable(value = "departmentId") DepartmentModel departmentId, Pageable pageable) {
		return userRepository.findByDepartmentId(departmentId, pageable);
	}

	// Method to get all users
	@GetMapping("/user")
	public List<UsersDataModel> getAlluser() {
		return userRepository.findAll();
	}
	@GetMapping("/email/{email}")
	public List<UsersDataModel> findUserByEmail(@PathVariable(value = "email") String email) {
		return userRepository.findAll().stream().filter((user)->
		{
			if(user.getEmail().toLowerCase().equals(email.toLowerCase()))
				return true;
			else
				return false;
		}).collect(Collectors.toList());
	}

	// Method to Edit a user
	@PutMapping("/user/{userId}")
	public UsersDataModel updateUser(@PathVariable(value = "userId") Long userId, @Valid @RequestBody UsersDataModel userRequest) {
		return userRepository.findById(userId).map(user -> {
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());
			user.setEmail(userRequest.getEmail());
			user.setPassword(userRequest.getPassword());
			user.setPicture(userRequest.getPicture());
			user.setPrefLocation(userRequest.getPrefLocation());
			return userRepository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("User", "id", userRequest));
	}
	// Method to Edit a user
	@PutMapping("/user/{userId}/updatePassword/{pass}")
	public UsersDataModel updatePassword(@PathVariable(value = "userId") Long userId, @PathVariable(value = "pass") String newPass) {
		return userRepository.findById(userId).map(user -> {
			user.setPassword(newPass);
			return userRepository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("User", "id", null));
	}

	// Method to remove a user
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") Long userId) {
		return userRepository.findById(userId).map(user -> {
			userRepository.delete(user);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("UserId", userId.toString(), null));

	}

}
