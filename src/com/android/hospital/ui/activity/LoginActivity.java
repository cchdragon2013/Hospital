package com.android.hospital.ui.activity;



import com.android.hospital.HospitalApp;
import com.android.hospital.asyntask.UpdateDBTask;
import com.android.hospital.asyntask.add.DrugOrNonDrugTask;
import com.android.hospital.service.MyService;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;
import com.android.hospital.widgets.MyProssDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
/**
 * 
* @ClassName: LoginActivity 
* @Description: TODO(登录界面) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:37:58 
*
 */
public class LoginActivity extends Activity implements OnClickListener{

	private SharedPreferences sp;
	private EditText mUserEditText,mPwdEditText;
	private CheckBox mCheckBox;
	private Button mOkBut,mCancleBut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		sp=getSharedPreferences("UserInfo", 0);
		initView();
		isAutoLogin();
		/*Intent intent=new Intent();
		intent.setClass(this, MyService.class);
		startService(intent);*/
		
		new UpdateDBTask(this).execute();//更新任务
	}

	
    /**
     * 
    * @Title: initView 
    * @Description: TODO(初始化控件) 
    * @param     设定文件 
    * @return void    返回类型 
    * @throws
     */
	private void initView() {
		mUserEditText=(EditText) findViewById(R.id.login_username);
		mPwdEditText=(EditText) findViewById(R.id.login_password);
		mCheckBox=(CheckBox) findViewById(R.id.login_checkBox1);
		mOkBut=(Button) findViewById(R.id.login_ok);
		mCancleBut=(Button) findViewById(R.id.login_cancel);
		
		mOkBut.setOnClickListener(this);
		mCancleBut.setOnClickListener(this);
		mCheckBox.setOnCheckedChangeListener(new MyCheckedChangeListener());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_ok:
			String username=mUserEditText.getText().toString();
			String pwd=mPwdEditText.getText().toString();
			if (username.equals("")||pwd.equals("")) {
				Toast.makeText(getApplicationContext(), "用户名或密码不能为空!", Toast.LENGTH_SHORT).show();
			}else {
				new LoginTask().execute();
			}
			break;
		case R.id.login_cancel:
			finish();
			break;

		}
	}
	
	//记住密码监听事件内部类
    private class MyCheckedChangeListener implements OnCheckedChangeListener{

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				sp=getSharedPreferences("UserInfo", 0);
				sp.edit().putBoolean("ischoose", isChecked).commit();
				
			}
			
	}
	/**
	 * 
	* @Title: login 
	* @Description: TODO(登录初始化) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void login(){	
		if (mCheckBox.isChecked()) {
			sp=getSharedPreferences("UserInfo", Context.MODE_WORLD_WRITEABLE|Context.MODE_WORLD_READABLE); 
			sp.edit().putString("username", mUserEditText.getText().toString()).commit();
			sp.edit().putString("password", mPwdEditText.getText().toString()).commit();		
		}
		
	}
	
	/**
	 * 
	* @Title: isAutoLogin 
	* @Description: TODO(是否记住密码) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void isAutoLogin(){
		boolean flag=sp.getBoolean("ischoose", false);
		if (flag) {
			String username=sp.getString("username", "");
			String pwd=sp.getString("password", "");
			mUserEditText.setText(username);
			mPwdEditText.setText(pwd);
			mCheckBox.setChecked(flag);
		}
	}
	
	/**
	 * 
	* @ClassName: LoginTask 
	* @Description: TODO(登录任务) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-18 下午11:06:46 
	*
	 */
	private class LoginTask extends AsyncTask<Void, Void, String>{
		
		private MyProssDialog mDialog;
		private HospitalApp app;
		
		@Override
		protected void onPreExecute() {
            mDialog=new MyProssDialog(LoginActivity.this, "登录", "正在登录，请稍候...");
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String username=mUserEditText.getText().toString().trim().toUpperCase();
			String pwd=mPwdEditText.getText().toString().trim().toUpperCase();
			String result=WebServiceHelper.getUserName(username, pwd);
			app=(HospitalApp) getApplication();
			app.setLoginName(username);
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			mDialog.cancel();
			if (null==result||result.equals("空")) {
				Toast.makeText(getApplicationContext(), "登录失败，用户名或密码错误!", Toast.LENGTH_SHORT).show();
			}else {
				String[] strArr=result.split("<;>");
				app.setDoctor(strArr[0]);//设置医生名字
				login();
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
}
