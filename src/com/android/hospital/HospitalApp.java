package com.android.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.InspectionItemEntity;
import com.android.hospital.entity.NonDrugEntity;
import com.android.hospital.entity.PatientEntity;

import android.app.Application;
/**
 * 
* @ClassName: HospitalApp 
* @Description: 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 上午8:57:34 
*
 */
public class HospitalApp extends Application{

	private PatientEntity patientEntity;//单个病人实体
	private ArrayList<DrugEntity> drugList;//药品集合
	private ArrayList<DrugEntity> middleDrugList;//处方中药房药品集合
	private ArrayList<NonDrugEntity> nondrugList;//非药品集合
	private String maxNumber;//最大序号，每次点击新增按钮，值被改变
	private List<Map<String, String>> freqList;//途径集合
	private List<Map<String, String>> wayList;//频次集合
	private List<Map<String, String>> classList;//检查类别集合
	private List<Map<String, String>> deptList;//检查科室集合
	private List<Map<String, String>> inspecitonClassList;//检验类别集合
	private List<Map<String, String>> inspectionDeptList;//检验科室集合
	private String loginName="";//登录账号
	private String doctor="";//医生名
	private ArrayList<InspectionItemEntity> inspectionItemList;//检验项目集合
	private String nextval="";//触发器
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public PatientEntity getPatientEntity() {
		return patientEntity;
	}

	public void setPatientEntity(PatientEntity patientEntity) {
		this.patientEntity = patientEntity;
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public ArrayList<DrugEntity> getDrugList() {
		return drugList;
	}

	public void setDrugList(ArrayList<DrugEntity> drugList) {
		this.drugList = drugList;
	}

	public ArrayList<NonDrugEntity> getNondrugList() {
		return nondrugList;
	}

	public void setNondrugList(ArrayList<NonDrugEntity> nondrugList) {
		this.nondrugList = nondrugList;
	}

	public List<Map<String, String>> getFreqList() {
		return freqList;
	}

	public void setFreqList(List<Map<String, String>> freqList) {
		this.freqList = freqList;
	}

	public List<Map<String, String>> getWayList() {
		return wayList;
	}

	public void setWayList(List<Map<String, String>> wayList) {
		this.wayList = wayList;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(String maxNumber) {
		this.maxNumber = maxNumber;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public List<Map<String, String>> getClassList() {
		return classList;
	}

	public void setClassList(List<Map<String, String>> classList) {
		this.classList = classList;
	}

	public List<Map<String, String>> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Map<String, String>> deptList) {
		this.deptList = deptList;
	}

	public List<Map<String, String>> getInspecitonClassList() {
		return inspecitonClassList;
	}

	public void setInspecitonClassList(List<Map<String, String>> inspecitonClassList) {
		this.inspecitonClassList = inspecitonClassList;
	}

	public List<Map<String, String>> getInspectionDeptList() {
		return inspectionDeptList;
	}

	public void setInspectionDeptList(List<Map<String, String>> inspectionDeptList) {
		this.inspectionDeptList = inspectionDeptList;
	}

	public ArrayList<InspectionItemEntity> getInspectionItemList() {
		return inspectionItemList;
	}

	public void setInspectionItemList(
			ArrayList<InspectionItemEntity> inspectionItemList) {
		this.inspectionItemList = inspectionItemList;
	}

	public ArrayList<DrugEntity> getMiddleDrugList() {
		return middleDrugList;
	}

	public void setMiddleDrugList(ArrayList<DrugEntity> middleDrugList) {
		this.middleDrugList = middleDrugList;
	}

	public String getNextval() {
		return nextval;
	}

	public void setNextval(String nextval) {
		this.nextval = nextval;
	}

	

}
