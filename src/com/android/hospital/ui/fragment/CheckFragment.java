package com.android.hospital.ui.fragment;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.CheckAdapter;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.ui.activity.AddCheckActivity;
import com.android.hospital.ui.activity.AddDcAdviceActivity;
import com.android.hospital.ui.activity.CheckdetailActivity;
import com.android.hospital.ui.activity.MainActivity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
/**
 * 
* @ClassName: CheckFragment 
* @Description: TODO(检查界面显示) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:40:23 
*
 */
public class CheckFragment extends ListFragment {

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		getListView().setFastScrollEnabled(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(Menu.NONE, Menu.FIRST, 0, "新增检查")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case Menu.FIRST:
			if (AppConstant.isPatientChoose) {
				intent=new Intent();
				intent.setClass(getActivity(), AddCheckActivity.class);
				startActivityForResult(intent, 12);
			}else {
				Toast.makeText(getActivity(), "请先选择病人!", Toast.LENGTH_SHORT).show();//可根据左边病人listview是否有被选中判断
			}	
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	* @Title: clearAdapter 
	* @Description: TODO(清空adapter) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void clearAdapter(){
	    CheckAdapter adapter=(CheckAdapter) getListAdapter();
		if (null!=adapter&&adapter.getCount()!=0) {
			adapter.clearAdapter();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		DebugUtil.debug("resultCode---"+resultCode);
		DebugUtil.debug("requestCode---"+requestCode);
        if (resultCode==12) {
			MainActivity mainActivity=(MainActivity) getActivity();
			HospitalApp app=(HospitalApp) mainActivity.getApplication();
			mainActivity.putCheckTask(app.getPatientEntity());
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent=new Intent();
		intent.setClass(getActivity(), CheckdetailActivity.class);
		startActivity(intent);
	}
}
