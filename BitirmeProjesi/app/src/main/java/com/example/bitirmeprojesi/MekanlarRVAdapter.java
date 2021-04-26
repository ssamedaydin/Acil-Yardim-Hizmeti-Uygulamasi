package com.example.bitirmeprojesi;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MekanlarRVAdapter extends RecyclerView.Adapter<MekanlarRVAdapter.CardTasarimTutucu>
{
    private Context mContext;
    private List<Mekanlar> mekanlarListe;

    public MekanlarRVAdapter(Context mContext, List<Mekanlar> mekanlarListe) {
        this.mContext = mContext;
        this.mekanlarListe = mekanlarListe;
    }


    public class CardTasarimTutucu extends RecyclerView.ViewHolder{
        private TextView textViewMekan_adi,textViewLokasyon,textViewAdres;
        public CardTasarimTutucu(@NonNull View itemView) {
            super(itemView);

            textViewMekan_adi = itemView.findViewById(R.id.textViewMekanAdi);
            textViewLokasyon = itemView.findViewById(R.id.textViewLokasyon);
            textViewAdres = itemView.findViewById(R.id.textViewAdres);
        }
    }

    @NonNull
    @Override
    public CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mekan_card_tasarim,parent,false);
        return new CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTasarimTutucu holder, int position) {
        Mekanlar mekan = mekanlarListe.get(position);
        holder.textViewMekan_adi.setText(mekan.getMekan_adi());
        holder.textViewLokasyon.setText(mekan.getEnlem()+ " - "+mekan.getBoylam());
        holder.textViewAdres.setText(mekan.getAdres());
    }

    @Override
    public int getItemCount() {
        return mekanlarListe.size();
    }
}
