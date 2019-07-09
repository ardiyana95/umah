package com.example.umah_app.result;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.umah_app.R;
import com.example.umah_app.model.Rumah;

import java.util.ArrayList;

public class TopsisAdapter extends RecyclerView.Adapter<TopsisAdapter.TopsisViewHolder> {
    private ArrayList<Rumah> dataList;
    private Context context;

    public TopsisAdapter(Context context, ArrayList<Rumah> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public TopsisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_rv_item, parent, false);
        return new TopsisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopsisViewHolder holder, int position) {
        final Integer id = dataList.get(position).getId();
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtHarga.setText(dataList.get(position).getHarga().toString());
        holder.txtLokasi.setText(dataList.get(position).getKota());
        holder.image.setImageBitmap(dataList.get(position).getImage());
        holder.pengguna.setText(dataList.get(position).getPengguna());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context, DetailPage.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", id);
                detail.putExtras(bundle);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    /*
    ADD DATA TO ADAPTER
     */
    public void add(Rumah rumah) {
        dataList.add(rumah);
        notifyDataSetChanged();
    }

    /*
    CLEAR DATA FROM ADAPTER
     */
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public class TopsisViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtHarga, txtLokasi;
        private ImageView image;
        private CardView cardView;
        private TextView pengguna;

        public TopsisViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.topNama);
            txtHarga = (TextView) itemView.findViewById(R.id.topHarga);
            txtLokasi = (TextView) itemView.findViewById(R.id.topLokasi);
            image = (ImageView) itemView.findViewById(R.id.topImage);
            cardView = (CardView) itemView.findViewById(R.id.cardTopsis);
            pengguna = (TextView) itemView.findViewById(R.id.topPengguna);
        }
    }
}
