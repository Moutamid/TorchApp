package com.moutamid.torchapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        torch = findViewById(R.id.torch);

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