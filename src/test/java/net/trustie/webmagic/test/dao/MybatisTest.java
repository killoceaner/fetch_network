package net.trustie.webmagic.test.dao;

import java.util.List;

import javax.annotation.Resource;

import net.trustie.webmagic.one.dao.ListHtmlDao;
import net.trustie.webmagic.one.model.ListHtml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MybatisTest {
	@Resource
	private ListHtmlDao listHtmlDao;
	public List<ListHtml> getBatch(){
		return listHtmlDao.getBatch("freecode_html_list", 0 , 4);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"spring/applicationContext*.xml");
		MybatisTest mbt = applicationContext.getBean(MybatisTest.class);
		List<ListHtml> list= mbt.getBatch();
		for(ListHtml html : list){
			System.out.println(html.toString());
		}
		System.out.println(list.size());
	}

}
