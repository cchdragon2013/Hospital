package com.android.hospital.ui.fragment;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.InspectionAdapter;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.entity.InspectionEntity;
import com.android.hospital.ui.activity.AddCheckActivity;
import com.android.hospital.ui.activity.AddInspectionActivity;
import com.android.hospital.ui.activity.InspectiondetailActivity;
import com.android.hospital.ui.activity.MainActivity;
import com.android.hospital.util.DebugUtil;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
/**
 * 
* @ClassName: InspectionFragment 
* @Description: TODO(检验界面) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:41:04 
*
 */
public class InspectionFragment extends ListFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		getListView().setFastScrollEnabled(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(Menu.NONE, Menu.FIRST, 0, "新增检验")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.addSubMenu(1, Menu.FIRST+1, 1, "按时间查询");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case Menu.FIRST:
			if (AppConstant.isPatientChoose) {
				intent=new Intent();
				intent.setClass(getActivity(), AddInspectionActivity.class);
				startActivityForResult(intent, 13);
			}else {
				Toast.makeText(getActivity(), "请先选择病人!", Toast.LENGTH_SHORT).show();//可根据左边病人listview是否有被选中判断
			}				
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		InspectionAdapter adapter=(InspectionAdapter) l.getAdapter();
		InspectionEntity item=(InspectionEntity) adapter.getItem(position);
		Intent intent=new Intent();
		intent.putExtra("inspection", item);
		intent.setClass(getActivity(), InspectiondetailActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==13) {
			MainActivity mainActivity=(MainActivity) getActivity();
			HospitalApp app=(HospitalApp) mainActivity.getApplication();
			mainActivity.putInspectionTask(app.getPatientEntity());
		}
	}
}
