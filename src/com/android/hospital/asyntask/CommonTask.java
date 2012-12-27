package com.android.hospital.asyntask;

import java.util.ArrayList;

import android.view.View;

/**
 * 
* @ClassName: CommonFragmentTask 
* @Description: TODO(���е�) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����3:10:23 
*
 */
public interface CommonTask<T> {

	/**
	* @Title: getValues 
	* @Description: TODO(�õ�����) 
	 */
	void getValues(DataLoadedCallback<T> callback);
	
	/**
	 * 
	* @Title: SendValues 
	* @Description: TODO(��������) 
	 */
	void SendValues(DataLoadedCallback<T> callback);
	
	/** 
	* @Title: isLoading 
	* @Description: TODO(���ڼ���) 
	 */
	boolean isLoading();
	
	
	/**
	 * 
	* @ClassName: DataLoadedCallback 
	* @Description: TODO(�ص��ӿ�) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-14 ����3:13:19 
	* 
	* @param <T>
	 */
	interface DataLoadedCallback<T> {

        void dataLoaded(ArrayList<T> values);
        void showLoading(boolean bool);
        void showReloading(boolean bool);
    }
}
