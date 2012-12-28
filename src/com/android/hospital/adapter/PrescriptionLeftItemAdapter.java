package com.android.hospital.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.android.hospital.HospitalApp;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.InspectionItemEntity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionLeftItemAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<DrugEntity> mList;
	private LayoutInflater mInflater;
	private HospitalApp app;
	private List<Map<String, String>> mFreqList;//频次list
	private List<Map<String, String>> mWayList;//途径list
	private ArrayAdapter<String> freqAdapter;
	private ArrayAdapter<String> wayAdapter;
	
	public PrescriptionLeftItemAdapter(Context context,ArrayList<DrugEntity> list){
		this.mContext=context;
		this.mList=list;
		this.mInflater=LayoutInflater.from(context);
		this.app=(HospitalApp) mContext.getApplicationContext();
		mFreqList=app.getFreqList();
		mWayList=app.getWayList();
		setSpinner();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList==null?0:mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void clear(){
		mList.clear();
		notifyDataSetChanged();
	}
	
	public void addItem(DrugEntity entity,int defaultFlag){
		if (mList==null) {
			mList=new ArrayList<DrugEntity>();			
		}
		int size=mList.size();
		for (int i = 0; i < size; i++) {
			if (entity.drug_name.equals(mList.get(i).drug_name)) {
				Toast.makeText(mContext, "该项已被添加!", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		mList.add(entity);
		notifyDataSetChanged();
	}
	
	public void setSpinner(){
		List<String> freqSpList=new ArrayList<String>();
		for (int i = 0; i < mFreqList.size(); i++) {
			String item=mFreqList.get(i).get("freq_desc");
			freqSpList.add(item);
		}
		freqAdapter=new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, freqSpList);
		freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		List<String> waySpList=new ArrayList<String>();
		for (int i = 0; i < mWayList.size(); i++) {
			String item=mWayList.get(i).get("administration_name");
			waySpList.add(item);
		}
		wayAdapter=new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, waySpList);
		wayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.fragment_add_prescription_left_item, null);
		}
		convertView.setTag(position);
		TextView tev1=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_1);
		TextView tev2=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_2);
		TextView tev3=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_3);
		EditText edit4=(EditText) convertView.findViewById(R.id.add_prescription_left_item_edit_4);
		TextView tev5=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_5);
		Spinner spinner6=(Spinner) convertView.findViewById(R.id.add_prescription_left_item_spinner_6);
		Spinner spinner7=(Spinner) convertView.findViewById(R.id.add_prescription_left_item_spinner_7);
		EditText edit8=(EditText) convertView.findViewById(R.id.add_prescription_left_item_edit_8);
		TextView tev9=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_9);
		TextView tev10=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_10);
		TextView tev11=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_11);		
		
		DrugEntity item=mList.get(position);
		int id=position+1;
		tev1.setText(String.valueOf(id));
		tev2.setText(item.drug_name);//药名
		tev3.setText(item.drug_spec);//规格
		tev5.setText(item.dose_units);//单位
		tev9.setText(item.package_units);
		tev10.setText(item.purchase_price);
		tev11.setText(item.purchase_price);
		if (spinner6.getAdapter()==null) {
			spinner6.setAdapter(wayAdapter);
		}
		if (spinner7.getAdapter()==null) {
			spinner7.setAdapter(freqAdapter);
		}				
		return convertView;
	}
}
