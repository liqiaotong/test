package com.example.mysale;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.adapter.IndexAdapter;
import com.example.entity.Game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeView{

	public static List<Game> gameList = new ArrayList<Game>();
	public static Game game = new Game();
	public GridView gridView;
	public XMLHandler handler;
	public LayoutInflater layoutInflater;
	public Context context;
	public View view;
	
	public HomeView(Context context)
	{
		this.context=context;
		layoutInflater=LayoutInflater.from(context);
		view=layoutInflater.inflate(R.layout.activity_viewpager_home, null);
	}
	
	
	public View findView() {
		// TODO Auto-generated method stub
		handler = new XMLHandler();
		gridView = (GridView)view.findViewById(R.id.gridView);
		gridView.setAdapter(new IndexAdapter(context, getDate()));
		setListener();
		return gridView;
		
	}
	private void setListener() {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(new GridViewListener());
	
	}
	
	class GridViewListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int location,
				long arg3) {
			// TODO Auto-generated method stub
			// Toast.makeText(IndexActivity.this, gameList+"",
			// Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(context, InfoActivity.class);
			game = gameList.get(location);
			context.startActivity(intent);
		}

	}
	
	public List<Game> getDate() {
		XMLThread thread = new XMLThread(gameList);
		thread.start();
		return gameList;
	}
	
	class XMLHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			// Toast.makeText(IndexActivity.this,gameList.size()+"",
			// Toast.LENGTH_SHORT).show();
		}

	}
	
	
	public String readData(InputStream inputStream, String charsetName)
			throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inputStream.close();
		return new String(data, charsetName);
	}

	class XMLThread extends Thread {
		public XMLThread(List<Game> ls) {
			super();
			gameList = ls;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				String hurl="http://"+IndexActivity.ip+"/MyServlet/IndexServlet?tis=android";
				HttpClient httpClient=new DefaultHttpClient();
				HttpPost httpPost=new HttpPost(hurl);
				httpClient.execute(httpPost);

				URL url = new URL("http://" +IndexActivity.ip + "/MyServlet/game.json");
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoInput(true);
				connection.setDoOutput(true);
				InputStream inputStream = connection.getInputStream();
				String context = HomeView.this.readData(inputStream,
						"UTF-8");
				JSONArray array = new JSONArray(context);
				for (int i = 0; i < array.length(); i++) {
					Game game = new Game();
					JSONObject object = array.getJSONObject(i);
					game.setId(object.getInt("id"));
					game.setName(object.getString("name"));
					game.setImage(object.getString("image"));
					game.setInfo(object.getString("info"));
					game.setMoney(object.getString("money"));
					gameList.add(game);
				}

				for (int i = 0; i < gameList.size(); i++) {
					URL urls = new URL("http://" + IndexActivity.ip + "/MyServlet/image/"
							+ gameList.get(i).getImage());
					HttpURLConnection connections = (HttpURLConnection) urls
							.openConnection();
					connections.setDoInput(true);
					connections.setDoOutput(true);
					InputStream inputStreams = connections.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(inputStreams);
					gameList.get(i).setImageBitmap(bitmap);
				}

				// SAXParserFactory factory = SAXParserFactory.newInstance();
				// SAXParser saxParser = factory.newSAXParser();
				// XMLReader xmlRead = saxParser.getXMLReader();
				// xmlRead.setContentHandler(new IndexListener(gameList));
				// xmlRead.parse(new InputSource(inputStream));
				// //Message message = new Message();
				// //IndexActivity.this.handler.sendMessage(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	

}
