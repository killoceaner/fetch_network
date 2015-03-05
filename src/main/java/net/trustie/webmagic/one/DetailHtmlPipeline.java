package net.trustie.webmagic.one;

import net.trustie.webmagic.one.dao.DetailHtmlDao;
import net.trustie.webmagic.one.dao.ErrorPageDao;
import net.trustie.webmagic.one.dao.URLDao;
import net.trustie.webmagic.one.model.DetailHtml;
import net.trustie.webmagic.utils.DBPipeline;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by gyiang on 2014/11/16.
 */
@Component("detailHtmlPipeline")
public class DetailHtmlPipeline extends DBPipeline {
	Logger logger = Logger.getLogger(DetailHtmlPipeline.class);
	@Resource
	private DetailHtmlDao detailHtmlDao;

	@Resource
	private ErrorPageDao errorPageDao;

	@Override
	public void process(ResultItems resultItems, Task task) {
		DetailHtml detailHtml = resultItems.get("model");

		try {
			if (detailHtml.getStatusCode() == 200) {
				detailHtmlDao.insertDetailHtml(detailHtml,
						this.getDetailHtmlTableName());
				logger.info("save page " + detailHtml.getUrl() + " success");
			} else {
				errorPageDao.insertPage(this.getErrorPageTableName(),
						detailHtml);
				logger.info("save error page " + detailHtml.getUrl() + "error"
						+ detailHtml.getStatusCode() + " success!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Logger logger = Logger.getLogger(MySqlPipelinePost.class);
			logger.error("error on Pipeline,when:" + detailHtml.getUrl());
		}
	}
}
