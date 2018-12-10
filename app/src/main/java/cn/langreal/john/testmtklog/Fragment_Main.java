package cn.langreal.john.testmtklog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;


public class Fragment_Main extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
  FButton btnOppenLog;


  String strIMEI;
  String strVersion;
  String  strMsg;
  JSONArray caseArray;
  int        caseIndex;
  String caseName = "";
  String caseResult = "";
  boolean bLogOpened = false;
  TextView tvStatus;

  TextView tvSubVersion;
  View decorView;


  float downX, downY;

  float screenWidth, screenHeight;

  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
  //获取当前时间

  String startTimeInfo = "";
  String stopTimeInfo = "";
  public Fragment_Main() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment Fragment_Main.
   */
  // TODO: Rename and change types and number of parameters
  public static Fragment_Main newInstance(String param1, String param2) {
    Fragment_Main fragment = new Fragment_Main();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment__main, container, false);
    btnOppenLog = (FButton)v.findViewById(R.id.ButtonOpenLog);
    tvStatus =  (TextView) v.findViewById(R.id.textViewStatus);
    tvSubVersion = (TextView) v.findViewById(R.id.textViewSubVersion);
    int newcolor = getActivity().getResources().getColor(R.color.fbutton_color_normal);
    btnOppenLog.setButtonColor(newcolor);
    // btnCloseLog = (Button)findViewById(R.id.buttonCloseLog);
    btnOppenLog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!bLogOpened) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("启动MTKLog")
                        .setContentText("将要启动MTKLog功能")
                        .setCancelText("不，不要")
                        .setConfirmText("好的，启动")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need

                                sDialog.cancel();

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                StartMTKLogger(GiantsConstans.context,1);
                                sDialog.setTitleText("启动Log")
                                        .setContentText("Logg功能已启动!")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener(){
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog){
                                                sDialog.dismiss();

                                            }
                                        })
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                tvStatus.setText("关闭MTKLog功能");
                                btnOppenLog.setText("停止Log");
                               // btnOppenLog.setBackgroundResource(R.drawable.red_button_background);
                              int newcolor = getActivity().getResources().getColor(R.color.fbutton_color_stop);

                              btnOppenLog.setButtonColor(newcolor);

                            }
                        })
                        .show();
            }
            else {


                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("停止MTKLog")
                        .setContentText("将要停止MTKLog功能")
                        .setCancelText("不，不要")
                        .setConfirmText("好的，停止")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need

                                sDialog.cancel();

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("停止Log")
                                        .setContentText("Logg功能已停止!")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismiss();
                                                StopMTKLogger(GiantsConstans.context);
                                            }
                                        })
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                tvStatus.setText("启动MTKLog功能");
                                btnOppenLog.setText("打开Log");
                               // btnOppenLog.setBackgroundResource(R.drawable.blue_button_background);
                              int newcolor = getActivity().getResources().getColor(R.color.fbutton_color_normal);
                              btnOppenLog.setButtonColor(newcolor);
                            }
                        })
                        .show();
            }
        }
    });
    caseIndex = 0;


    btnOppenLog.setVisibility(View.VISIBLE);

    if(!bLogOpened)
    {
      tvStatus.setText("启动MTKLog功能");
      btnOppenLog.setText("打开Log");
    }
    else
    {
      tvStatus.setText("关闭MTKLog功能");
      btnOppenLog.setText("停止Log");
    }
      // 获得decorView
      decorView = getActivity().getWindow().getDecorView();

      // 获得手机屏幕的宽度和高度，单位像素
      DisplayMetrics metrics = new DisplayMetrics();
      getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
      screenWidth = metrics.widthPixels;
      screenHeight = metrics.heightPixels;
      tvSubVersion.setText(GiantsConstans.VERSIONSTR);


    return v;
  }


  @Override
  public void onResume() {
    super.onResume();
    decorView.setX(0);
  }




  /**
   * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
   *
   * requestCode 请求码，即调用startActivityForResult()传递过去的值
   * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
   */
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      caseName = data.getExtras().getString("caseName");//得到新Activity 关闭后返回的数据
    caseResult = data.getExtras().getString("success");//得到新Activity 关闭后返回的数据
    RenameMTKLogger();
    SaveJasonFile();
    btnOppenLog.setVisibility(View.VISIBLE);

  }




  public  void StartMTKLogger(Context ctx, int target)

  {

    try

    {

      Intent i = new Intent();

      i.setAction("com.mediatak.mtklogger.ADB_CMD");

      i.putExtra("cmd_name","modem_auto_reset_1");

      i.putExtra("cmd_target",2);

      i.setPackage("cmd_mediatak.mtklogger");

      ctx.sendBroadcast(i);
      //Global.isStoppedLogger = false;

      Intent intent =  new Intent();
      intent .setAction("com.mediatak.mtklogger.ADB_CMD");

      intent .putExtra("cmd_name","start");

      intent.putExtra("cmd_target",target);

      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      intent.setPackage("cmd_mediatak.mtklogger");

      ctx.sendBroadcast(intent);
      Log.i("testMTKLog", "send command  start MTK logger with target" + 2 );
      Date date = new Date(System.currentTimeMillis());
      startTimeInfo = simpleDateFormat.format(date);
      //  btnOppenLog.setVisibility(View.INVISIBLE);
      //btnCloseLog.setVisibility(View.VISIBLE);
      bLogOpened = true;

    }

    catch(Exception e)

    {
      Log.i("testMTKLog", "start MTK logger with target" + 2 +"get exception"+e.getMessage());


    }

  }
  public  void StopMTKLogger(Context ctx)

  {

    try

    {



      Intent intent =  new Intent();
      intent .setAction("com.mediatak.mtklogger.ADB_CMD");

      intent .putExtra("cmd_name","stop");

      intent.putExtra("cmd_target",1);

      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      intent.setPackage("cmd_mediatak.mtklogger");

      ctx.sendBroadcast(intent);
      Log.i("testMTKLog", "send command  stop MTK logger with target" + 2 );
      Date date = new Date(System.currentTimeMillis());
      stopTimeInfo = simpleDateFormat.format(date);


      // btnCloseLog.setVisibility(View.INVISIBLE);
      //先读取jasonfile
      ReadJasonFile();
      //启动新activity,获取用户输入成功或失败
      Intent judTntent=new Intent(getActivity(),LogSettingActivity.class);
      Bundle bundle = new Bundle();

      bundle.putString("caseName", "case_"+Integer.toString(caseIndex));
      judTntent.putExtras(bundle);
      // judTntent.putExtra("caseName", "case_"+Integer.toString(caseIndex));
      startActivityForResult(judTntent,1);
      bLogOpened = false;
    }

    catch(Exception e)

    {

      Log.i("testMTKLog", "start MTK logger with target" + 2 +"get exception"+e.getMessage());

    }

  }
  public  void RenameMTKLogger()

  {

    try

    {

      String newFolderName = "mtklog_"+Integer.toString(caseIndex);

      File path = Environment.getExternalStorageDirectory();
      File mtkPath = new File(path+ "/mtklog");
      File  newFolder = new File(path+ "/qtlogs");
      if(!newFolder.exists())
      {
        newFolder.mkdirs();
      }

      File mtkNewPath = new File(path+ "/qtlogs"+"/"+ newFolderName);
      mtkPath.renameTo(mtkNewPath);
      Toast.makeText(getActivity(), "mtk log已另存为"+newFolderName,
              Toast.LENGTH_LONG).show();
      Log.i("testMTKLog", "rename mtk logger folder to" +newFolderName );
    }

    catch(Exception e)

    {

      Log.i("testMTKLog", "start MTK logger with target" + 2 +"get exception"+e.getMessage());

    }

  }


  public   boolean  SaveJasonFile()
  {


    try {
      JSONObject object = new JSONObject();//创建一个总的对象，这个对象对整个json串

      strIMEI = Basic_GetDeviceIMEI();
      strVersion = GiantsConstans.VERSIONSTR;
      object.put("imei", strIMEI);
      object.put("version", strVersion);
      object.put("model", "mtk one");

      JSONObject caseObject = new JSONObject();//case objects;
      caseObject.put("caseName", caseName);
      caseObject.put("caseId", Integer.toString(caseIndex));
      caseObject.put("instanceId", startTimeInfo);
      caseObject.put("status", caseResult);
      caseObject.put("startTime", startTimeInfo);
      caseObject.put("endTime", stopTimeInfo);
      File qtlogpath = Environment.getExternalStorageDirectory();
      String  strQTlogPath = qtlogpath.toString()+ "/qtlogs";
      caseObject.put("qtlog", strQTlogPath);
      caseObject.put("mtklog", strQTlogPath+"/mtklog_"+Integer.toString(caseIndex));

      caseArray.put(caseObject);
      object.put("caseresults",caseArray);
      String strContent = object.toString()+ "\n";
      FileUtil.writeFilesToExternalStorage(GiantsConstans.JASON_CASEFILE_NAME,strContent);

      return true;
    }
    catch (JSONException e) {

      // TODO Auto-generated catch block

      e.printStackTrace();

      strMsg = "JSON error:"+ e.getMessage();

    }
    return false;
  }

  public   boolean  ReadJasonFile()
  {
    try {
      caseArray = null;
      caseArray = new JSONArray();
      String strContent = FileUtil.readFilesFromExternalStorage(GiantsConstans.JASON_CASEFILE_NAME);
      JSONObject object = new JSONObject(strContent);//总的对象


      //当你获得Array类型的时候，然后要遍历获取代表里面面每一个个Object
      JSONArray caseResults = object.getJSONArray("caseresults");

      for (int i = 0; i < caseResults.length(); i++) {
        JSONObject caseResult = caseResults.getJSONObject(i);
        String mtkLog = caseResult.getString("mtklog");
        caseArray.put(caseResult);
      }

      caseIndex = caseResults.length()+1;

      return true;
    }
    catch (JSONException e) {

      // TODO Auto-generated catch block

      e.printStackTrace();

      strMsg = "JSON error:"+ e.getMessage();

    }
    return false;
  }
  public String   Basic_GetDeviceIMEI ()
  {

    String imei = ((TelephonyManager) GiantsConstans.context
            .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    return imei;
  }
}
