package com.android.hospital.ui.fragment;

import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.CheckAdapter;
import com.android.hospital.adapter.DcAdviceAdapter;
import com.android.hospital.asyntask.DcAdviceTask;
import com.android.hospital.asyntask.add.MaxNumberTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.ui.activity.AddDcAdviceActivity;
import com.android.hospital.ui.activity.MainActivity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.app.ListFragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
* @ClassName: DoctorAdviceFragment 
* @Description: TODO(医嘱界面) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:40:47 
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
		getListView().setFastScrollEnabled(true);
		getListView().setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.setHeaderTitle("请选择");       
				menu.add(0, 0, 0, "停止该条医嘱");  
				menu.add(0, 1, 0, "复制该条医嘱");  
				menu.add(0, 2, 0, "取消");  
			}
		});
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(Menu.NONE, Menu.FIRST, 0, "新增医嘱")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, Menu.FIRST+1, 0, "套餐医嘱")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.addSubMenu(1, Menu.FIRST+2, 1, "全部");
		menu.addSubMenu(1, Menu.FIRST+3, 1, "长期");
		menu.addSubMenu(1, Menu.FIRST+4, 1, "临时");
		menu.addSubMenu(1, Menu.FIRST+5, 1, "按时间查询");
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
				startActivityForResult(intent, 11);
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
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		ContextMenuInfo menuInfo = (ContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case 0:
			 Toast.makeText(getActivity(), "111111111111", 200).show(); 
			return true;

		default:
			break;
		}
		return false;
	}		
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==11) {
			MainActivity mainActivity=(MainActivity) getActivity();
			HospitalApp app=(HospitalApp) mainActivity.getApplication();
			mainActivity.putDcAdviceTask(app.getPatientEntity());
		}
	}
}
