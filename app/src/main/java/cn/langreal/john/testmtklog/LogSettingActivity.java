package cn.langreal.john.testmtklog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dd.CircularProgressButton;

public class LogSettingActivity extends AppCompatActivity {
  Button btnSuccess;
  Button btnFailed;
  String  caseName = "";
  EditText editTextCaseName;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log_setting);
    btnSuccess = (Button)findViewById(R.id.buttonCaseSuccess);
    btnFailed = (Button)findViewById(R.id.buttonCaseFailed);
    editTextCaseName = (EditText)findViewById(R.id.editTextCaseName);
   //获得上个Activity传递的intent
    Bundle bundle = this.getIntent().getExtras();
    caseName=bundle.getString("caseName");
   // caseName=this.getIntent().getStringExtra("caseName");
    editTextCaseName.setText(caseName);
    btnSuccess.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("success", "success");
        String caseName = editTextCaseName.getText().toString();;
        intent.putExtra("caseName",caseName );
        //设置返回数据
        LogSettingActivity.this.setResult(RESULT_OK, intent);
        //关闭Activity
        LogSettingActivity.this.finish();
      }
    });
    btnFailed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("success", "failed");
        String caseName = editTextCaseName.getText().toString();;
        intent.putExtra("caseName",caseName );
        //设置返回数据
        LogSettingActivity.this.setResult(RESULT_OK, intent);
        //关闭Activity
        LogSettingActivity.this.finish();
      }
    });

  }


}
