package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.myapplication.response.ResponseAzure;
import com.example.myapplication.response.ResponseFoto;
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
    private static final int CAMERA_REQUEST = 0;


    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);



        btn_ambil_foto = findViewById(R.id.btn_ambil_foto);




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
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    , System.currentTimeMillis() +"_image.webp");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.WEBP,0, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file

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
        final String desc = "1212";
        File file = createTempFile(gambarbitmap);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("foto", file.getName(), requestFile);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);

        ApiServices apiServices = InitRetrofit.getInstanceCF();
        Call<ResponseFoto> up = apiServices.uploadFoto(descBody,body);
        up.enqueue(new Callback<ResponseFoto>() {
            @Override
            public void onResponse(Call<ResponseFoto> call, Response<ResponseFoto> response) {
                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                getEmosi("https://makanla.000webhostapp.com/foto.php?id_user="+desc);
            }

            @Override
            public void onFailure(Call<ResponseFoto> call, Throwable t) {
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
                    //Toast.makeText(getApplicationContext(), String.valueOf(response.body().get(0).getFaceAttributes().getEmotion().getAnger()), Toast.LENGTH_LONG).show();
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
            uploadImage(mphoto);
        }
    }
}
