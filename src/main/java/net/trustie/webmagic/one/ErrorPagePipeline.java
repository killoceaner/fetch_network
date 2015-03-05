package net.trustie.webmagic.one;

import net.trustie.webmagic.one.dao.DetailHtmlDao;
import net.trustie.webmagic.one.dao.ErrorPageDao;
import net.trustie.webmagic.one.dao.ListHtmlDao;
import net.trustie.webmagic.one.dao.URLDao;
import net.trustie.webmagic.one.model.AbstractHtml;
import net.trustie.webmagic.one.model.DetailHtml;
import net.trustie.webmagic.one.model.ListHtml;
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
@Component("errorPagePipeline")
public class ErrorPagePipeline extends DBPipeline {
	Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private DetailHtmlDao detailHtmlDao;

	@Resource
	private ListHtmlDao listHtmlDao;

	@Resource
	private ErrorPageDao errorPageDao;

	@Override
	public void process(ResultItems resultItems, Task task) {
		AbstractHtml html = resultItems.get("model");

		try {
			if (html.getStatusCode() == 200) {
				if ("List".equals(html.getType())) {
					listHtmlDao.insertListHtml((ListHtml) html,
							this.getListHtmlTableName());
				} else if ("Detail".equals(html.getType())) {
					detailHtmlDao.insertDetailHtml((DetailHtml) html,
							this.getDetailHtmlTableName());
				}
				logger.info("save page " + html.getUrl() + " success!");
			} else {
				errorPageDao.insertPage(this.getErrorPageTableName(), html);
				logger.info("save error page " + html.getUrl() + "error"
						+ html.getStatusCode() + " success!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Logger logger = Logger.getLogger(MySqlPipelinePost.class);
			logger.error("error on Pipeline,when:" + html.getUrl());
		}
	}
}
