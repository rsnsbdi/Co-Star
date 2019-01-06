package com.costar.talkwithidol;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.costar.talkwithidol.ui.activities.eventDetailpexip.EventDetailPexipActivity;

import java.util.Arrays;

public class EventPretestActivity extends AppCompatActivity {
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final int POLL_INTERVAL = 300;
    public static String videoId;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private Size previewsize;
    private Size jpegSizes[] = null;
    private TextureView textureView;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder previewBuilder;
    private CameraCaptureSession previewSession;
    private boolean mRunning = false;
    private int mThreshold;
    private PowerManager.WakeLock mWakeLock;
    private Handler mHandler = new Handler();
    private TextView mStatusView;
    private SoundLevelView mDisplay;
    private SoundMeter mSensor;
    private Runnable mPollTask = new Runnable() {
        public void run() {

            double amp = mSensor.getAmplitude();
            updateDisplay("Monitoring Voice...", amp);
            if ((amp > mThreshold)) {
                callForHelp();
            }
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);

        }
    };

    private Runnable mSleepTask = new Runnable() {
        public void run() {
            start();
        }
    };
    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            startCamera();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
        }

        @Override
        public void onError(CameraDevice camera, int error) {
        }
    };
    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Defined SoundLevelView in main.xml file
        setContentView(R.layout.event_live_pretest);
        videoId = getIntent().getStringExtra("VIDEOID");

        // Used to record voice
        mSensor = new SoundMeter();
        mDisplay = findViewById(R.id.vol);
        textureView = findViewById(R.id.camerapreview);
        textureView.setSurfaceTextureListener(surfaceTextureListener);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");
        Button joinBtn = findViewById(R.id.join_event);

        joinBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EventPretestActivity.this, EventDetailPexipActivity.class);
        intent.putExtra("VIDEOID", videoId);
        startActivity(intent);
        finish();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mThreshold = 8;
        mDisplay.setLevel(0, mThreshold);
        if (!mRunning) {
            mRunning = true;
            start();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        stop();

    }

    private void start() {
        mSensor.start();
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        mDisplay.setLevel(0, 0);
        updateDisplay("stopped...", 0.0);
        mRunning = false;
    }

    private void updateDisplay(String status, double signalEMA) {
        mDisplay.setLevel((int) signalEMA, mThreshold);
    }

    private void callForHelp() {
        Toast.makeText(getApplicationContext(), "Noise Thersold Crossed, do here your stuff.",
                Toast.LENGTH_LONG).show();
    }


    public void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String camerId = manager.getCameraIdList()[1];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(camerId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            previewsize = map.getOutputSizes(SurfaceTexture.class)[0];
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            manager.openCamera(camerId, stateCallback, null);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraDevice != null) {
            cameraDevice.close();
        }
    }

    void startCamera() {
        if (cameraDevice == null || !textureView.isAvailable() || previewsize == null) {
            return;
        }
        SurfaceTexture texture = textureView.getSurfaceTexture();
        if (texture == null) {
            return;
        }
        texture.setDefaultBufferSize(previewsize.getWidth(), previewsize.getHeight());
        Surface surface = new Surface(texture);
        try {
            previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (Exception e) {
        }
        previewBuilder.addTarget(surface);
        try {
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    previewSession = session;
                    getChangedPreview();
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                }
            }, null);
        } catch (Exception e) {
        }
    }

    void getChangedPreview() {
        if (cameraDevice == null) {
            return;
        }
        previewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        HandlerThread thread = new HandlerThread("changed Preview");
        thread.start();
        Handler handler = new Handler(thread.getLooper());
        try {
            previewSession.setRepeatingRequest(previewBuilder.build(), null, handler);
        } catch (Exception e) {
        }
    }




}
