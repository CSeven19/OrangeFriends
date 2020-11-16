package com.as.seven.orangefriends;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class IndexFragment extends Fragment {
    View root = null;
    TextView tv1 = null;
    TextView tv2 = null;
    TextView tv3 = null;
    TextView tv4 = null;
    TextView tv5 = null;
    TextView tv6 = null;
    ImageView iv1 = null;
    ImageView iv2 = null;
    Handler handler = null;

    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_index, container, false);
        tv1 = root.findViewById(R.id.index_tv1);
        tv2 = root.findViewById(R.id.index_tv2);
        tv3 = root.findViewById(R.id.index_tv3);
        tv4 = root.findViewById(R.id.index_tv4);
        tv5 = root.findViewById(R.id.index_tv5);
        tv6 = root.findViewById(R.id.index_tv6);
        iv1 = root.findViewById(R.id.index_iv1);
        iv2 = root.findViewById(R.id.index_iv2);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        iv1.setImageResource(R.mipmap.welcome1_5);
                        break;
                    case 1:
                        iv2.setImageResource(R.mipmap.welcome2_4);
                        break;
                }
            }
        };
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) tv1.getTextSize() + 20);
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) tv2.getTextSize() + 20);
                tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) tv3.getTextSize() + 20);
                tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) tv4.getTextSize() + 20);
                tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) tv5.getTextSize() + 20);
                tv6.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) tv6.getTextSize() + 20);
                String indexText = "こんにちは、私たちはオレンジ一族の大橘と小黄。ようこそ、オレンジ友よ、私たちの世界に向かうことを。";
                for (int i = 0; i < indexText.length() + 1; i++) {
                    switch ((int) Math.ceil(i / 10)) {
                        case 0:
                            tv1.setText(indexText.substring(0, i));
                            break;
                        case 1:
                            tv2.setText(indexText.substring(9, i));
                            break;
                        case 2:
                            tv3.setText(indexText.substring(19, i));
                            break;
                        case 3:
                            tv4.setText(indexText.substring(29, i));
                            break;
                        case 4:
                            tv5.setText(indexText.substring(39, i));
                            break;
                        case 5:
                            tv6.setText(indexText.substring(49, i));
                            break;
                    }
                    try {
                        if (i == 0)
                            Thread.sleep(1000);
                        else
                            Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        service.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    Message message = new Message();
                    switch (i) {
                        case 0:
                            message.what = 0;
                            handler.sendMessageDelayed(message, 100);
                            break;
                        case 1:
                            message.what = 1;
                            handler.sendMessageDelayed(message, 500);
                            break;
                    }
                }
            }
        });
        return root;
    }

    private void BackgroundAnimation() {
        if (this.getActivity() != null) {
            this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator ba = ObjectAnimator.ofFloat(root, "alpha", 0, 0, 1);
                    ba.setDuration(2000);
                    ba.setStartDelay(500);
                    ba.start();
                }
            });
        }
    }
}