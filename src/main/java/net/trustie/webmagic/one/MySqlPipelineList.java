package net.trustie.webmagic.one;

import net.trustie.webmagic.one.dao.DetailHtmlDao;
import net.trustie.webmagic.one.dao.URLDao;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by gyiang on 2014/11/16.
 */
@Component("MySqlPipelineList")
public class MySqlPipelineList implements Pipeline {
	@Resource
	private URLDao htmlTargetUrlDao;

	@Override
	public void process(ResultItems resultItems, Task task) {
		List<String> l = (List<String>) resultItems.get("findUrl");
		// 爬取时间戳
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String crawlerTime = bartDateFormat.format(new Date());
		for (String urlPost : l) {
			htmlTargetUrlDao.add(urlPost, crawlerTime,
					resultItems.get("listTableName").toString());
		}

	}
}
