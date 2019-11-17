package com.example.myapplication;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.myapplication.response.ResponseAzure;
import com.example.myapplication.response.ResponseGambar;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button btn_ambil_foto;
    private ProgressBar progressBar;
    private static final int CAMERA_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_ambil_foto = findViewById(R.id.btn_ambil_foto);
        progressBar = findViewById(R.id.progress_bar);

        btn_ambil_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }
        });
    }
    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() +"_image.jpeg");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bos);
        byte[] bitmapdata = bos.toByteArray();
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
    }
    void uploadImage(Bitmap gambarbitmap){
        File file = createTempFile(gambarbitmap);
        final RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("fileToUpload", file.getName(), requestFile);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), "ewl");

        ApiServices apiServices = InitRetrofit.getInstanceCF();
        Call<ResponseGambar> up = apiServices.uploadPhoto(descBody,body);
        up.enqueue(new Callback<ResponseGambar>() {
            @Override
            public void onResponse(Call<ResponseGambar> call, Response<ResponseGambar> response) {
                getEmosi("https://makanla.000webhostapp.com/tempat/images/ewl"+response.body().getResult());
            }

            @Override
            public void onFailure(Call<ResponseGambar> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    void getEmosi(String url){
        final Intent intent = new Intent(MainActivity.this, UserPrefMood.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Url", url);

        ApiServices apiServices = InitRetrofit.getInstanceAzure();
        Call<ArrayList<ResponseAzure>> getMood = apiServices.getEmosi("emotion",jsonObject);
        getMood.enqueue(new Callback<ArrayList<ResponseAzure>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseAzure>> call, Response<ArrayList<ResponseAzure>>response) {
                if (response.isSuccessful()){
                    List<Double> list = new ArrayList<>();

                    list.add(response.body().get(0).getFaceAttributes().getEmotion().getAnger());
                    list.add(response.body().get(0).getFaceAttributes().getEmotion().getHappiness());
                    list.add(response.body().get(0).getFaceAttributes().getEmotion().getNeutral());
                    list.add(response.body().get(0).getFaceAttributes().getEmotion().getSadness());

                    Collections.sort(list);
                    double max = list.get(list.size()-1);
                    String emosi;

                    if (max == response.body().get(0).getFaceAttributes().getEmotion().getAnger())
                        emosi = "Marah";
                    else if (max == response.body().get(0).getFaceAttributes().getEmotion().getHappiness())
                        emosi = "Senang";
                    else if (max == response.body().get(0).getFaceAttributes().getEmotion().getNeutral())
                        emosi = "Biasa";
                    else if (max == response.body().get(0).getFaceAttributes().getEmotion().getSadness())
                        emosi = "Sedih";
                    else {
                        emosi = "Gagal mendeteksi";
                    }
                    progressBar.setVisibility(View.GONE);
                    intent.putExtra("emosi", emosi);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ResponseAzure>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            progressBar.setVisibility(View.VISIBLE);
            uploadImage(mphoto);
        }
    }
}
