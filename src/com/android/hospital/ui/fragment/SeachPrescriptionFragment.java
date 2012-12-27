package com.android.hospital.ui.fragment;

import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.PreMiddleDrugAdapter;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.ui.activity.R;

import android.os.Bundle;
import android.widget.BaseAdapter;

public class SeachPrescriptionFragment extends SearchFragment{

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		show(null);//��ҩ
	}
	
	/**
	 * Ĭ����ʾ��ҩ����
	 */
	@Override
	public void show(BaseAdapter adapter) {
		// TODO Auto-generated method stub
	    ArrayList<DrugEntity> arrayList=getApp().getMiddleDrugList();
		PreMiddleDrugAdapter preAdapter = new PreMiddleDrugAdapter(
				getActivity(), R.layout.fragment_add_prescription_list_item,
				arrayList);
		getListView().setAdapter(preAdapter);
	}
	/**
	 * 
	* @Title: showWestDrug 
	* @Description: TODO(��ʾ��ҩ) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void showWestDrug(ArrayList<DrugEntity> arrayList){
		PreMiddleDrugAdapter preAdapter = new PreMiddleDrugAdapter(
				getActivity(), R.layout.fragment_add_prescription_list_item,
				arrayList);
		getListView().setAdapter(preAdapter);
	}
}
