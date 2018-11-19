package no.hiof.fredrivo.budgetapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Camera_activity extends AppCompatActivity {

    private Button Camerabtn;
    private ImageView imageView;
    private static final int CAMERA_REQUEST_CODE = 1;
    StorageReference ref;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ref = FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.activity_camera_activity);

        Camerabtn = (Button) findViewById(R.id.camerashot);
        imageView = (ImageView) findViewById(R.id.imageV);
        progress = new ProgressDialog(this);

        Camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // intent.putExtra(MediaStore.EXTRA_OUTPUT,v.getContext());
                startActivityForResult(intent,CAMERA_REQUEST_CODE);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            progress.setMessage("Uploading receipt...");
            progress.show();
            Uri uri = data.getData();
            StorageReference filepath = ref.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                    progress.dismiss();
                    Toast.makeText(Camera_activity.this,"Receipt has been uploaded",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
