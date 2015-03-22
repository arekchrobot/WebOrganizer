package ark.chr.web.organizer.services.impl;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import ark.chr.web.organizer.dao.api.ITestDao;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.ITestService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Arek
 */
@RunWith(MockitoJUnitRunner.class)
public class TestServiceTest {

    @InjectMocks
    private ITestService sut = new TestService();
    
    @Mock
    private ITestDao testDao;

    public TestServiceTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldReturnUsernameWhenCalledGetMsg() {
        //given
        OrganizerUser testUser = new OrganizerUser();
        testUser.setId(1L);
        testUser.setName("testUserMock");
        when(testDao.find(any(Long.class))).thenReturn(testUser);
        //when
        String result = sut.getMsg();
        //then
        assertThat(result)
                .isNotEmpty()
                .isEqualTo("testService + testDao = testUserMock");
    }

}
