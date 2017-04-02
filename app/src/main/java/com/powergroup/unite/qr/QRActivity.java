package com.powergroup.unite.qr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.powergroup.unite.R;
import com.powergroup.unite.app.Application;
import com.powergroup.unite.app.GenericActivity;
import com.powergroup.unite.app.Profile;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Created by bummy on 4/1/17.
 */

public class QRActivity extends GenericActivity {

    private ImageView qrCode;
    private TextView scan;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        assignViews();
        assignVariables(savedInstanceState);
        assignHandlers();
    }

    private void assignViews() {
        qrCode = (ImageView) findViewById(R.id.qr_code);
        scan = (TextView) findViewById(R.id.scan);
    }

    private void assignVariables(Bundle savedInstanceState) {
        try {
            qrCode.setImageBitmap(createQR(new Gson().toJson(Profile.INSTANCE.info)));
        } catch (Exception e) {
        }
    }

    private void assignHandlers() {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new com.google.zxing.integration.android.IntentIntegrator(QRActivity.this).initiateScan();
            }
        });
    }

    private Bitmap createQR(String message) throws WriterException {

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3/4;

        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(message,
                    BarcodeFormat.QR_CODE, smallerDimension, smallerDimension, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, smallerDimension, 0, 0, w, h);
        return bitmap;
    }
}
