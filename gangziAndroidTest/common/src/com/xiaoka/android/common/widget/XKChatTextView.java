package com.xiaoka.android.common.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * 聊天消息文本显示类
 * 
 * @version 1.0
 * @since 1.0
 * @author oracle 
 *
 */
public class XKChatTextView extends TextView{

	
	private int mProgress = 0;
	
	public XKChatTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setText(CharSequence text, BufferType type) {
		if (text == null) {
			text = "";
		}
		super.setText(text, type);
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 1:
				update();
				break;
			case 2:
				stopUpload();
				break;
			}
		}
		
	};
	//显示loading页面
	public void setLoadShow(){
		setVisibility(View.VISIBLE);
	}
	//隐藏loading页面
	public void setLoadHide(){
		setVisibility(View.GONE);
	}
	
	public void startUpload(){
		setVisibility(View.VISIBLE);
		mHandler.sendEmptyMessage(1);
	}
	
	public void update(){
		if(mProgress < 100){
			mProgress++;
			//setText("" + mProgress + "进度");
			mHandler.sendEmptyMessageDelayed(1,100);
		}
		else{
			mHandler.sendEmptyMessage(2);
		}
	}
	
	public void stopUpload(){
		setVisibility(View.GONE);
	}
	
}
