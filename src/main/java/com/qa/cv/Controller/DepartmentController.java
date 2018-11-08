package com.qa.cv.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.qa.cv.Exceptions.ResourceNotFoundException;
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Repositories.DepartmentRepository;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class DepartmentController {

	@Autowired
	DepartmentRepository departmentRepository;

	// Method to Post to Departments
	@PostMapping("/department")
	public DepartmentModel createdepartment(@Valid @RequestBody DepartmentModel mSDM) {
		return departmentRepository.save(mSDM);
	}

	// Method to Get a Department
	@GetMapping("/department/{id}")
	public DepartmentModel getDepartmentbyID(@PathVariable(value = "id") Long departmentID) {
		return departmentRepository.findById(departmentID)
				.orElseThrow(() -> new ResourceNotFoundException("DepartmentModel", "id", departmentID));
	}

	// Method to Get all Departments
	@GetMapping("/department")
	public List<DepartmentModel> getAllDepartment() {
		return departmentRepository.findAll();
	}

	// Method to Edit a Department
	@PutMapping("/department/{id}")
	public DepartmentModel updateDepartment(@PathVariable(value = "id") Long departmentID,
			@Valid @RequestBody DepartmentModel departmentDetails) {

		DepartmentModel mSDM = departmentRepository.findById(departmentID)
				.orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentID));

		mSDM.setRole(departmentDetails.getRole());

		DepartmentModel updateData = departmentRepository.save(mSDM);
		return updateData;
	}

	// Method to remove a department
	@DeleteMapping("department/{id}")
	public ResponseEntity<?> deleteDepartment(@PathVariable(value = "id") Long departmentID) {
		DepartmentModel mSDM = departmentRepository.findById(departmentID)
				.orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentID));

		departmentRepository.delete(mSDM);
		return ResponseEntity.ok().build();

	}

}
