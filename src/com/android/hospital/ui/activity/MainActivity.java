package com.android.hospital.ui.activity;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.android.hospital.adapter.CheckAdapter;
import com.android.hospital.adapter.DcAdviceAdapter;
import com.android.hospital.adapter.InspectionAdapter;
import com.android.hospital.adapter.PrescriptionAdapter;
import com.android.hospital.asyntask.BaseAsyncTask;
import com.android.hospital.asyntask.CheckTask;
import com.android.hospital.asyntask.DcAdviceTask;
import com.android.hospital.asyntask.DepartmentTask;
import com.android.hospital.asyntask.BaseAsyncTask.AsyncTaskCallback;
import com.android.hospital.asyntask.add.DrugOrNonDrugTask;
import com.android.hospital.asyntask.InspectionTask;
import com.android.hospital.asyntask.PrescriptionTask;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.PatientEntity;
import com.android.hospital.ui.fragment.CheckFragment;
import com.android.hospital.ui.fragment.DoctorAdviceFragment;
import com.android.hospital.ui.fragment.InspectionFragment;
import com.android.hospital.ui.fragment.LeftListFragment;
import com.android.hospital.ui.fragment.PrescriptionFragment;
import com.android.hospital.util.DebugUtil;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
* @ClassName: MainActivity 
* @Description: TODO(程序主界面) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:36:57 
*
 */
public class MainActivity extends Activity implements AsyncTaskCallback<PatientEntity>{	
	private LeftListFragment leftFm;
	private Spinner mSpinner;
	private TextView titleTev;
	private ActionBar actionBar;
	private List<AsyncTask> asyncTasks=null;
	private PatientEntity patientEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		actionBar=getActionBar();
		actionBar.setHomeButtonEnabled(true);//设置可点击
		actionBar.setLogo(R.drawable.logo_new);
		actionBar.setDisplayShowTitleEnabled(false);
		FragmentTransaction ft=getFragmentManager().beginTransaction();
		if (leftFm==null) {
			leftFm=new LeftListFragment();		
			ft.add(R.id.main_left_panel, leftFm ,"leftfm");
		}
		ft.commit();
		
