package com.android.hospital.ui.fragment;

import java.util.ArrayList;

import com.android.hospital.adapter.CheckAdapter;
import com.android.hospital.adapter.DcAdviceAdapter;
import com.android.hospital.asyntask.DcAdviceTask;
import com.android.hospital.asyntask.add.MaxNumberTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.ui.activity.AddDcAdviceActivity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.app.ListFragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
* @ClassName: DoctorAdviceFragment 
* @Description: TODO(ҽ������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:40:47 
*
 */
public class DoctorAdviceFragment extends ListFragment {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//ArrayList<DcAdviceEntity> arrayList=new DcAdviceEntity("").list;
//		DcAdviceAdapter adapter=new DcAdviceAdapter(getActivity(),arrayList);
//		setListAdapter(adapter);
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		getListView().setDivider(getResources().getDrawable(R.drawable.main_list_divder));
//		getListView().setDividerHeight(2);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(Menu.NONE, Menu.FIRST, 0, "����ҽ��")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, Menu.FIRST+1, 0, "�ײ�ҽ��")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.addSubMenu(1, Menu.FIRST+2, 1, "ȫ��");
		menu.addSubMenu(1, Menu.FIRST+3, 1, "����");
		menu.addSubMenu(1, Menu.FIRST+4, 1, "��ʱ");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case Menu.FIRST:
			intent=new Intent();
			DoctorAdviceFragment fragment=(DoctorAdviceFragment) getActivity().getFragmentManager().findFragmentByTag("dcadvice");
			DcAdviceAdapter adapter=(DcAdviceAdapter) fragment.getListAdapter();		
			if (AppConstant.isPatientChoose) {
				DcAdviceEntity entity=(DcAdviceEntity) adapter.getItem(adapter.getCount()-1);
				intent.putExtra("subentity", entity);
				intent.setClass(getActivity(), AddDcAdviceActivity.class);
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
	
}
