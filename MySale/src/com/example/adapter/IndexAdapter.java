package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.Game;
import com.example.mysale.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexAdapter  extends BaseAdapter{
	public Context context;
	public List<Game> gameList=new ArrayList<Game>();
	public Game game;
	
	public IndexAdapter(Context context,List<Game> list) {
		super();
		this.context = context;
		this.gameList=list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return gameList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater=LayoutInflater.from(context);
		View view=layoutInflater.inflate(R.layout.activity_index_gridview,null);
		ImageView imageView=(ImageView)view.findViewById(R.id.indexImage);
		TextView textViewName=(TextView)view.findViewById(R.id.indexName);
		TextView textViewMoney=(TextView)view.findViewById(R.id.indexMoney);
		imageView.setImageBitmap(gameList.get(position).getImageBitmap());
		textViewName.setText(gameList.get(position).getName());
		textViewMoney.setText("$"+gameList.get(position).getMoney());
		return view;
	}


}
