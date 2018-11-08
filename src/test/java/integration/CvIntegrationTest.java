/*package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.qa.cv.MyCvV1Application;

import com.qa.cv.Model.CvModel;
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Model.UsersDataModel;
import com.qa.cv.Repositories.CvRepository;
import com.qa.cv.Repositories.DepartmentRepository;
import com.qa.cv.Repositories.UserRepository;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MyCvV1Application.class })
@AutoConfigureMockMvc
public class CvIntegrationTest {
	
	static ExtentReports report = new ExtentReports("C:\\Users\\Admin\\Desktop\\ANOTHER ONE\\CV Integration Test Results.html");;
	ExtentTest test;
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Autowired
	private CvRepository cvRepo;

	@Before
	public void clearDB() {
		userRepo.deleteAll();
		departmentRepo.deleteAll();
		cvRepo.deleteAll();
	}
	
	@After
	public void tearDown() {
	report.endTest(test);
	report.flush();
	}

//	@Test
//	public void uploadACVTest() throws Exception {
//		test = report.startTest("Creating a CV in Database");
//		DepartmentModel department = new DepartmentModel("Big Boss");
//		departmentRepo.save(department);
//		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
//		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department);
//		userRepo.save(user);
//		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
//		
//		
//		File file = new File("C:\\Users\\Admin\\Downloads\\CV Project.pdf");
//		
//		mvc.perform(MockMvcRequestBuilders.post("/api/user/" + user.getUserId() + "/upload")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content("{\"cvLink\":" + null + ", \"status\" : \"Gray\", \"fileType\" : \"PDF\", \"fileName\": \"Amazing Cv\""))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
//		//.andExpect(jsonPath("$.fileName", is("Amazing Cv")));
//		
//		if(mvc.perform(get("/api/user/" + user.getUserId() +"/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Test Cv") == true ){
//			test.log(LogStatus.PASS, "CV creation was successful");
//		} else {
//			test.log(LogStatus.FAIL, "CV creation was unsuccessful");
//		}
//		}

	@Test
	public void getASpecificCvInfoTest() throws Exception {
		test = report.startTest("Finding a CV in Database");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		test.log(LogStatus.INFO, "Created a CV With the Name 'Test Cv' for Jon");
		
		String id = mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String cvId = (id.substring(index1, index2));
		
		
		mvc.perform(get("/api/cv/" + cvId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.fileName", is("Test Cv"))).andExpect(status().isOk());
		
		if(mvc.perform(get("/api/cv/" + cvId).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Test Cv") == true){
			test.log(LogStatus.PASS, "CV has been found");
		} else {
			test.log(LogStatus.FAIL, "CV was not found");
		}
		
		
	}
	
	@Test
	public void getAllCvInfoForAUser() throws Exception {
		test = report.startTest("Finding all CVs for a User");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Amazing Cv"));
		test.log(LogStatus.INFO, "Created a CV With the Name 'Test Cv' for Jon");
		test.log(LogStatus.INFO, "Created a CV With the Name 'Amazing Cv' for Jon");
		
		String id = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String userId = (id.substring(index1, index2));
		
		mvc.perform(get("/api/user/" + userId +"/cv").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.[0]fileName", is("Test Cv"))).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.[1]fileName", is("Amazing Cv"))).andExpect(status().isOk());
		
		if(mvc.perform(get("/api/user/" + userId +"/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Test Cv") == true &&
				mvc.perform(get("/api/user/" + userId +"/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Amazing Cv") == true){
			test.log(LogStatus.PASS, "Both CVs for the user have been found");
		} else {
			test.log(LogStatus.FAIL, "Atleast one CV was not found");
		}
		
	}
	
	@Test
	public void getAllCvsInfo() throws Exception {
		test = report.startTest("Finding All CVs in Database");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		UsersDataModel user2 = new UsersDataModel("Will", "Smith", "ws@gmail.com", "password", department); 
		userRepo.save(user);
		userRepo.save(user2);
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		test.log(LogStatus.INFO, "Created a User With the Name 'Will'");
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		cvRepo.save(new CvModel(user2, null, "Gray", "PDF", "Amazing Cv"));
		test.log(LogStatus.INFO, "Created a CV With the Name 'Test Cv' for Jon");
		test.log(LogStatus.INFO, "Created a CV With the Name 'Amazing Cv' for Will");
		
		mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].fileName", is("Test Cv"))).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[1].fileName", is("Amazing Cv"))).andExpect(status().isOk());
		
		if(mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Test Cv") == true &&
				mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Amazing Cv") == true	){
			test.log(LogStatus.PASS, "All CVs have been found successfully");
		} else {
			test.log(LogStatus.FAIL, "Atleast one CV as not found");
		}
	}
	
	public void downloadCV() {
		
	}
	
	@Test
	public void deleteACv() throws Exception {
		test = report.startTest("Deleting a CV from the Database");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		test.log(LogStatus.INFO, "Created a CV With the Name 'Test Cv' for Jon");
		
		String id = mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String cvId = (id.substring(index1, index2));
		
		mvc.perform(delete("/api/cv/" + cvId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		
		if(mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Test Cv") == false){
			test.log(LogStatus.PASS, "CV deletion successful");
		} else {
			test.log(LogStatus.FAIL, "CV deletion unsuccessful");
		}
		
	}
	@Test
	public void findCvsByStatus() throws Exception {
		test = report.startTest("Finding CVs by status");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		cvRepo.save(new CvModel(user, null, "Green", "PDF", "Amazing Cv"));
		test.log(LogStatus.INFO, "Created a CV With the Name 'Test Cv' with 'Gray' status");
		test.log(LogStatus.INFO, "Created a CV With the Name 'Amazing Cv' with 'Green' status");
		
		mvc.perform(get("/api/cv/status/Gray").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.[0]status", is("Gray"))).andExpect(status().isOk());
		
		if(mvc.perform(get("/api/cv/status/Gray").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Test Cv") == true &&
				mvc.perform(get("/api/cv/status/Gray").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Amazing Cv") == false){
			test.log(LogStatus.PASS, "Successfully Filtered Cvs by status");
		} else {
			test.log(LogStatus.FAIL, "Filtering by status unsuccessful");
		}
		
	}
	
	@Test
	public void changeCvStatus() throws Exception {
		test = report.startTest("Finding CVs by status");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		test.log(LogStatus.INFO, "Created a CV With the Name 'Test Cv' with 'Gray' status");
		
		
		String id = mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String cvId = (id.substring(index1, index2));
		
		test.log(LogStatus.INFO, "Attempt to change the status of CV to 'Green'");
		mvc.perform(put("/api/cv/" + cvId + "/status/Green").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status", is("Green")));
		
		if(mvc.perform(put("/api/cv/" + cvId + "/status/Green").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Test Cv") == true){
			test.log(LogStatus.PASS, "Successfully changed the Cvs status");
		} else {
			test.log(LogStatus.FAIL, "Status change unsuccessful");
		}
		
		}
	
}
*/