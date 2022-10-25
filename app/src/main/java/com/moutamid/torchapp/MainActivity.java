package com.moutamid.torchapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    ImageView torch;
    boolean state = false;
    TextView privacy;
    Button donate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        torch = findViewById(R.id.torch);
        privacy = findViewById(R.id.privacy);
        donate = findViewById(R.id.donate);

        donate.setOnClickListener(v -> {
            startActivity(new Intent(this, DonateActivity.class));
        });

        privacy.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/e/2PACX-1vSt8Cys91J9waHzKdrQaOYlwn3Vbcs_bRS5Lgko46sATHep6SPF_MNqcmYDz-pNb5jbjVLN_Viz_z80/pub"));
            startActivity(browserIntent);
        });

        Dexter.withContext(this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                FlashLight();
                            }
                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(MainActivity.this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }
                            })
                        .check();

        torch.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                FlashLight();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void FlashLight() {
        torch.setOnClickListener(v -> {
            if (!state){
                CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                try {
                    String cameraID = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraID, true);
                    state = true;
                    torch.setBackgroundResource(R.drawable.torch_on);
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                try {
                    String cameraID = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraID, false);
                    state = false;
                    torch.setBackgroundResource(R.drawable.torch_off);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}