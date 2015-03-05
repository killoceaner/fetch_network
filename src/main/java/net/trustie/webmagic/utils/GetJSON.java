package net.trustie.webmagic.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2014/7/31.
 */
public class GetJSON {
	public static void main(String[] args) throws JSONException, IOException {

		JSONObject json = new JSONObject();
		json.put("period", "7");
		json.put(
				"__RequestVerificationToken",
				"G7lAAjIljV3yoksJNBfesXVbBm9M4ZF0nS8yjs0i2QYkiay4O7ybpgVD07NOiXhUkCURkwW0wLdAIiOagn-nAoJJxjs0I-oYRyMXX8Z6pixZ7DNRWhrfd49RTTmEPV8APA0HDg2");
		HttpPost httpPost = new HttpPost(
				"http://kinectmultipoint.codeplex.com/stats/getActivity");
		StringEntity entity = new StringEntity(json.toString(), HTTP.UTF_8);
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		// HttpClient client = new DefaultHttpClient();
		HttpClient client = null;
		HttpResponse response = client.execute(httpPost);
		System.out.println(response.toString());

		/*
		 * List<NameValuePair> params = new ArrayList<NameValuePair>();
		 * params.add(new BasicNameValuePair("period", "7")); params.add(new
		 * BasicNameValuePair("__RequestVerificationToke...",
		 * "G7lAAjIljV3yoksJNBfesXVbBm9M4ZF0nS8yjs0i2QYkiay4O7ybpgVD07NOiXhUkCURkwW0wLdAIiOagn-nAoJJxjs0I-oYRyMXX8Z6pixZ7DNRWhrfd49RTTmEPV8APA0HDg2"
		 * )); //要传递的参数. String url =
		 * "http://kinectmultipoint.codeplex.com/stats/getActivity?" +
		 * URLEncodedUtils.format(params, HTTP.UTF_8); //拼接路径字符串将参数包含进去 json =
		 * get(url);
		 */
		System.out.println(json.get("Visits"));

	}

	public static JSONObject get(String url) {

		// HttpClient client = new DefaultHttpClient();
		HttpClient client = null;
		HttpGet get = new HttpGet(url);
		JSONObject json = null;
		try {
			HttpResponse res = client.execute(get);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				json = new JSONObject(new JSONTokener(new InputStreamReader(
						entity.getContent(), HTTP.UTF_8)));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);

		} finally {
			// 关闭连接 ,释放资源
			client.getConnectionManager().shutdown();
		}
		return json;
	}

}
