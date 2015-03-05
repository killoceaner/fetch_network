package net.trustie.webmagic.oneX;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by Administrator on 2014/11/15.
 */
public class ConsolePipelineOther implements Pipeline {
	@Override
	public void process(ResultItems resultItems, Task task) {
		System.out.println("get page: " + resultItems.getRequest().getUrl());
	}
}
