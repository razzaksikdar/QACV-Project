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
//import com.qa.cv.Model.UsersDataModel;
//import com.qa.cv.Repositories.UserRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = { MyCvV1Application.class})
//@DataJpaTest
//public class UserRepositoryTest {
//
//	@Autowired
//	private TestEntityManager entityManager;
//	
//	@Autowired
//	private UserRepository userRepo;
//
//	@Test
//	public void retrieveByIdTest() {
//		DepartmentModel department = new DepartmentModel("Big Boss");
//		UsersDataModel userModelTest = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department );
//		entityManager.persist(department);
//		entityManager.persist(userModelTest);
//		entityManager.flush();
//		assertTrue(userRepo.findById(userModelTest.getUserId()).isPresent());
//	}
//
//}
