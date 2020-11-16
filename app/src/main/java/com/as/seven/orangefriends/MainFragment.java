package com.as.seven.orangefriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainFragment extends Fragment {
    BottomBar bottomBar;
    int outerHeight;
    int outerwidth;
    ImageView iv1;
    private int ivFlag = 0;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 初始化
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        bottomBar = rootView.findViewById(R.id.bottomBar);
//        iv1 = rootView.findViewById(R.id.iv_flfg1);
//        // 取屏幕高
//        outerHeight = getResources().getDisplayMetrics().heightPixels - (int) (25 * getResources().getDisplayMetrics().density);
//        //取屏幕宽
//        outerwidth = getResources().getDisplayMetrics().widthPixels;
//        bottombarListen();
//        iv1Listen();


        return rootView;
    }

//    private void bottombarListen() {
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                switch (tabId) {
//                    case R.id.tab_mmck:
//                        iv1.setImageResource(R.drawable.mmck_welcome);
//                        ivFlag = 0;
//                        break;
//                    case R.id.tab_scz:
//                        iv1.setImageResource(R.drawable.sevencz_welcome);
//                        ivFlag = 1;
//                        break;
//                    case R.id.tab_sbq:
//                        iv1.setImageResource(R.drawable.smbq_welcome);
//                        ivFlag = 2;
//                        break;
//                }
//            }
//        });
//    }
//
//    private void iv1Listen() {
//        iv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (ivFlag) {
//                    case 0:
//                        startActivity(new Intent(getActivity(), MainMMCK.class));
//                        break;
//                    case 1:
//                        startActivity(new Intent(getActivity(), MainSevenGU.class));
//                        break;
//                    case 2:
//                        startActivity(new Intent(getActivity(), MainSMBQ.class));
//                        break;
//
//                }
//            }
//        });
//    }
}
