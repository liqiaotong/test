package com.example.mysale;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.mysale.MainActivity.LoginThread;



import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{

	private Button btny;
	private Button btnn;
	private EditText editTextNamer;
	private EditText editTextPwdr;
	private EditText editTextPwdrs;
	private String answer;
	private RegisterHandler myHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		findView();
		setListener();
	}
	
	
	
	private void findView() {
		// TODO Auto-generated method stub
		btny=(Button)findViewById(R.id.yregister);
		btnn=(Button)findViewById(R.id.nregister);
		editTextNamer=(EditText)findViewById(R.id.editTextNamer);
		editTextPwdr=(EditText)findViewById(R.id.editTextPwdr);
		editTextPwdrs=(EditText)findViewById(R.id.editTextPwdrs);
		myHandler=new RegisterHandler();
		
	}



	private void setListener() {
		// TODO Auto-generated method stub
		btny.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					
					if(!editTextNamer.getText().toString().equals(""))
					{
						if(!editTextPwdr.getText().toString().equals(""))
						{
							if(!editTextPwdrs.getText().toString().equals(""))
							{
								if(editTextPwdrs.getText().toString().equals(editTextPwdr.getText().toString()))
								{
									RegisterThread thread=new RegisterThread();
									thread.start();
								}
								else
								{
									Toast.makeText(RegisterActivity.this, "确认密码不正确", Toast.LENGTH_SHORT).show();
								}
								
							}
							else
							{
								Toast.makeText(RegisterActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						Toast.makeText(RegisterActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
					}
				
			}
		});
		btnn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RegisterActivity.this.finish();
			}
		});
		
	}



	class RegisterHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(answer.equals("success"))
			{
				RegisterActivity.this.finish();
			}
			else
			{
				Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
			}
			
			
		}
		
	}
	
	class RegisterThread extends Thread
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				URL url=new URL("http://"+IndexActivity.ip+"/MyServlet/RegisterServlet");
				HttpURLConnection connection=(HttpURLConnection)url.openConnection();
				connection.setDoInput(true);
				connection.setDoOutput(true);
				OutputStream outputStream=connection.getOutputStream();
				String out="userName="+editTextNamer.getText().toString()+"&userPwd="+editTextPwdr.getText().toString()+"&tis=android";
				outputStream.write(out.getBytes());
				if(connection.getResponseCode()==200)
				{
					InputStream inputStream=connection.getInputStream();
					int i=0;
					String context="";
					while((i=inputStream.read())!=-1)
					{
						context+=(char)i;
					}
					answer=context;
					Message message=new Message();
					RegisterActivity.this.myHandler.sendMessage(message);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	}

