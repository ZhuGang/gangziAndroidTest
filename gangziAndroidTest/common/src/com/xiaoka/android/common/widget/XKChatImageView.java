package com.xiaoka.android.common.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.xiaoka.android.common.R;

/**
 * 聊天消息图片显示类
 * 
 * @version 1.0
 * @since 1.0
 * @author oracle
 * 
 */
public class XKChatImageView extends ImageView{

	private View view;
	
	private XKChatTextView chat_item_text;

	public XKChatImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public void init(Context context){
		view = LayoutInflater.from(context).inflate(R.layout.chat_item_imageview, null);
		chat_item_text = (XKChatTextView)view.findViewById(R.id.chat_item_text);
		
	}
	
}
