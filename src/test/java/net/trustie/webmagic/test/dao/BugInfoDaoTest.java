package net.trustie.webmagic.test.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * User: cairne Date: 13-5-13 Time: 下午8:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext-*.xml" })
public class BugInfoDaoTest {

	@Ignore
	@Test
	public void test() {
		/*
		 * OpenStackBugInfo bugInfo = new OpenStackBugInfo();
		 * bugInfo.setSource("a"); try { final int add =
		 * bugInfoDAO.add(bugInfo); System.out.println(add); } catch (Exception
		 * e) { e.printStackTrace(); }
		 */
	}
}
