package com.example.mysale;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.BuyAdapter;
import com.example.entity.Game;
import com.example.entity.Good;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class BuyActivity extends Activity {

	public static List<Good> goods;
	public Game game;
	public int tis;
	public ListView listView;
	public Button buyBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy);
		findView();
		setListener();

	}

	private void findView() {
		// TODO Auto-generated method stub
		game = InfoActivity.game;
		tis = InfoActivity.tis;
		listView = (ListView) findViewById(R.id.goodsList);
		listView.setAdapter(new BuyAdapter(BuyActivity.this, doSale()));
		buyBtn=(Button)findViewById(R.id.tbuy);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		buyBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goods = new ArrayList<Good>();
				Intent intent=new Intent();
				intent.setClass(BuyActivity.this, BuySuccessActivity.class);
		        startActivity(intent);
		       
			}
		});
	}

	private List<Good> doSale() {
		// TODO Auto-generated method stub
		if (goods == null) {
			goods = new ArrayList<Good>();
		}
		if (tis == 1) {
			if (goods.size() > 0) {
				int s=0;
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGame() == game) {
						goods.get(i)
								.setQuantity(goods.get(i).getQuantity() + 1);
						s=1;
					}
				}
				if(s==0)
				{
					Good good = new Good();
					good.setGame(game);
					good.setQuantity(1);
					goods.add(good);
				}
				
				
			}
			if (goods.size() == 0) {
				Good good = new Good();
				good.setGame(game);
				good.setQuantity(1);
				goods.add(good);
			}

		}
		return goods;
	}

}
