package com.as.seven.orangefriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.index_container, new IndexFragment()).commit();
    }

    public boolean onTouchEvent(MotionEvent event) {
        startActivity(new Intent(this, MainActivity.class));
        return true;
    }
}