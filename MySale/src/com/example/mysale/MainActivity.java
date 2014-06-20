package com.example.mysale;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btnLogin;
	private TextView btnRegister;
	private EditText editTextName;
	private EditText editTextPwd;
	private String answer;
	private LoginHandler myHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
    }

    private void findViews() {
		// TODO Auto-generated method stub
		btnLogin=(Button)findViewById(R.id.login);
		btnRegister=(TextView)findViewById(R.id.register);
		editTextName=(EditText)findViewById(R.id.editTextName);
		editTextPwd=(EditText)findViewById(R.id.editTextPwd);
		myHandler=new LoginHandler();
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		btnLogin.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!editTextName.getText().toString().equals(""))
				{
					if(!editTextPwd.getText().toString().equals(""))
					{
						LoginThread loginThread=new LoginThread();
						loginThread.start();
					}
					else
					{
						Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
					}
					
				}
				else
				{
					Toast.makeText(MainActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		btnRegister.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
	}
	
	class LoginHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if(answer.equals("success"))
			{
				//Intent intent=new Intent();
				//intent.setClass(MainActivity.this,IndexActivity.class);
				//startActivity(intent);
				Toast.makeText(MainActivity.this,editTextName.getText().toString()+"登录成功", Toast.LENGTH_SHORT).show();
				finish();
				
			}else
			{
				Toast.makeText(MainActivity.this, answer+"账号或密码不正确", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	class LoginThread extends Thread
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				String url="http://"+IndexActivity.ip+"/MyServlet/LoginServlet";
				HttpClient httpClient=new DefaultHttpClient();
				HttpPost httpPost=new HttpPost(url);
				List<NameValuePair> list=new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("userName",editTextName.getText().toString()));
				list.add(new BasicNameValuePair("userPwd",editTextPwd.getText().toString()));
				list.add(new BasicNameValuePair("tis","android"));
				httpPost.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
				HttpResponse httpResponse=httpClient.execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				{
					answer=EntityUtils.toString(httpResponse.getEntity());
					Message message=new Message();
					MainActivity.this.myHandler.sendMessage(message);
				}
				
				//不安全
//				URL url=new URL("http://192.168.1.100:8080/MyServlet/LoginServlet");
//				HttpURLConnection connection=(HttpURLConnection)url.openConnection();
//				connection.setDoInput(true);
//				connection.setDoOutput(true);
//				OutputStream outputStream=connection.getOutputStream();
//				String out="userName="+editTextName.getText().toString()+"&userPwd="+editTextPwd.getText().toString()+"&tis=android";
//				outputStream.write(out.getBytes());
//				if(connection.getResponseCode()==200)
//				{
//					InputStream inputStream=connection.getInputStream();
//					int i=0;
//					String context="";
//					while((i=inputStream.read())!=-1)
//					{
//						context+=(char)i;
//					}
//					answer=context;
//					Message message=new Message();
//					MainActivity.this.myHandler.sendMessage(message);
//				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}
	
	

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
