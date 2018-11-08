package com.qa.cv.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Model.UsersDataModel;

@Repository
public interface UserRepository extends JpaRepository<UsersDataModel, Long> {

	Page<UsersDataModel> findByDepartmentId(DepartmentModel departmentId, Pageable pageable);

	Page<UsersDataModel> findByEmail(String email, Pageable pageable);

	Page<UsersDataModel> findByPassword(String password, Pageable pageable);

	Page<UsersDataModel> findByUserId(Long userId, Pageable pageable);

}
