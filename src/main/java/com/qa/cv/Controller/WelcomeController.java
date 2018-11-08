package com.qa.cv.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.cv.Exceptions.ResourceNotFoundException;
import com.qa.cv.Model.CvModel;

@CrossOrigin
@RestController
public class WelcomeController 
{
//	@GetMapping("/")
//	public String getMain ()
//	{
//		return "Welcome to QACvs!";
//	}
}
