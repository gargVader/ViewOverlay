package com.example.viewoverlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private ViewGroup root;
    private Button button;
    private Drawable hand;
    private View handPointer;
    RelativeLayout.LayoutParams handPointerParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = findViewById(R.id.button);
        root = findViewById(android.R.id.content);
        handPointer = getLayoutInflater().inflate(R.layout.hand_view, null);

        hand = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.hand);

        // We need to delay this until everything is actually measured and laid
        // out on-screen, which doesn't happen until after onCreate() ends.
        // This is a handy trick to do that.
        root.post(this::showHand);
    }

    private void showHand() {
        final ViewGroupOverlay rootOverlay = root.getOverlay();

        // You're not adding Views directly to WindowManager anymore, so using
        // getLocationOnScreen() isn't appropriate. Instead, we need to find the
        // coordinates with respect to the root, so we place the Drawable correctly.

        // The button is not directly inside the root, but we need to know its bounds
        // with respect to the root, so we have the right location for the overlay.
        final Rect buttonBoundsInRoot = new Rect(0, 0, button.getWidth(), button.getHeight());
        root.offsetDescendantRectToMyCoords(button, buttonBoundsInRoot);

        // This is simply setting the top-center of the hand image to point at the center
        // of the Button. The index finger isn't centered in the image, though, so it looks
        // off, but this is just for a quick demo. Also just using the image at its
        // actual size, which is pretty huge.
        final int handLeft = buttonBoundsInRoot.centerX() - hand.getIntrinsicWidth() / 2;
        final int handTop = buttonBoundsInRoot.centerY();

        hand.setBounds(handLeft, handTop, handLeft + hand.getIntrinsicWidth(), handTop + hand.getIntrinsicHeight());
        handPointerParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        handPointerParam.leftMargin = handLeft;
        handPointerParam.topMargin = handTop;
        handPointerParam.rightMargin = handLeft + hand.getIntrinsicWidth();
        handPointerParam.bottomMargin = handTop + hand.getIntrinsicHeight();

//        rootOverlay.add(hand);
        rootOverlay.add(handPointer);
    }
}