package com.android.hospital.asyntask;

import java.util.ArrayList;

import com.android.hospital.adapter.DcAdviceAdapter;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.ui.fragment.DoctorAdviceFragment;
import com.android.hospital.webservice.WebServiceHelper;

import android.app.Activity;
import android.app.Fragment;

/**
 * 
* @ClassName: DcAdviceTask 
* @Description: TODO(获取医嘱信息) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 下午1:22:13 
*
 */
public class DcAdviceTask extends BaseAsyncTask{

	private DoctorAdviceFragment mFragment;
	private String sql;
	private ArrayList<DcAdviceEntity> arrayList;
	
	public DcAdviceTask(DoctorAdviceFragment fragment,String sql){
		this.mFragment=fragment;
		this.sql=sql;
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
		arrayList=DcAdviceEntity.init(dataList);
		return arrayList;
	}
	
	@Override
	protected void onPostExecute(Object result) {

		if (arrayList.size()!=0) {
			DcAdviceAdapter adapter=new DcAdviceAdapter(mFragment.getActivity(), arrayList);
			mFragment.setListAdapter(adapter);
			if (mFragment.isAdded()) {
				mFragment.setSelection(adapter.getCount()-1);
			}
		}
	}
}
