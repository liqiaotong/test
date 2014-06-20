package com.example.mysale;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

public class IndexActivity extends Activity {

	public Button btnSreach;
	public Button btnLogin;
	public Button btnExit;
	public Button btnBuyList;
	public Button indexbtn;
	public FrameLayout title;
	public SlidingDrawer slidingDrawer;
	int select = 0;
	static String ip= "10.0.2.2:8080";

	// ViewPager��google SDk���Դ���һ�����Ӱ���һ���࣬��������ʵ����Ļ����л���
	// android-support-v4.jar
	private ViewPager mPager;// ҳ������
	private List<View> listViews; // Tabҳ���б�
	private TextView t1, t2, t3, t4;// ҳ��ͷ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		findViews();
		setListeners();

		InitImageView();
		InitTextView();
		InitViewPager();
	}

	private void findViews() {
		// TODO Auto-generated method stub

		title = (FrameLayout) findViewById(R.id.title);

		// ����
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View view = layoutInflater.inflate(R.layout.activity_index_title, null);
		btnSreach = (Button) view.findViewById(R.id.sreach);
		btnSreach.setOnClickListener(new MySreachListener());
		title.addView(view);

		btnBuyList = (Button) findViewById(R.id.indexList);
		btnLogin = (Button) findViewById(R.id.loginBtn);
		btnExit = (Button) findViewById(R.id.exitBtn);
		indexbtn = (Button) findViewById(R.id.indexbtn);
		slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
	}

	private void setListeners() {
		// TODO Auto-generated method stub

		btnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(IndexActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		btnExit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				System.exit(0);
			}
		});

		btnBuyList.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				InfoActivity.tis = 0;
				Intent intent = new Intent();
				intent.setClass(IndexActivity.this, BuyActivity.class);
				startActivity(intent);
			}
		});

		indexbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPager.setCurrentItem(0);
				slidingDrawer.animateClose();
				slidingDrawer.toggle();
				
			}
		});

	}

	class MySreachListener implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			LayoutInflater layoutInflater = LayoutInflater
					.from(IndexActivity.this);
			final View sreachView = layoutInflater.inflate(
					R.layout.activity_index_title_sreach, null);
			Button sreachs = (Button) sreachView.findViewById(R.id.sreachs);
			sreachs.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});
			title.addView(sreachView);
			select = 1;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (select == 1) {
				LayoutInflater layoutInflater = LayoutInflater.from(this);
				View view = layoutInflater.inflate(
						R.layout.activity_index_title, null);
				Button sreachs = (Button) view.findViewById(R.id.sreach);
				sreachs.setOnClickListener(new MySreachListener());
				title.addView(view);
				select = 0;
			} else {
				exitBy2Click();
			}// ����˫���˳�����
		}
		return false;
	}

	/**
	 * ˫���˳�����
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // ׼���˳�
			Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // ȡ���˳�
				}
			}, 2000); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����

		} else {
			finish();
			System.exit(0);
		}
	}

	/**
	 * ��ʼ��ͷ��
	 */
	private void InitTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);
		t4 = (TextView) findViewById(R.id.text4);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
		t4.setOnClickListener(new MyOnClickListener(3));
	}

	/**
	 * ��ʼ��ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		HomeView home = new HomeView(IndexActivity.this);
		listViews.add(home.findView());
		listViews.add(mInflater.inflate(R.layout.activity_viewpager_hot, null));
		listViews
				.add(mInflater.inflate(R.layout.activity_viewpager_type, null));
		listViews
				.add(mInflater.inflate(R.layout.activity_viewpager_news, null));
		mPager.setAdapter(new MyPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	/**
	 * ��ʼ������
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.as)
				.getWidth();// ��ȡͼƬ���
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		offset = (screenW / 4 - bmpW) / 2;// ����ƫ����
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
	}

	/**
	 * ViewPager������
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * ͷ��������
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	private ImageView cursor;// ����ͼƬ
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���

	/**
	 * ҳ���л�����
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW - 13;// ҳ��1 -> ҳ��2 ƫ����
		int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����
		int three = two + 250;

		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
				}
				break;
			case 3:
				if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
				} else if (currIndex == 0) {
					animation = new TranslateAnimation(0, three, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
				}
				break;

			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}

}
