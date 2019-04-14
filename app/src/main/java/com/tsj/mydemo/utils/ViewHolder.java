package com.tsj.mydemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	private final SparseArray<View> mViews;
	private View mConvertView;
	private int mPosition;
	private ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.mViews = new SparseArray<View>();
		this.mPosition = position;
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {

		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {

		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public View getConvertView() {
		return mConvertView;
	}
	
	/**
	 * 为TextView设置字符
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, String text)
	{
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}
	
	public ViewHolder setText(int viewId, String text,int textSize){
		TextView view = getView(viewId);
		view.setText(text);
		view.setTextSize(textSize);
		return this;
	}
	
	public ViewHolder setText(int viewId, String text,int textSize,int color){
		TextView view = getView(viewId);
		view.setText(text);
		view.setTextSize(textSize);
		view.setTextColor(color);
		return this;
	}
 
	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId)
	{
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
 
		return this;
	}
 
	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm)
	{
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}
 
	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param
	 * @return
	 */
	public ViewHolder setImageByUrl(final Context context, int viewId, final String url)
	{
		Log.w("Picture","list url ========== : " + url);
//		Glide.with(context).load(url).into((ImageView)getView(viewId));
		return this;
	}
	
	public int getPosition()
	{
		return mPosition;
	}


}
