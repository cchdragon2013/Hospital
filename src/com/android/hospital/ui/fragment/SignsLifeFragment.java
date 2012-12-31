package com.android.hospital.ui.fragment;

import com.android.hospital.adapter.GridButtonAdapter;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.R.anim;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * 
* @ClassName: SignsLifeFragment 
* @Description: TODO(ÉúÃüÌåÕ÷) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-30 ÏÂÎç10:12:31 
*
 */
public class SignsLifeFragment extends Fragment{

	private GridView mGridView=null;
	private GridButtonAdapter mAdapter=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mAdapter=new GridButtonAdapter(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_signslife, null);
		mGridView=(GridView) view.findViewById(R.id.signslife_gridview);
		mGridView.setAdapter(mAdapter);	
		return view;
	}	
}
