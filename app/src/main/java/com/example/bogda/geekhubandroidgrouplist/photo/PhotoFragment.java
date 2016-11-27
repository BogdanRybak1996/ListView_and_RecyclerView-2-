package com.example.bogda.geekhubandroidgrouplist.photo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bogda.geekhubandroidgrouplist.R;

import java.io.File;

/**
 * Created by bohdan on 10.11.16.
 */

public class PhotoFragment extends Fragment {
    final int REQUEST_CAMERA = 1;
    final int REQUEST_GALLERY = 2;
    Bitmap bm;
    ImageView img;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        if(savedInstanceState!=null){
            bm = (Bitmap) savedInstanceState.getParcelable("image");
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        img = (ImageView) getActivity().findViewById(R.id.photo_image_view);
        if (bm!=null) {
            img.setImageBitmap(bm);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    private void showDialog(){
        final String [] items = {"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Get a photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(),"temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent,REQUEST_CAMERA);
                }
                if(items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY);
                }
                if(items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for(File temp : f.listFiles()){
                    if(temp.getName().equals("temp.jpg")){
                        f=temp;
                        break;
                    }
                }
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),bitmapOptions);
                bitmap = rotateImage(bitmap,270);
                img.setImageBitmap(bitmap);
                f.delete();
            }
            if(requestCode==REQUEST_GALLERY){
                Uri selectedImageUri = data.getData();
                String tempPath = getPath(selectedImageUri, getActivity());
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(tempPath, btmapOptions);
                bitmap = rotateImage(bitmap,270);
                img.setImageBitmap(bitmap);
            }
        }
    }
    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public static Bitmap rotateImage(Bitmap src, float degree)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        outState.putParcelable("image",bitmap);

        }
}
