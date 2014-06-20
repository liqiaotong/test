package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.Good;
import com.example.mysale.R;

public class BuyAdapter extends BaseAdapter{

	public Context context;
	public List<Good> goods;
	
	public BuyAdapter(Context context,List<Good> goods)
	{
		this.context=context;
		this.goods=goods;
	}
	
	
	public int getCount() {
		// TODO Auto-generated method stub
		return goods.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	double all=0;

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater=LayoutInflater.from(context);
		View view=layoutInflater.inflate(R.layout.activity_buy_list, null);
		View views=layoutInflater.inflate(R.layout.activity_buy, null);
		
		ImageView imageView=(ImageView)view.findViewById(R.id.buyImage);
		TextView buyName=(TextView)view.findViewById(R.id.buyName);
		TextView buyMoney=(TextView)view.findViewById(R.id.buyMoney);
		TextView buyNum=(TextView)view.findViewById(R.id.buyNum);
		TextView count=(TextView)view.findViewById(R.id.count);
		TextView tbuy=(TextView)views.findViewById(R.id.tbuy);
		
		
		imageView.setImageBitmap(goods.get(position).getGame().getImageBitmap());
		buyName.setText(goods.get(position).getGame().getName());
		buyMoney.setText("$"+goods.get(position).getGame().getMoney());
		buyNum.setText(goods.get(position).getQuantity()+"");
		double s=0;
		double a=Double.parseDouble(goods.get(position).getGame().getMoney());
		double e=goods.get(position).getQuantity();
		s=a*e;
		all=all+s;
		count.setText("$"+s);
		tbuy.setText("$"+all+" ¹ºÂò");
		return view;
	}

}
