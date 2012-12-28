package com.android.hospital.asyntask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.hospital.HospitalApp;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.InspectionItemEntity;
import com.android.hospital.entity.NonDrugEntity;
import com.android.hospital.ui.activity.LoginActivity;
import com.android.hospital.webservice.WebServiceHelper;
import com.android.hospital.widgets.MyProssDialog;

import android.app.Activity;
import android.widget.Toast;

/**
 * 
* @ClassName: UpdateDBTask 
* @Description: TODO(登录更新任务) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-28 上午11:26:25 
*
 */
public class UpdateDBTask extends BaseAsyncTask{

	private LoginActivity mActivity;
	private HospitalApp app;
	private ArrayList<DrugEntity> drugList;//药品集合
	private ArrayList<NonDrugEntity> nondrugList;//非药品集合
	private List<Map<String, String>> wayList;//途径集合
	private List<Map<String, String>> freqList; //频次集合
	private List<Map<String, String>> classList;//检查类别
	private List<Map<String, String>> deptList;//发往科室
	private List<Map<String, String>> inspectionDeptList;//检验科室
	private List<Map<String, String>> inspectionClassList;//检验类别
	private ArrayList<InspectionItemEntity> inspectionItemList;//检验项目集合
	private MyProssDialog mDialog;
	
	
	public UpdateDBTask(Activity activity){
		this.mActivity=(LoginActivity) activity;
		this.app=(HospitalApp) mActivity.getApplication();
	}
		
