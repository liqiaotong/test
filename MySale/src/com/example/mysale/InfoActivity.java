package com.example.mysale;

import com.example.entity.Game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends Activity {

	public ImageView imageView;
	public TextView textViewName;
	public TextView textViewMoney;
	public Button btnBuy;
	public Button btnList;
	public static Game game;
	public static int tis=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		findView();
		setListener();
	}

	private void findView() {
		// TODO Auto-generated method stub
		game = HomeView.game;
		ImageView imageView = (ImageView) findViewById(R.id.infoImage);
		TextView textViewName = (TextView) findViewById(R.id.infoName);
		TextView textViewMoney = (TextView) findViewById(R.id.infoMoney);
		TextView textViewInfo = (TextView) findViewById(R.id.infoInfo);
		imageView.setImageBitmap(game.getImageBitmap());
		textViewName.setText(game.getName());
		textViewMoney.setText(game.getMoney());
		textViewInfo.setText(game.getInfo());

		btnBuy = (Button) findViewById(R.id.buy);
		btnList = (Button) findViewById(R.id.buyList);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		btnBuy.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				game=HomeView.game;
				tis=1;
				Intent intent = new Intent();
				intent.setClass(InfoActivity.this, BuyActivity.class);
				InfoActivity.this.startActivity(intent);
			}
		});

		btnList.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				tis=0;
				Intent intent = new Intent();
				intent.setClass(InfoActivity.this, BuyActivity.class);
				InfoActivity.this.startActivity(intent);
			}
		});

	}

}
