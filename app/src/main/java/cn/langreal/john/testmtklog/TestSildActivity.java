package cn.langreal.john.testmtklog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simple.transformslibrary.CardSlideTransformer;
import com.simple.transformslibrary.StackZoomOutTransform;
import com.simple.transformslibrary.TransformUtil;

import java.util.ArrayList;
import java.util.List;

public class TestSildActivity  extends AppCompatActivity {
  private ViewPager mViewPager;
  private ViewPager mViewPagerNew;
  private List<View> viewList = new ArrayList<>();
  private int[] colors = {Color.parseColor("#00BFFF"), Color.parseColor("#FF1493")
    , Color.parseColor("#8B0000"), Color.parseColor("#008B8B")
    , Color.parseColor("#8B008B")};
  private TextView mTextview;
  private int curIndex;


  private FragmentLogList Fragment2;
  private Fragment_Main Fragment1;
  private List<Fragment> mFragmentList = new ArrayList<Fragment>();
  private FragmentAdapter mFragmentAdapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_sild);
    getPermission();
    mViewPager = (ViewPager) findViewById(R.id.viewPager);

    GiantsConstans.context = this;
    for (int i = 0; i <3; i++) {
      curIndex = i;
      View rootView = View.inflate(TestSildActivity.this, R.layout.item_page, null);
      rootView.setBackgroundColor(colors[i]);
      TextView textView = (TextView) rootView.findViewById(R.id.pager_tv);
      textView.setText(String.valueOf(i + 1));
      textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Toast.makeText(TestSildActivity.this, ((TextView) v).getText(), Toast.LENGTH_SHORT)
            .show();
        }
      });
      viewList.add(i, rootView);
    }
    //  View newView = View.inflate(MainActivity.this, R.layout.layout_new, null);
    // viewList.add(1, newView);


    // View newView2 = View.inflate(MainActivity.this, R.layout.fragment_fragment2, null);
    //  viewList.add(2, newView2);
    //   TransformUtil.reverse(mViewPager, new FlipHorizontalTransformer());
    // TransformUtil.reverse(mViewPager, new Flip3DTransform());

    // TransformUtil.reverse(mViewPager, new CardSlideTransformer());
    // TransformUtil.reverse(mViewPager, new ZoomInTransform());
    // TransformUtil.reverse(mViewPager, new ZoomBothTransform());
    //  TransformUtil.reverse(mViewPager, new StackZoomInTransform());
    TransformUtil.reverse(mViewPager, new CardSlideTransformer());
    Fragment2 = new FragmentLogList();
    Fragment1 = new Fragment_Main();

    //给FragmentList添加数据
    mFragmentList.add(Fragment1);
    mFragmentList.add(Fragment2);

    // mViewPager.setAdapter(new MyAdapter());
    mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
    mViewPager.setAdapter(mFragmentAdapter);
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   //super.onActivityResult(requestCode, resultCode, data);
    /*在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment*/
    FragmentManager fragmentManager=getSupportFragmentManager();
   // for(int indext=0;indext<fragmentManager.getFragments().size();indext++)
   // {
      Fragment fragment=fragmentManager.getFragments().get(0); //找到第一层Fragment
      if(fragment==null)
        ;
      else
        /*然后在碎片中调用重写的onActivityResult方法*/
        fragment.onActivityResult(requestCode, resultCode, data);
   // }
}

public class FragmentAdapter extends FragmentPagerAdapter {

  List<Fragment> fragmentList = new ArrayList<Fragment>();

  public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
    super(fm);
    this.fragmentList = fragmentList;
  }

  @Override
  public Fragment getItem(int position) {
    return fragmentList.get(position);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }

}


  public   void  getPermission()
  {
    int permissionCheck1;
    permissionCheck1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
    int permissionCheck2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
              124);
    }
    int permissionCheck3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
    if (permissionCheck3 != PackageManager.PERMISSION_GRANTED ) {
      ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.READ_PHONE_STATE},
              124);
    }

  }
}
