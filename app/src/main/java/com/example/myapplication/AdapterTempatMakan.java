package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterTempatMakan extends RecyclerView.Adapter<AdapterTempatMakan.ViewHolder> {
    private final ArrayList<TempatMakan> daftarMakan;
    private final Context context;

    public AdapterTempatMakan(ArrayList<TempatMakan> tempatMakans, Context context) {
        daftarMakan = tempatMakans;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView tv_nama;
        TextView tv_jarak;
        ImageView iv_gambar;

        ViewHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.card_makan);
            tv_nama = v.findViewById(R.id.tv_nama);
            tv_jarak = v.findViewById(R.id.tv_jarak);
            iv_gambar = v.findViewById(R.id.iv_gambar);
        }
    }

    @NonNull
    @Override
    public AdapterTempatMakan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rm, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String nama = daftarMakan.get(position).getNama_tempat();
        double jarak = daftarMakan.get(position).getJarak();
        String url_gambar = daftarMakan.get(position).getUrl_gambar();


        holder.tv_nama.setText(nama);
        holder.tv_jarak.setText(String.valueOf(jarak)+" km");
        Picasso.get().load(url_gambar).into(holder.iv_gambar);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTempatMakan.class);
                intent.putExtra(DetailTempatMakan.EXTRA_TEMPAT_MAKAN, daftarMakan.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return (daftarMakan == null) ? 0 : daftarMakan.size();
    }
}
