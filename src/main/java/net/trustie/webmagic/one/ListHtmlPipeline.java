package net.trustie.webmagic.one;

import net.trustie.webmagic.one.dao.DetailHtmlDao;
import net.trustie.webmagic.one.dao.ErrorPageDao;
import net.trustie.webmagic.one.dao.ListHtmlDao;
import net.trustie.webmagic.one.model.DetailHtml;
import net.trustie.webmagic.one.model.ListHtml;
import net.trustie.webmagic.utils.DBPipeline;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

/**
 * Created by ganyiang on 2014/12/19.
 */
@Component("listHtmlPipeline")
public class ListHtmlPipeline extends DBPipeline {
	Logger logger = Logger.getLogger(ListHtmlPipeline.class);
	@Resource
	private ListHtmlDao listHtmlDao;
	@Resource
	private ErrorPageDao errorPageDao;

	@Override
	public void process(ResultItems resultItems, Task task) {
		ListHtml listHtml = resultItems.get("model");
		try {
			if (listHtml.getStatusCode() == 200) {
				listHtmlDao.insertListHtml(listHtml,
						this.getListHtmlTableName());
				logger.info("save page " + listHtml.getUrl());
			} else {
				errorPageDao.insertPage(this.getErrorPageTableName(), listHtml);
				logger.info("save error page " + listHtml.getUrl() + " error "
						+ listHtml.getStatusCode()+" success!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Logger logger = Logger.getLogger(MySqlPipelinePost.class);
			logger.error("error on Pipeline,when:" + listHtml.getUrl());
		}
	}
}
