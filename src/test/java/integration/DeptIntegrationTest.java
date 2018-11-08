//package integration;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.qa.cv.MyCvV1Application;
//import com.qa.cv.Model.DepartmentModel;
//import com.qa.cv.Repositories.DepartmentRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {MyCvV1Application.class})
//@AutoConfigureMockMvc
//public class DeptIntegrationTest {
//
//	@Autowired
//	private MockMvc mvc;
//	
//	@Autowired
//	private DepartmentRepository departmentRepo;
//	
//	@Before
//	public void clearDB() {
//		departmentRepo.deleteAll();
//	}
//	
//	@Test
//	public void findingADepartmentFromDatabase() throws Exception 
//	{
//		departmentRepo.save(new DepartmentModel("Big Boss"));
//		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
//		int index1 = id.indexOf(":") + 1;
//		int index2 = id.indexOf(",");
//		String deptId = id.substring(index1, index2);
//		mvc.perform(get("/api/department/"+deptId).contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk()).andExpect(content()
//				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.role", is("Big Boss")));
//	}
//	
//	@Test
//	public void addDepartmentToDatabase() throws Exception{
//		mvc.perform(MockMvcRequestBuilders.post("/api/department")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content("{\"role\" : \"Big Boss\"}"))
//		.andExpect(status()
//				.isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.role", is("Big Boss")));
//	}
//	
//	@Test
//	public void editADepartmentInTheDatabase() throws Exception{
//		departmentRepo.save(new DepartmentModel("Big Boss"));
//		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
//		int index1 = id.indexOf(":") + 1;
//		int index2 = id.indexOf(",");
//		String deptId = id.substring(index1, index2);
//		mvc.perform(put("/api/department/"+deptId).contentType(MediaType.APPLICATION_JSON)
//		.content("{\"role\" : \"Little Boss\"}")).andExpect(status()
//				.isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.role", is("Little Boss")));
//	}
//	
//	@Test
//	public void deleteADepartmentFromTheDatabase() throws Exception{
//		departmentRepo.save(new DepartmentModel("Big Boss"));
//		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
//		int index1 = id.indexOf(":") + 1;
//		int index2 = id.indexOf(",");
//		String deptId = id.substring(index1, index2);
//		mvc.perform(delete("/api/department/"+deptId).contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk());
//	}
//	
//	@Test
//	public void findingAllDepartmentsFromDatabase() throws Exception 
//	{
//		departmentRepo.save( new DepartmentModel("Big Boss"));
//		mvc.perform(MockMvcRequestBuilders.post("/api/department")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content("{\"role\" : \"Little Boss\"}"));
//		mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk()).andExpect(content()
//				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$[0].role", is("Big Boss"))).andExpect(status().isOk()).andExpect(content()
//				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$[1].role", is("Little Boss")));
//	}
//
//}
