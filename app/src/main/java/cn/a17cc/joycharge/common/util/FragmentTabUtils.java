package cn.a17cc.joycharge.common.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

/**
 * 创建人：luying
 * 创建时间：16/12/13
 * 类说明：
 */

public class FragmentTabUtils implements RadioGroup.OnCheckedChangeListener {
    private FragmentManager manager;
    private List<Fragment> fragments;
    private int containerId;
    private int preIndex;

    public FragmentTabUtils(FragmentManager manager, RadioGroup rgs, List<Fragment> fragments,
                            int containerId) {
        this.manager = manager;
        this.fragments = fragments;
        this.containerId = containerId;
        rgs.setOnCheckedChangeListener(this);
        ((RadioButton) rgs.getChildAt(0)).setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        manager.beginTransaction().hide(fragments.get(preIndex)).commit();
        int childCount = group.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int id = ((RadioButton) group.getChildAt(i)).getId();
            if (id == checkedId) {
                Fragment fragment = fragments.get(i);
                if (!fragment.isAdded()) {
                    manager.beginTransaction().add(containerId, fragment).commit();
                }
                manager.beginTransaction().show(fragment).commit();
                preIndex = i;
                break;
            }
        }
    }
}
