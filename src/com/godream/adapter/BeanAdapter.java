package com.godream.adapter;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.godream.util.BeanUtil;
import com.godream.util.BitmapHelper;

public class BeanAdapter extends BaseAdapter {
	private List<?> mData;
	private String[] mFrom;
	private LayoutInflater mInflater;
	private int mResource;
	private int[] mTo;
	private int selection;
	private Context context;
	public BeanAdapter(Context paramContext, List<?> paramList, int paramInt,
			String[] paramArrayOfString, int[] paramArrayOfInt) {
		this.mData = paramList;
		this.mResource = paramInt;
		this.mFrom = paramArrayOfString;
		this.mTo = paramArrayOfInt;
		this.mInflater = ((LayoutInflater) paramContext
				.getSystemService("layout_inflater"));
		this.context = paramContext;
	}

	private void bindView(int paramInt, View paramView) {
		Object localObject1 = getItem(paramInt);
		if ((localObject1 == null) || (paramView == null))
			return;
		String[] arrayOfString = this.mFrom;
		int[] arrayOfInt = this.mTo;
		int i = arrayOfInt.length;
		int j = 0;
		while (j < i) {
			View localView = paramView.findViewById(arrayOfInt[j]);
			if (localView == null) {
				throw new RuntimeException("check " + arrayOfInt[j] + " View"
						+ " is a childView of "
						+ paramView.getClass().getSimpleName());
			}
			Object localObject2 = BeanUtil.getValue(localObject1,
					arrayOfString[j]);
			String str = "";
			if (localObject2 == null) {
				j++;
				continue;
			} else {
				str = localObject2.toString();
			}
			if ((localView instanceof TextView)) {
				((TextView) localView).setText(str);
			} else if ((localView instanceof ImageView)) {
				Integer localInteger;
				localInteger = Integer.valueOf(Integer.parseInt(str));
				if (localInteger != null) {
					((ImageView) localView).setImageBitmap(BitmapHelper.drawableToBitmap(context.getResources().getDrawable(localInteger
							.intValue())));
				}
			}
			j++;
		}
	}

	public void addData(Collection paramCollection) {
		if (this.mData != null)
			this.mData.addAll(paramCollection);
	}

	@Override
	public int getCount() {
		if (this.mData == null)
			return 0;
		return this.mData.size();
	}

	@Override
	public Object getItem(int position) {
		if (this.mData == null)
			return Integer.valueOf(0);
		return this.mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getSelection() {
		return this.selection;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View localView;
		if (convertView == null) {
			localView = this.mInflater.inflate(this.mResource, null);
		} else {
			localView = convertView;
		}
		bindView(position, localView);
		return localView;
	}

	public void replaceData(Collection paramCollection) {
		if (this.mData != null) {
			this.mData.clear();
			this.mData.addAll(paramCollection);
		}
	}

	public void setData(List<?> paramList) {
		this.mData = paramList;
	}

	public void setSelection(int paramInt) {
		this.selection = paramInt;
	}
}
