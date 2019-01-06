package com.costar.talkwithidol.ui.fragments.setting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.costar.talkwithidol.BuildConfig;
import com.costar.talkwithidol.R;
import com.costar.talkwithidol.app.PadloktApplication;
import com.costar.talkwithidol.app.network.ApiManager;
import com.costar.talkwithidol.app.network.models.profileUpdateResponse.UpdateProfilePicResponse;
import com.costar.talkwithidol.ext.AppUtils;
import com.costar.talkwithidol.ext.Constants;
import com.costar.talkwithidol.ui.activities.homepage.mvp.HomePageView;
import com.costar.talkwithidol.ui.fragments.setting.dagger.DaggerSettingComponent;
import com.costar.talkwithidol.ui.fragments.setting.dagger.SettingModule;
import com.costar.talkwithidol.ui.fragments.setting.mvp.SettingPresenter;
import com.costar.talkwithidol.ui.fragments.setting.mvp.SettingView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class SettingFragment extends Fragment {

    @Inject
    SettingView settingView;

    @Inject
    SettingPresenter settingPresenter;

    public static  String type ;

    File tempOutputFile;
    private static int RESULT_LOAD_IMAGE1 = 1;
    Bitmap rotatedBMP, scaled;
    String picturePath1, picturePathG;
    static final int REQUEST_TAKE_PHOTO1 = 2;
    String selectedPath;
    File compressedImage;
    private File actualImage;
    File photoFile;
    SharedPreferences sharedPreferences;
    Random random = new Random(System.nanoTime());
    File pictureFile;
    ProgressDialog progressDialog ;

    private Callback<UpdateProfilePicResponse> profileRespnseDtoCallback;
    public File finalfile;


    @Override
    public void onResume() {
        super.onResume();
        HomePageView.currentFragment= "settings";

         settingPresenter.onCreateView();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DaggerSettingComponent.builder()
                .appComponent(PadloktApplication.get(getActivity()).appComponent())
                .settingModule(new SettingModule((AppCompatActivity) getActivity()))
                .build()
                .inject(this);
        settingPresenter.onCreateView();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Glide.with(getActivity()).load(settingPresenter.preferencesManager.get(Constants.PROFILEIMAGESetting))
                .into(settingView.takeImage1);

        settingView.iv_camera.setOnClickListener(view -> TakePhotoGallery(RESULT_LOAD_IMAGE1,REQUEST_TAKE_PHOTO1));


        //retrofit login response call back
        profileRespnseDtoCallback = new Callback<UpdateProfilePicResponse>() {
            @Override
            public void onResponse(Call<UpdateProfilePicResponse> call, retrofit2.Response<UpdateProfilePicResponse> response) {
                //AppLog.d(TAG, response.body().toString());
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    UpdateProfilePicResponse updateProfilePicResponse = response.body();
                    Glide.with(getActivity()).load(finalfile)
                            .into(settingView.takeImage1);
                    Toast.makeText(getActivity(), updateProfilePicResponse.getMessage(), Toast.LENGTH_SHORT).show();


                } else {
                    UpdateProfilePicResponse updateProfilePicResponse = response.body();
                    Toast.makeText(getActivity(), updateProfilePicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UpdateProfilePicResponse> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        return settingView;
    }

    @Override
    public void onDestroyView() {
        ViewGroup mContainer = getActivity().findViewById(R.id.container);
        mContainer.removeAllViews();
        super.onDestroyView();

    }

    //gallery or take photo
    public void TakePhotoGallery(final int requestcodegallery, final int requestcodephoto) {

        List<String> option = Arrays.asList(getResources().getStringArray(R.array.option));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.DialogStyle);

        builder.setTitle(getActivity().getString(R.string.select_option));
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
                    dispatchTakePictureIntent(requestcodephoto);

                }
                if (which == 1) {

                    Intent selectImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    selectImageIntent.setType("image/*");
                    selectImageIntent.setType("image/*");
                    selectImageIntent.putExtra("crop", "true");
                    selectImageIntent.putExtra("scale", true);
                    selectImageIntent.putExtra("outputX", 256);
                    selectImageIntent.putExtra("outputY", 256);
                    selectImageIntent.putExtra("aspectX", 1);
                    selectImageIntent.putExtra("aspectY", 1);
                    selectImageIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    selectImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
                    startActivityForResult(selectImageIntent, requestcodegallery);
                }

            }
        });
        builder.show();

    }


    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(Environment.getExternalStorageDirectory(),"TEMP_PHOTO_FILE");
            try {
                file.createNewFile();
            } catch (IOException e) {

            }

            return file;
        } else {

            return null;
        }
    }

    private void dispatchTakePictureIntent(final int requestcode) {


        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "Camera_" + timeStamp + "_";
            tempOutputFile = new File(getActivity().getExternalCacheDir(), imageFileName + ".jpeg");
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    tempOutputFile));
            startActivityForResult(captureIntent, requestcode);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "Gallery_" + timeStamp + random.nextInt(1000000000) + ".jpeg";
        mediaFile = new File(storageDir.getPath() + File.separator + mImageName);
        picturePathG = mediaFile.getAbsolutePath();
        return mediaFile;

    }

    private void storeImage(Bitmap image) {
        pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("TAG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
    }


    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //  Log.i(TAG, "onActivityResult: " + this);

        String TAG = "Tag";

        if (resultCode != getActivity().RESULT_OK) {
            return;
        }

        //for gallery
        if (requestCode == RESULT_LOAD_IMAGE1 && null != data) {

            ParcelFileDescriptor parcelFD = null;
            try {
                parcelFD = getActivity().getContentResolver().openFileDescriptor(data.getData(), "r");
                FileDescriptor imageSource = parcelFD.getFileDescriptor();
                // Decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(imageSource, null, o);
                // the new size we want to scale to
                final int REQUIRED_SIZE = 1024;
                // Find the correct scale value. It should be the power of 2.
                int width_tmp = o.outWidth, height_tmp = o.outHeight;
                int scale = 1;
                while (true) {
                    if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                        break;
                    }
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }

                // decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);

                storeImage(bitmap);

                // Get the dimensions of the View
                int targetW = settingView.takeImage1.getWidth();
                int targetH = settingView.takeImage1.getHeight();
                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(picturePathG, bmOptions);

                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;


                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                try {

                    Bitmap bitmap1 = BitmapFactory.decodeFile(picturePathG, bmOptions);
                    // Bitmap bitmap2 = modifyOrientation(bitmap1,picturePathG);
                    if (requestCode == 1) {
                        picturePath1 = picturePathG;
                        sharedPreferences.edit().putString(Constants.PROFILEIMAGE, picturePath1).apply();

                        settingView. takeImage1.setImageBitmap(bitmap1);

                      /*  Glide.with(this).load(picturePath1)
                                .into(settingView.takeImage1);*/
                        //UpdateUserProfile(pictureFile);
                        uploadimageintoserver(pictureFile);
                    }


                } catch (OutOfMemoryError e) {
                    try {
                        bmOptions = new BitmapFactory.Options();
                        bmOptions.inSampleSize = 2;
                        Bitmap bitmap1 = BitmapFactory.decodeFile(picturePathG, bmOptions);

                        if (requestCode == 1) {
                            picturePath1 = picturePathG;
                            sharedPreferences.edit().putString(Constants.PROFILEIMAGE, picturePath1).apply();

                            settingView.takeImage1.setImageBitmap(bitmap1);
                           /* Glide.with(this).load(picturePath1)
                                    .into(settingView.takeImage1);*/
                           // UpdateUserProfile(pictureFile);
                            uploadimageintoserver(pictureFile);
                        }

                    } catch (Exception e11) {

                        Toast.makeText(getActivity(), e11.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (FileNotFoundException e) {
                // handle errors

                Toast.makeText(getActivity(),"FileNotFoundException:"+e.getMessage(),Toast.LENGTH_LONG).show();

            }


            //for camera
        } else if (requestCode == REQUEST_TAKE_PHOTO1 ) {


            try{
                Uri outputFile;

                if (data != null && (data.getAction() == null || !data.getAction().equals(MediaStore.ACTION_IMAGE_CAPTURE))) {
                    outputFile = data.getData();
                    actualImage = new File(getPath(outputFile));

                } else {

                    actualImage = tempOutputFile;
//                actualImageView.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath(),options));
                    // actualSizeTextView.setData(String.format("Size : %s", getReadableFileSize(actualImage.length())));
                }


                if (actualImage == null) {
                    Toast.makeText(getActivity(), getString(R.string.choose_option), Toast.LENGTH_LONG).show();
                } else {

                    compressedImage = new Compressor.Builder(getActivity())
                            .setMaxWidth(1024)
                            .setMaxHeight(720)
                            .setQuality(100)
                            .setCompressFormat(Bitmap.CompressFormat.JPEG)
                            .setDestinationDirectoryPath(getActivity().getExternalFilesDir(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .build()
                            .compressToFile(actualImage);
                    setPic(requestCode, compressedImage.getAbsolutePath());
                }

               //UpdateUserProfile(compressedImage);
                uploadimageintoserver(compressedImage);

            }catch(Exception e){
                Toast.makeText(getActivity(),"CompressedException:"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }


    private void setPic(int requestcode, String pathfile) {


        try {

            // Bitmap bitmap = BitmapFactory.decodeFile(picturePath, bmOptions);
            //Bitmap bitmap =  decodeSampledBitmapFromFile(picturePath,100,100);

            if (requestcode == 2) {
                picturePath1 = pathfile;
                sharedPreferences.edit().putString(Constants.PROFILEIMAGE, picturePath1).apply();

               // settingView.takeImage1.setImageBitmap(BitmapFactory.decodeFile(picturePath1));
                /*Glide.with(this).load(picturePath1)
                        .into(settingView.takeImage1);*/
            }


        } catch (OutOfMemoryError e) {
            Toast.makeText(getActivity(), "OutOfMemoryException:"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void uploadimageintoserver(File file) {

        finalfile =file;

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part userimage =
                MultipartBody.Part.createFormData("profile_picture", "image/jpeg", requestFile);

        if(AppUtils.hasInternet(getActivity())) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Uploading picture please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiManager.UserProfileImageResponse(profileRespnseDtoCallback, settingView.preferencesManager.get(Constants.TOKEN), settingView.preferencesManager.get(Constants.COOKIE), userimage);
        }else
        {
            Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_LONG).show();
        }

    }



}
