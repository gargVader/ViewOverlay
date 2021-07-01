package com.example.viewoverlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.Button;
import android.widget.RelativeLayout;

import static android.view.View.MeasureSpec.makeMeasureSpec;
import static android.view.View.MeasureSpec.EXACTLY;

public class MainActivity extends AppCompatActivity {

    private ViewGroup root;
    private Button button;
//    private Drawable hand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(android.R.id.content);
        button = findViewById(R.id.button);
//        hand = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.hand);

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
//        final int handLeft = buttonBoundsInRoot.centerX() - hand.getIntrinsicWidth() / 2;
//        final int handTop = buttonBoundsInRoot.centerY();
//        hand.setBounds(handLeft, handTop, handLeft + hand.getIntrinsicWidth(), handTop + hand.getIntrinsicHeight());
//        rootOverlay.add(hand);

        // The layout_width and _height in the XML are not applied when inflating
        // a View like this, so this example figures the pixel values here.
        final int handWidth = dpToPx(60);
        final int handHeight = dpToPx(70);

        final View handView = getLayoutInflater().inflate(R.layout.hand_view, null);
        final int handLeft = buttonBoundsInRoot.centerX() - handWidth / 2;
        final int handTop = buttonBoundsInRoot.centerY();
        final int handRight = buttonBoundsInRoot.centerX() + handWidth / 2;
        final int handBottom = buttonBoundsInRoot.centerY() + handHeight;

        handView.measure(makeMeasureSpec(handWidth, EXACTLY), makeMeasureSpec(handHeight, EXACTLY));
        handView.layout(handLeft, handTop, handRight, handBottom);

        handView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Main", "onClick: ");
            }
        });

        rootOverlay.add(handView);

        // DEBUG - Just so you can see how it lines up.
        handView.setBackgroundColor(0x55ff00ff);
    }

    int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}