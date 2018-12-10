package cn.langreal.john.testmtklog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

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

public class MtkLogListActivity extends AppCompatActivity {

    //RecyclerView mRecycler;
    @BindView(R.id.RecycleViewList)
    RecyclerView mRecycler;
    private MTKLogCaseAdapter mAdapter;


  JSONArray caseArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtk_log_list);

        //  mRecycler = (RecyclerView)findViewById(R.id.RecycleViewList);
        // Get the status bar colors to interpolate between
        ButterKnife.bind(this);
        initRecycler();

    }


    private void initRecycler(){
        mAdapter = new MTKLogCaseAdapter();
        mAdapter.addAll(getData());
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<caseResultModel>() {
            @Override
            public void onItemClick(View view, caseResultModel caseResult, int i) {
               //   Launch the slidable activity
                Intent viewer = new Intent(MtkLogListActivity.this, MtkLogDetailActivity.class);
               viewer.putExtra(MtkLogDetailActivity.EXTRA_RESULT, caseResult);
                startActivity(viewer);
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

