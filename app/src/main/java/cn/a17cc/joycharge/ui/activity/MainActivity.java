package cn.a17cc.joycharge.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.a17cc.joycharge.R;
import cn.a17cc.joycharge.common.util.FragmentTabUtils;
import cn.a17cc.joycharge.ui.fragment.ChargeFragment;
import cn.a17cc.joycharge.ui.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    @BindView(R.id.tab_search)
    RadioButton tabSearch;
    @BindView(R.id.tab_charge)
    RadioButton tabCharge;
    @BindView(R.id.tab_find)
    RadioButton tabFind;
    @BindView(R.id.tab_me)
    RadioButton tabMe;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.main_group)
    RadioGroup mainGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDrawableTop();
        initFragment();

    }

    private void initDrawableTop() {
        int size =  getResources().getDimensionPixelSize(R.dimen.activity_tab_drawable);
        Drawable search = getResources().getDrawable(R.drawable.select_tab_search);
        Drawable charge = getResources().getDrawable(R.drawable.select_tab_charge);
        Drawable find = getResources().getDrawable(R.drawable.select_tab_find);
        Drawable me = getResources().getDrawable(R.drawable.select_tab_me);
        search.setBounds(0, 0,size, size);
        charge.setBounds(0, 0,size, size);
        find.setBounds(0, 0,size, size);
        me.setBounds(0, 0,size, size);
        tabSearch.setCompoundDrawables(null,search,null,null);
        tabCharge.setCompoundDrawables(null,charge,null,null);
        tabFind.setCompoundDrawables(null,find,null,null);
        tabMe.setCompoundDrawables(null,me,null,null);
    }

    private void initFragment() {
        fragments.add(SearchFragment.newInstance("1111111"));
        fragments.add(ChargeFragment.newInstance());
        fragments.add(SearchFragment.newInstance("3333333"));
        fragments.add(SearchFragment.newInstance("4444444"));
       new FragmentTabUtils(getSupportFragmentManager(), mainGroup, fragments, R.id.main_container);

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }


}
