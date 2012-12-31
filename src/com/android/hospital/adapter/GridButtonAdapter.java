package com.android.hospital.adapter;

import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
/**
 * 
* @ClassName: GridButtonAdapter 
* @Description: TODO(体温或者生命体征中一组button) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-30 上午10:51:25 
*
 */
public class GridButtonAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private String[] textArr = {"测试四","测试五","测试六","测试一","测试二","测试三","测试四","测试五","测试六","测试一","测试二","测试三","测试四","测试五","测试六","测试一","测试二","测试三","测试四","测试五","测试六"};
	
	public GridButtonAdapter(Context context){
		this.mContext=context;
		this.mInflater=LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return textArr.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView=mInflater.inflate(R.layout.gridview_item_button, null);
		final Button button=(Button) convertView.findViewById(R.id.gridview_item_but);
		button.setText(textArr[position]);	
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});				
		return convertView;
	}

}
