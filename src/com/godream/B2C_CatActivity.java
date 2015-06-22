package com.godream;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.godream.adapter.B2C_Cat2_Adapter;
import com.godream.adapter.B2C_Cat3_Adapter;
import com.godream.adapter.B2C_Comm_Adapter;
import com.godream.bean.B2C_Cat2_Bean;
import com.godream.bean.B2C_Cat3_Bean;
import com.godream.bean.B2C_Comm_Bean;
import com.godream.util.LogUtil;

public class B2C_CatActivity extends Activity{
	
	private ListView mCommList;
	private ListView mCat2List;
	private ListView mCat3List;
	private LinearLayout mCatList_ll;
	private LinearLayout linear_listview3;
	
	private B2C_Comm_Adapter b2c_comm_adapter;
	private B2C_Cat2_Adapter b2c_cat2_adapter;
	private B2C_Cat3_Adapter b2c_cat3_adapter;
	
	private List<B2C_Comm_Bean> commList;
	private List<B2C_Cat2_Bean> cat2List;
	private List<B2C_Cat3_Bean> cat3List;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_b2c);
		mCommList = (ListView) findViewById(R.id.commlist);
		mCat2List = (ListView) findViewById(R.id.cat2list);
		mCat3List = (ListView) findViewById(R.id.cat3list);
		mCatList_ll = (LinearLayout) findViewById(R.id.ll_catlist);
		linear_listview3 = (LinearLayout) findViewById(R.id.linear_listview3);
				
		commList = B2C_Comm_Bean.getData();
		cat2List = B2C_Cat2_Bean.getData();
		cat3List = B2C_Cat3_Bean.getData();
		
		b2c_comm_adapter = new B2C_Comm_Adapter(this, commList);
		mCommList.setAdapter(b2c_comm_adapter);
		
		setCat2Adapter(cat2List);
		setCat3Adapter(cat3List);
		
		
		mCommList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				//改变点击的item状态
				mCatList_ll.setVisibility(View.VISIBLE);
				B2C_Comm_Adapter.pressedPosition = position;
				b2c_comm_adapter.notifyDataSetChanged();
				B2C_Cat2_Adapter.pressedPosition = -1;//隐藏第二个listview的箭头
				
				//显示第二个listview，隐藏第三个listview
				linear_listview3.setVisibility(View.GONE);
				mCat3List.setVisibility(View.GONE);
				cat2List = B2C_Cat2_Bean.getDatasByParentId(commList.get(position).getId());//根据ID获取子数据
				setCat2Adapter(cat2List);
				mCat2List.startAnimation(getTransSet_cat3(mCat2List));
				mCat2List.setVisibility(View.VISIBLE);
				
			}
		});
		
		mCat2List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				//改变点击的item箭头
				B2C_Cat2_Adapter.pressedPosition = position;
				b2c_cat2_adapter.notifyDataSetChanged();
				
				//显示第三个listview
				linear_listview3.setVisibility(View.VISIBLE);
				cat3List = B2C_Cat3_Bean.getDatasByParentId(cat2List.get(position).getId());
				setCat3Adapter(cat3List);
				b2c_cat3_adapter.notifyDataSetChanged();
				mCat3List.startAnimation(getTransSet_cat3(mCat3List));
				mCat3List.setVisibility(View.VISIBLE);
			}
		});
	}
	private void setCat2Adapter(List<B2C_Cat2_Bean> list){
		b2c_cat2_adapter = new B2C_Cat2_Adapter(this, cat2List);
		mCat2List.setAdapter(b2c_cat2_adapter);
	}
	
	private void setCat3Adapter(List<B2C_Cat3_Bean> list){
		b2c_cat3_adapter = new B2C_Cat3_Adapter(this, cat3List);
		mCat3List.setAdapter(b2c_cat3_adapter);
	}
	private Animation getTransSet(View v) {
		LogUtil.getInstance().i("v.getX():"+v.getX() + ",v.getMeasuredWidth():" + v.getMeasuredWidth() );
		TranslateAnimation localTranslateAnimation = new TranslateAnimation(
				v.getX()-v.getMeasuredWidth(), 0.0F, 0.0F, 0.0F);
		localTranslateAnimation.setDuration(200L);
		localTranslateAnimation.setFillBefore(false);
		localTranslateAnimation.setFillAfter(true);
		return localTranslateAnimation;
	}
	
	private Animation getTransSet_cat3(View v) {
		LogUtil.getInstance().i("v.getX():"+v.getX() + ",v.getMeasuredWidth():" + v.getMeasuredWidth() );
		Animation localTranslateAnimation = AnimationUtils.loadAnimation(this, R.anim.b2c_menu_enter);
		localTranslateAnimation.setFillBefore(false);
		localTranslateAnimation.setFillAfter(true);
		return localTranslateAnimation;
	}
}



















