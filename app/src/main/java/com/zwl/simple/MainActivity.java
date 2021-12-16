package com.zwl.simple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zwl.cspinner.CSpinnerTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CSpinnerTextView mSpinnerText;
    List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinnerText = findViewById(R.id.spinner_textvre);

        for (int i = 0; i < 20; i++) {
            mDatas.add("item: " + i);
        }
        mSpinnerText.setmDatas(mDatas)
                .setmChoosePosition(1)
                .setmShowRightIcon(true)
                .setOnSpinnerChoosedListener((position, content) -> {

        });
    }
}