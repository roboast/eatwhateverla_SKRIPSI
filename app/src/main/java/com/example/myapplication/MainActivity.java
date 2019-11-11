package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Button btn_ambil_foto;
    private static final int CAMERA_REQUEST = 0;
    FirebaseAuth mAuth;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserPrefMood.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        btn_ambil_foto = findViewById(R.id.btn_ambil_foto);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl("gs://eatwhateverla.appspot.com");


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


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    void getEmosi(String url){
        final Intent intent = new Intent(MainActivity.this, UserPrefMood.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Url", url);

        //ApiServices apiServices = InitRetrofit.getInstanceAzure();
        //Call<ArrayList<Kuliner>> getAllRekom = apiServices.getRekom(latitude,longitude);
        /**final String reqBody = jsonObject.toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://skripsi.cognitiveservices.azure.com/face/v1.0/detect?returnFaceAttributes=emotion";

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.POST, URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject emotion = response.getJSONObject(0);
                            JSONObject emotion1 = emotion.getJSONObject("faceAttributes");
                            JSONObject emotion2 = emotion1.getJSONObject("emotion");

                            List<Double> list = new ArrayList<>();
                            list.add(emotion2.getDouble("happiness"));
                            list.add(emotion2.getDouble("anger"));
                            list.add(emotion2.getDouble("neutral"));
                            list.add(emotion2.getDouble("sadness"));

                            Collections.sort(list);
                            double max = list.get(list.size()-1);
                            String emosi;

                            if (max == emotion2.getDouble("anger"))
                                emosi = "Marah";
                            else if (max == emotion2.getDouble("happiness"))
                                emosi = "Senang";
                            else if (max == emotion2.getDouble("neutral"))
                                emosi = "Biasa";
                            else if (max == emotion2.getDouble("sadness"))
                                emosi = "Sedih";
                            else {
                                emosi = "Gagal mendeteksi";
                            }
                            intent.putExtra("emosi", emosi);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() {
                return reqBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Ocp-Apim-Subscription-Key", "667cdde46a6440af974b82f3671eb214");
                return headers;
            }

            @Override
            public int getMethod() {
                return Method.POST;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
         */
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);
            final StorageReference reference = storageReference.child("user/"+UUID.randomUUID());
            reference.putFile(tempUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            getEmosi(uri.toString());
                        }
                    });
                }
            });
        }
    }
}