	@Override
	protected void onPreExecute() {
        mDialog=new MyProssDialog(mActivity, "更新", "正在更新数据库，请稍候...");
	}

	
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		startDrugOrNonDrugTask();
		startFreqAndWayTask();
		startClassAndDeptTask();
		startInspectionDeptTask();
		startInspectionItemTask();
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		mDialog.cancel();
		Toast.makeText(mActivity, "更新成功!", Toast.LENGTH_SHORT).show();
		endDrugOrNonDrug();
		endFreqAndTask();
		endClassAndDeptTask();
		endInspectionDeptTask();
		endInspectionItemTask();
	}
	
	
	/**
	 * 
	* @Title: startDrugOrNonDrug 
	* @Description: TODO(获取药品非药品任务) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void startDrugOrNonDrugTask(){
		String drugSql = "select * from input_drug_lists where storage in('3103','3102')";
		String nondrugSql="select * from v_clinic_name_dict";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(drugSql);
		drugList=DrugEntity.init(dataList);
		ArrayList<DataEntity> dataList2=WebServiceHelper.getWebServiceData(nondrugSql);
		nondrugList=NonDrugEntity.init(dataList2);
	}	
	private void endDrugOrNonDrug(){
		if (drugList.size()==0||nondrugList.size()==0) {
			Toast.makeText(mActivity, "获取数据失败!", Toast.LENGTH_SHORT).show();
		}else {	
			app.setDrugList(drugList);
			app.setNondrugList(nondrugList);
			setPrescriptionMiddleDrug();
		}
	}	
	/**
     * 
    * @Title: setPrescriptionMiddleDrug 
    * @Description: TODO(设置处方中药房药品集合) 
    * @param     设定文件 
    * @return void    返回类型 
    * @throws
     */
	private void setPrescriptionMiddleDrug() {
		// TODO Auto-generated method stub
		ArrayList<DrugEntity> middLeDrugList=new ArrayList<DrugEntity>();
		int size=drugList.size();
		for (int i = 0; i < size; i++) {
			if ("中药房".equals(drugList.get(i).storage_name)) {
				middLeDrugList.add(drugList.get(i));
			}
		}
		app.setMiddleDrugList(middLeDrugList);
	}
	
	/**
	 * 
	* @Title: startFreqAndWayTask 
	* @Description: TODO(获取途径，频次任务) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void startFreqAndWayTask(){
		String freqSql = "select serial_no,freq_desc,freq_counter,freq_interval,freq_interval_units from perform_freq_dict";
		String waySql = "select administration_name,serial_no,administration_code,input_code from administration_dict where inp_outp_flag ='1' or inp_outp_flag is null order by  serial_no";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(waySql);
		wayList=new ArrayList<Map<String,String>>();
		Map<String, String> mapNull=new HashMap<String, String>();
		mapNull.put("administration_name", "");
		wayList.add(mapNull);
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("administration_name", dataList.get(i).get("administration_name"));
			wayList.add(map);
		}
		ArrayList<DataEntity> dataList2=WebServiceHelper.getWebServiceData(freqSql);
		freqList=new ArrayList<Map<String,String>>();
		Map<String, String> mapNull2=new HashMap<String, String>();
		mapNull2.put("freq_desc", "");
		mapNull2.put("freq_counter", "");
		mapNull2.put("freq_interval", "");
		mapNull2.put("freq_interval_unit", "");
		freqList.add(mapNull2);
		for (int i = 0; i < dataList2.size(); i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("freq_desc", dataList2.get(i).get("freq_desc").trim());
			map.put("freq_counter", dataList2.get(i).get("freq_counter").trim());
			map.put("freq_interval", dataList2.get(i).get("freq_interval").trim());
			map.put("freq_interval_unit", dataList2.get(i).get("freq_interval_unit").trim());
			freqList.add(map);
		}	
	}
	
	private void endFreqAndTask(){
		app.setFreqList(freqList);
		app.setWayList(wayList);
	}
	
	/**
	 * 
	* @Title: startClassAndDeptTask 
	* @Description: TODO(获取检查类别和科室) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void startClassAndDeptTask(){
		String classSql="select exam_class_code,exam_class_name,serial_no,perform_by from exam_class_dict";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(classSql);
		classList=new ArrayList<Map<String,String>>();
		int size=dataList.size();
		for (int i = 0; i < size; i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("exam_class_name", dataList.get(i).get("exam_class_name").trim());
			classList.add(map);
		}
		String deptSql="select dept_name,dept_code from dept_dict where clinic_attr='1'";
		ArrayList<DataEntity> dataList2=WebServiceHelper.getWebServiceData(deptSql);
		deptList=new ArrayList<Map<String,String>>();
		int size2=dataList2.size();
		for (int i = 0; i < size2; i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("dept_name", dataList2.get(i).get("dept_name").trim());
			map.put("dept_code", dataList2.get(i).get("dept_code").trim());
			deptList.add(map);
		}
	}
	
	private void endClassAndDeptTask(){
		app.setClassList(classList);
		app.setDeptList(deptList);
	}
	
	/**
	 * 
	* @Title: startInspectionDeptTask 
	* @Description: TODO(获取检验科室和类别) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void startInspectionDeptTask(){
		String deptSql = "select distinct  lab_sheet_master.performed_by, dept_dict.dept_name  "
				+ "from dept_dict,  lab_sheet_master   "
				+ "where ( dept_dict.dept_code = lab_sheet_master.performed_by )";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(deptSql);
		inspectionDeptList=new ArrayList<Map<String,String>>();
		int size=dataList.size();
		Map<String, String> mapNull=new HashMap<String, String>();
		mapNull.put("dept_name", "");
		mapNull.put("performed_by", "");
		inspectionDeptList.add(mapNull);
		for (int i = 0; i < size; i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("dept_name", dataList.get(i).get("dept_name"));
			map.put("performed_by", dataList.get(i).get("performed_by"));
			inspectionDeptList.add(map);
		}
		String classSql="select serial_no,class_code,class_name  from lab_item_class_dict";
		ArrayList<DataEntity> dataList2=WebServiceHelper.getWebServiceData(classSql);
		inspectionClassList=new ArrayList<Map<String,String>>();
		int size2=dataList2.size();
		Map<String, String> mapNull2=new HashMap<String, String>();
		mapNull2.put("class_name", "");
		inspectionClassList.add(mapNull2);
		for (int i = 0; i < size2; i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("class_name", dataList2.get(i).get("class_name"));
			inspectionClassList.add(map);
		}
	}
	
	private void endInspectionDeptTask(){
		app.setInspecitonClassList(inspectionClassList);
		app.setInspectionDeptList(inspectionDeptList);
	}
	
	/**
	 * 
	* @Title: putInspectionItem 
	* @Description: TODO(检验项目任务) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void startInspectionItemTask(){
		String sql="select * from v_clab_name_dict";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
		inspectionItemList=new ArrayList<InspectionItemEntity>();
		int size=dataList.size();
		for (int i = 0; i < size; i++) {
			InspectionItemEntity entity=new InspectionItemEntity();
			entity.item_class=dataList.get(i).get("item_class").trim();
			entity.item_name=dataList.get(i).get("item_name").trim();
			entity.item_code=dataList.get(i).get("item_code").trim();
			entity.std_indicator=dataList.get(i).get("std_indicator").trim();
			entity.input_code=dataList.get(i).get("input_code").trim();
			entity.input_code_wb=dataList.get(i).get("input_code_wb").trim();
			entity.performed_by=dataList.get(i).get("performed_by").trim();
			entity.expand1=dataList.get(i).get("expand1").trim();
			entity.expand2=dataList.get(i).get("expand2").trim();
			entity.expand3=dataList.get(i).get("expand3").trim();
			inspectionItemList.add(entity);
		}
	}
	
	private void endInspectionItemTask(){
		app.setInspectionItemList(inspectionItemList);
	}
}
