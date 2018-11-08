package com.qa.cv.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.cv.Model.CvModel;
import com.qa.cv.Model.UsersDataModel;

@Repository
public interface CvRepository extends JpaRepository<CvModel, Long> {

	Page<CvModel> findByUserId(UsersDataModel userId, Pageable pageable);

	Page<CvModel> findByStatus(String status, Pageable pageable);



}