		asyncTasks=new ArrayList<AsyncTask>();
		mSpinner=(Spinner) findViewById(R.id.main_left_department_spinner);
		titleTev=(TextView) findViewById(R.id.main_patient_info_tev);
		setSpinner();
		//setTitleTev();
		addTabFragment();
	}
	
    
	public void setSpinner(){
		String sql=ServerDao.getQuery("staff_group_dict", 
				                       new String[]{"group_name","group_code"}, 
				                       new String[]{"group_class"}, 
				                       new String[]{"病区医生"});
		new DepartmentTask(this, sql).execute();
	}
	
	public void setTitleTev(PatientEntity patientEntity){
		titleTev.setText("床号："+patientEntity.bed_no+" 姓名："+patientEntity.name+" 性别："+patientEntity.sex
				       +" 病人ID："+patientEntity.patient_id+" 剩余预交金："+patientEntity.prepayments+" 费别："+patientEntity.charge_type);
	}
		
	public void addTabFragment(){
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab()
				     .setText("医嘱")
				     .setTabListener(new TabListener<DoctorAdviceFragment>(
		                        this, "dcadvice", DoctorAdviceFragment.class)));
		actionBar.addTab(actionBar.newTab()
			     .setText("检查")
			     .setTabListener(new TabListener<CheckFragment>(
	                        this, "check", CheckFragment.class)));
		actionBar.addTab(actionBar.newTab()
			     .setText("检验")
			     .setTabListener(new TabListener<InspectionFragment>(
	                        this, "inspection", InspectionFragment.class)));
		actionBar.addTab(actionBar.newTab()
			     .setText("处方")
			     .setTabListener(new TabListener<PrescriptionFragment>(
	                        this, "prescription", PrescriptionFragment.class)));
		actionBar.addTab(actionBar.newTab()
			     .setText("体温图")
			     .setTabListener(new TabListener<DoctorAdviceFragment>(
	                        this, "simple", DoctorAdviceFragment.class)));
	}
	
	
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        private final Bundle mArgs;
        private Fragment mFragment;

        public TabListener(Activity activity, String tag, Class<T> clz) {
            this(activity, tag, clz, null);
        }

        public TabListener(Activity activity, String tag, Class<T> clz, Bundle args) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            mArgs = args;

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
            mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
        	FragmentTransaction ft=mActivity.getFragmentManager().beginTransaction();
        	ft.add(R.id.main_right_frame, mFragment, mTag);
            if (mFragment != null && !mFragment.isDetached()) {
                ft.detach(mFragment);
                ft.commit();
                DebugUtil.debug("TabListener--if--被调用");
            }
            DebugUtil.debug("TabListener被调用");
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
                ft.add(R.id.main_right_frame, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }
            DebugUtil.debug("onTabSelected被调用");
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
            }
            DebugUtil.debug("onTabUnselected被调用");
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
        }
    }


	@Override
	public void getSingle(PatientEntity value) {
		// TODO Auto-generated method stub
		DebugUtil.debug("测试getSingle"+value.name);
		this.patientEntity=value;
		setTitleTev(value);
		putDcAdviceTask(value);
		putCheckTask(value);
		putInspectionTask(value);
		putPrescriptionTask(value);
	}


	@Override
	public void getList(ArrayList<PatientEntity> values) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 
	* @Title: putDcAdviceTask 
	* @Description: TODO(医嘱任务) 
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void putDcAdviceTask(PatientEntity value){
		DebugUtil.debug("测试put");
		DoctorAdviceFragment fragment=(DoctorAdviceFragment) getFragmentManager().findFragmentByTag("dcadvice");
		if (fragment!=null) {
			DcAdviceAdapter adapter=(DcAdviceAdapter) fragment.getListAdapter();
			if (null!=adapter&&adapter.getCount()!=0) {
				adapter.clearAdapter();
			}		
			String tableName="orders";
			String[] paramArray1=new String[]{"repeat_indicator","TO_CHAR(start_date_time,'yyyy-MM-dd hh24:mi:ss') as start_date_time",
					                          "order_text","dosage","dosage_units","administration","frequency","perform_schedule",
					                          "TO_CHAR(stop_date_time,'yyyy-MM-dd hh24:mi:ss') as stop_date_time","freq_detail","doctor",
					                          "order_no","freq_counter","freq_interval","freq_interval_unit","order_status","ordering_dept",
					                          "order_sub_no","order_code","order_class","drug_billing_attr"};
			String[] whereArr=new String[]{"patient_id","visit_id"};
			String[] paramArray2=new String[]{value.patient_id,value.visit_id};
			String sql=ServerDao.getQuery(tableName, paramArray1, whereArr, paramArray2);
			putAsyncTask(new DcAdviceTask(fragment, sql).execute());
		}
		
	}
	
	/**
	 * 
	* @Title: putCheckTask 
	* @Description: TODO(检查) 
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void putCheckTask(PatientEntity value){
		CheckFragment fragment=(CheckFragment) getFragmentManager().findFragmentByTag("check");
		if (fragment!=null) {
            fragment.clearAdapter();
			String tableName="exam_master a,exam_report b,exam_items c";
			String[] paramArray1=new String[]{"a.exam_no","a.patient_id","a.visit_id","a.name","a.sex",
					                          "a.date_of_birth","c.exam_item","a.exam_class","a.exam_sub_class",
					                          "TO_CHAR(a.req_date_time,'yyyy-MM-dd hh24:mi:ss') as req_date_time",
					                          "a.req_physician","TO_CHAR(a.report_date_time,'yyyy-MM-dd hh24:mi:ss') as report_date_time",
					                          "a.reporter","b.description","b.impression"};
			String customWhere="where a.exam_no = b.exam_no and a.exam_no = c.exam_no and a.patient_id = '"+value.patient_id+"' "
					           +"and a.visit_id = '"+value.visit_id+"'";
			String sql=ServerDao.getQueryCustom(tableName, paramArray1, customWhere);
			putAsyncTask(new CheckTask(fragment, sql).execute());
		}
		
	}
	/**
	 * 
	* @Title: putInspectionTask 
	* @Description: TODO(检验任务) 
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void putInspectionTask(PatientEntity value){
		InspectionFragment fragment=(InspectionFragment) getFragmentManager().findFragmentByTag("inspection");
		if(fragment!=null){
			InspectionAdapter adapter=(InspectionAdapter) fragment.getListAdapter();
			if (null!=adapter&&adapter.getCount()!=0) {
				adapter.clearAdapter();
			}
			String tableName="LAB_TEST_ITEMS ,LAB_TEST_MASTER ,DEPT_DICT";
			String[] paramArray1=new String[]{"LAB_TEST_ITEMS.ITEM_NO","LAB_TEST_ITEMS.ITEM_NAME","LAB_TEST_MASTER.SPECIMEN","LAB_TEST_ITEMS.ITEM_CODE","LAB_TEST_ITEMS.TEST_NO",
					                          "DEPT_DICT.DEPT_NAME","LAB_TEST_MASTER.RESULT_STATUS","TO_CHAR(LAB_TEST_MASTER.REQUESTED_DATE_TIME,'yyyy-MM-dd hh24:mi:ss') as REQUESTED_DATE_TIME",
					                          "LAB_TEST_MASTER.BILLING_INDICATOR","LAB_TEST_MASTER.PRIORITY_INDICATOR","LAB_TEST_MASTER.CHARGE_TYPE","LAB_TEST_MASTER.NOTES_FOR_SPCM",
					                          "LAB_TEST_MASTER.PERFORMED_BY","LAB_TEST_MASTER.RELEVANT_CLINIC_DIAG","LAB_TEST_MASTER.NAME","LAB_TEST_MASTER.SEX","LAB_TEST_MASTER.AGE",
					                          "LAB_TEST_MASTER.ORDERING_DEPT","LAB_TEST_MASTER.PATIENT_ID","LAB_TEST_MASTER.ORDERING_PROVIDER "};
			String customWhere="WHERE (LAB_TEST_MASTER.TEST_NO= LAB_TEST_ITEMS.TEST_NO ) and "
					+"( LAB_TEST_MASTER.PERFORMED_BY = DEPT_DICT.DEPT_CODE ) and "
					+"( LAB_TEST_MASTER.PATIENT_ID = '"+value.patient_id +"' ) and "
					+"(LAB_TEST_MASTER.VISIT_ID = '"+value.visit_id+"' )  "
					+"order by LAB_TEST_ITEMS.TEST_NO,LAB_TEST_ITEMS.ITEM_NO  ";

			String sql=ServerDao.getQueryCustom(tableName, paramArray1, customWhere);
			putAsyncTask(new InspectionTask(fragment, sql).execute());
		}
		
	}
	
	
	/**
	 * 
	* @Title: putPrescriptionTask 
	* @Description: TODO(处方的任务) 
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void putPrescriptionTask(PatientEntity value){
		PrescriptionFragment fragment=(PrescriptionFragment) getFragmentManager().findFragmentByTag("prescription");
		if (fragment!=null) {
			PrescriptionAdapter adapter=(PrescriptionAdapter) fragment.getListAdapter();
			if (null!=adapter&&adapter.getCount()!=0) {
				adapter.clearAdapter();
			}
			String tableName="DOCT_DRUG_PRESC_MASTER, dept_dict";
			String[] paramArray1=new String[]{"DOCT_DRUG_PRESC_MASTER.PRESC_NO","TO_CHAR(DOCT_DRUG_PRESC_MASTER.PRESC_DATE,'yyyy-MM-dd hh24:mi:ss') as PRESC_DATE",
					                          "DOCT_DRUG_PRESC_MASTER.PRESCRIBED_BY","DEPT_DICT.DEPT_NAME","DOCT_DRUG_PRESC_MASTER.PRESC_TYPE","DOCT_DRUG_PRESC_MASTER.REPETITION",
					                          "DOCT_DRUG_PRESC_MASTER.COSTS","DOCT_DRUG_PRESC_MASTER.PRESC_STATUS"};
			String customWhere="WHERE DOCT_DRUG_PRESC_MASTER.Dispensary = dept_dict.dept_code  and (PRESC_STATUS not in (2, 3))  AND (DOCT_DRUG_PRESC_MASTER.PATIENT_ID = '"+value.patient_id+"') "
					           +"AND (DOCT_DRUG_PRESC_MASTER.COSTS >= 0)  order by PRESC_DATE";
			String sql=ServerDao.getQueryCustom(tableName, paramArray1, customWhere);
			putAsyncTask(new PrescriptionTask(fragment,sql).execute());
		}
	}
	
	/**
	 * 
	* @Title: putAsyncTask 
	* @Description: TODO(异步任务集合) 
	* @param @param paramAsyncTask
	* @param @return    设定文件 
	* @return AsyncTask    返回类型 
	* @throws
	 */
	protected AsyncTask putAsyncTask(AsyncTask paramAsyncTask){
		asyncTasks.add(paramAsyncTask);
		return paramAsyncTask;
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		for (int i = 0; i < asyncTasks.size(); i++) {
			AsyncTask localAsyncTask=(AsyncTask) asyncTasks.get(i);
			if (!(localAsyncTask == null) || (localAsyncTask.isCancelled())) {
				localAsyncTask.cancel(true);
			}
		}
		
		//另外一种方式
		/*Iterator localIterator=this.asyncTasks.iterator();
		if (!localIterator.hasNext()) {
			return;
		}
		AsyncTask localAsyncTask = (AsyncTask)localIterator.next();
		if ((localAsyncTask == null) || (localAsyncTask.isCancelled()))
	        continue;
	    localAsyncTask.cancel(true);*/
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			logout();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
	// 注销系统
	private void logout() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// .setIcon(R.);
			builder.setMessage("真的要退出系统吗？").setCancelable(false)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							finish();
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		}
	
	/*private void addCustomView() {
	// TODO Auto-generated method stub
	View mCustomView = getLayoutInflater().inflate(
			R.layout.app_logo, null);
	actionBar.setCustomView(mCustomView, new ActionBar.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	actionBar.setDisplayShowCustomEnabled(true);
	actionBar.setDisplayShowTitleEnabled(false);
}*/
	
	/*public void addBarSpinner(){
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	SpinnerAdapter adapter=ArrayAdapter.createFromResource(this, R.array.array1, android.R.layout.simple_spinner_dropdown_item);
	actionBar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
		
		@Override
		public boolean onNavigationItemSelected(int itemPosition, long itemId) {
			// TODO Auto-generated method stub
			return false;
		}
	});
}*/

}
