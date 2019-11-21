package com.example.lssdemo;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.UserManager;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Process;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import android.app.ActivityManager;
import com.example.lssdemo.R;
import android.os.UserHandle;
import static android.app.admin.DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE;
import static android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME;

public class MainActivity extends Activity implements View.OnClickListener{

   UserManager userManager;
   ActivityManager activityManager;
   int FLAG_MANAGED_PROFILE = 0x00000020;
   TextView textView;
   Button button, button2, button3, button4, button5;

   String name;
   private static final int REQUEST_PROVISION_MANAGED_PROFILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManager = (UserManager) getSystemService(Context.USER_SERVICE);
	activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        textView = (TextView) findViewById(R.id.id_tv);
        button = (Button) findViewById(R.id.id_btn);
        button2 = (Button) findViewById(R.id.id_btn2);
        button3 = (Button) findViewById(R.id.id_btn3);
        button4 = (Button) findViewById(R.id.id_btn4);
        button5 = (Button) findViewById(R.id.id_btn5);


	button.setOnClickListener(MainActivity.this);
        button2.setOnClickListener(MainActivity.this);
        button3.setOnClickListener(MainActivity.this);
        button4.setOnClickListener(MainActivity.this);
        button5.setOnClickListener(MainActivity.this);





    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_btn:
                if (userManager != null){
                   name = userManager.getUserName();
		   boolean guest = userManager.isGuestUser();
                   textView.setText("当前用户名为：" + name + "  AAA是否访客模式:" + guest );
		
		   //UserInfo userInfo = userManager.createProfileForUser("lishui1", UserInfo.FLAG_MANAGED_PROFILE , UserHandle.myUserId());
		   UserInfo userInfo = userManager.createProfileForUser("lishui1", UserInfo.FLAG_MANAGED_PROFILE , Process.myUserHandle().getIdentifier());

		   //Log.i("GGGG", "userInfo id:" + userInfo.id);
		   //Log.i("GGGG", "userInfo name:" + userInfo.name);


		   //Log.i("AAAAAA", "UserHandle.myUserId():" + UserHandle.myUserId());
		   //Log.i("AAAAAA", "userInfo:" + userInfo);
		   if(userInfo != null){
		   	Log.i("AAAAAA", "is mp1:" + userInfo.isManagedProfile());

		   }
               }else {
                   textView.setText("err");
               }
                break;
            case R.id.id_btn2:
		//userManager.createUser("li", FLAG_MANAGED_PROFILE);
	//	userManager.createUser("li", 3);
		UserInfo userInfo2 = userManager.createProfileForUser("lishui2", UserInfo.FLAG_MANAGED_PROFILE , UserHandle.myUserId());
                Toast.makeText(MainActivity.this, "userInfo2:" + userInfo2, Toast.LENGTH_LONG).show();
                break;
            case R.id.id_btn3:
		userManager.Del_user(15);
                break;
            case R.id.id_btn4:
                userManager.Switch(18);
               Toast.makeText(MainActivity.this, "switch", Toast.LENGTH_LONG).show();
                break;
            case R.id.id_btn5:
		int count = userManager.getUserCount();
                String name1 = userManager.getUserName();
	//	UserInfo userinfo = userManager.getUserInfo(10);
	//	boolean ism = userinfo.isManagedProfile();
                textView.setText("当前用户数为：" + count + "  count:" + count + "   name:" + name1);

		int max = userManager.getMaxSupportedUsers();
		Log.i("EEEEE", "max:" + max);

		userManager.setQuietModeEnabled(10, true);
		userManager.trySetQuietModeDisabled(10, null);

	//	provisionManagedProfile();
                break;
        }
    }

    private void provisionManagedProfile() {
	Log.i("AAAA","provisionManagedProfile");
        Intent intent = new Intent(ACTION_PROVISION_MANAGED_PROFILE);
        intent.putExtra(EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME,
                        getApplicationContext().getPackageName());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_PROVISION_MANAGED_PROFILE);
            finish();
	Log.i("AAAA","provisionManagedProfile finish");
        } else {
            Toast.makeText(this, "Device provisioning is not enabled. Stopping.",
                           Toast.LENGTH_SHORT).show();
        }   
    }   

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PROVISION_MANAGED_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Provisioning done.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Provisioning failed.", Toast.LENGTH_SHORT).show();
            }   
            return;
        }   
        super.onActivityResult(requestCode, resultCode, data);
    }   

}

