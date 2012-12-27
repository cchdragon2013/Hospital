package com.android.hospital.asyntask;

import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.PatientAdapter;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.PatientEntity;
import com.android.hospital.ui.fragment.LeftListFragment;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 
* @ClassName: PatientTask 
* @Description: TODO(��ȡ����ʵ��) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 ����11:06:44 
*
 */
public class PatientTask extends BaseAsyncTask{

	private Activity mActivity;
	private String sql;
	private ArrayList<PatientEntity> arrayList;
	private AsyncTaskCallback<PatientEntity> mTaskCallback;
	private LeftListFragment fm;
	
	public PatientTask(Activity activity,String sql){
		this.mActivity=activity;
		this.sql=sql;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
		arrayList=PatientEntity.init(dataList);
		DebugUtil.debug("arraylist"+arrayList.size());
		return arrayList;
	}

	@Override
	protected void onPostExecute(Object result) {
		DebugUtil.debug("listsiize"+arrayList.size());
		//�ص��ӿ�
		if (!(mActivity instanceof AsyncTaskCallback)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
		mTaskCallback=(AsyncTaskCallback<PatientEntity>) mActivity;
		fm=(LeftListFragment) mActivity.getFragmentManager().findFragmentByTag("leftfm");
		if (arrayList.size()!=0) {
			DebugUtil.debug("listsiize"+arrayList.size());			
			;
			final PatientAdapter adapter=new PatientAdapter(mActivity, arrayList);
			fm.setListAdapter(adapter);
			//fm.getListView().setItemChecked(0, true);//Ĭ��ѡ��
			fm.getListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					AppConstant.isPatientChoose=true;//�Ƿ��в��˱�ѡ��
					HospitalApp app=(HospitalApp) mActivity.getApplication();//����ѡ�в���Ϊȫ�ֱ���
					PatientEntity patientEntity=(PatientEntity) adapter.getItem(position);
					app.setPatientEntity(patientEntity);
					mTaskCallback.getSingle(patientEntity);
				}
			});
		}else {
			fm.setEmptyText("������Ϣ");
		}
	}
	
}
