package com.example.viewoverlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    ViewGroup root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        root = findViewById(android.R.id.content);

        ViewGroupOverlay viewGroupOverlay = root.getOverlay();
        ViewOverlay viewOverlay = button.getOverlay();

        Drawable drawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.hand);
        int[] location = new int[2];
        button.getLocationOnScreen(location);
        int sourceX = location[0];
        int sourceY = location[1];
        int width = button.getWidth();
        int height = button.getHeight();

        drawable.setBounds(sourceX, sourceY, 0, 0);

        viewGroupOverlay.add(drawable);
    }
}