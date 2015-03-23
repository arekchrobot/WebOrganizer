package ark.chr.web.organizer.dao.impl;

import ark.chr.web.organizer.model.OrganizerUser;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
@Transactional
public class TestDaoTest {

    @Inject
    private TestDao sut;

    public TestDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        sut.deleteAll();
    }

    @Test
    public void shouldInsertAndGetDataFromDatabase() {
        //given
        OrganizerUser insertUser = new OrganizerUser();
        insertUser.setName("testUser");
        //when
        sut.insert(insertUser);
        OrganizerUser result = sut.find(insertUser.getId());
        //then
        assertThat(result.getName())
                .isEqualTo("testUser");
        assertThat(result.getId())
                .isEqualTo(1L);
    }

}
