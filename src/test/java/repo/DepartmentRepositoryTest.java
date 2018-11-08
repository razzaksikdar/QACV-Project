//package repo;
//
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.qa.cv.MyCvV1Application;
//import com.qa.cv.Model.DepartmentModel;
//import com.qa.cv.Repositories.DepartmentRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = { MyCvV1Application.class})
//@DataJpaTest
//public class DepartmentRepositoryTest {
//	
//	@Autowired
//	private TestEntityManager entityManager;
//	
//	@Autowired
//	private DepartmentRepository departmentRepo;
//
//	@Test
//	public void retrieveByIdTest() {
//		DepartmentModel departmentModelTest = new DepartmentModel("Big Boss");
//		entityManager.persist(departmentModelTest);
//		entityManager.flush();
//		assertTrue(departmentRepo.findById(departmentModelTest.getDepartmentId()).isPresent());
//	}
//}
