package cn.langreal.john.testmtklog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.langreal.john.testmtklog.model.caseResultModel;


public class FragmentLogList extends Fragment {




  @BindView(R.id.recViewList)
  RecyclerView mRecycler;
  private MTKLogCaseAdapter mAdapter;


  JSONArray caseArray;


  public FragmentLogList() {
    // Required empty public constructor

  }



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_log_list, container, false);

    // TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
    //tv.setText(getArguments().getString("msg"));
    ButterKnife.bind(this, v);
    initRecycler();
    return v;
  }

  public static FragmentLogList newInstance(String text) {

    FragmentLogList f = new FragmentLogList();
    Bundle b = new Bundle();
    b.putString("msg", text);

    f.setArguments(b);

    return f;
  }



  private void initRecycler(){
    mAdapter = new MTKLogCaseAdapter();
    mAdapter.addAll(getData());
    mRecycler.setAdapter(mAdapter);
    mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

    mAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<caseResultModel>() {
      @Override
      public void onItemClick(View view, caseResultModel caseResult, int i) {
        //   Launch the slidable activity
        //Intent viewer = new Intent(MtkLogDetailActivity.this, MtkLogDetailActivity.class);
        Intent intent = new Intent(getActivity().getApplicationContext(),MtkLogDetailActivity.class);
        intent.putExtra(MtkLogDetailActivity.EXTRA_RESULT, caseResult);
       startActivity(intent);


      };
    });


  }
  private List<caseResultModel> getData(){
    // InputStream is = getResources().openRawResource(R.raw.tt1);
    //  InputStreamReader isr = new InputStreamReader(is);
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

      String strList = caseArray.toString();

      Gson gson = new Gson();
      Type listType = new TypeToken<List<caseResultModel>>(){}.getType();
      List<caseResultModel> caseList = gson.fromJson(strList, listType);
      return caseList;
    }
    catch (JSONException e) {

      // TODO Auto-generated catch block

      e.printStackTrace();



    }
    return null;
  }



}
