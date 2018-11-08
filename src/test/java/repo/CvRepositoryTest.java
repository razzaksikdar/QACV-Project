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
//import com.qa.cv.Model.CvModel;
//import com.qa.cv.Repositories.CvRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = { MyCvV1Application.class})
//@DataJpaTest
//public class CvRepositoryTest {
//
//
//	@Autowired
//	private TestEntityManager entityManager;
//	
//	@Autowired
//	private CvRepository cvRepo;
//
//	@Test
//	public void retrieveByIdTest() 
//	{
//		CvModel model1 = new CvModel();
//		entityManager.persist(model1);
//		entityManager.flush();
//		assertTrue(cvRepo.findById(model1.getCvId()).isPresent());
//	}
//}
//
