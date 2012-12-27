package com.android.hospital.ui.fragment;

import com.android.hospital.adapter.CheckAdapter;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.ui.activity.AddCheckActivity;
import com.android.hospital.ui.activity.AddDcAdviceActivity;
import com.android.hospital.ui.activity.R;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
/**
 * 
* @ClassName: CheckFragment 
* @Description: TODO(��������ʾ) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:40:23 
*
 */
public class CheckFragment extends ListFragment {

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(Menu.NONE, Menu.FIRST, 0, "�������")
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
				startActivity(intent);
			}else {
				Toast.makeText(getActivity(), "����ѡ����!", Toast.LENGTH_SHORT).show();//�ɸ�����߲���listview�Ƿ��б�ѡ���ж�
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
	* @Description: TODO(���adapter) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void clearAdapter(){
	    CheckAdapter adapter=(CheckAdapter) getListAdapter();
		if (null!=adapter&&adapter.getCount()!=0) {
			adapter.clearAdapter();
		}
	}
}
