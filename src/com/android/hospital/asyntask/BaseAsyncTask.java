package com.android.hospital.asyntask;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
/**
 * 
* @ClassName: BaseAsyncTask 
* @Description: TODO(�������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 ����8:51:33 
*
 */
public abstract class BaseAsyncTask extends AsyncTask{

	public BaseAsyncTask(){
		
	}
	
	public BaseAsyncTask(Context context){
		
	}
	
	public BaseAsyncTask(Fragment fragment){
		
	}
	public BaseAsyncTask(Activity activity,String sql){
		
	}
	
	/**
	 * 
	* @ClassName: AsyncTaskCallback 
	* @Description: TODO(�첽����ص��ӿ�) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-18 ����8:59:56 
	* 
	* @param <T>
	 */
	public interface AsyncTaskCallback<T> {
		/**
		 * 
		* @Title: dataLoaded 
		* @Description: TODO(�ص�����) 
		* @param @param values    �趨�ļ� 
		* @return void    �������� 
		* @throws
		 */
		void getSingle(T value);
		void getList(ArrayList<T> values);
	}
}
