package com.android.hospital.ui.fragment;

import android.app.ListFragment;
import android.os.Bundle;

public class AbstratListFragment extends ListFragment{

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		getListView().setFastScrollEnabled(true);
		getListView().setSelection(getListAdapter().getCount()-1);
	}
}
