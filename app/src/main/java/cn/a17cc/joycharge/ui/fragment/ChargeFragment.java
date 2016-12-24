package cn.a17cc.joycharge.ui.fragment;

import android.os.Bundle;

import cn.a17cc.joycharge.R;
import cn.a17cc.joycharge.common.Base.BaseFragment;

/**
 * 创建人：luying
 * 创建时间：16/12/17
 * 类说明：
 */

public class ChargeFragment extends BaseFragment{
    public static ChargeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChargeFragment fragment = new ChargeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int setLayout() {
        return R.layout.fragment_charge;
    }
}
