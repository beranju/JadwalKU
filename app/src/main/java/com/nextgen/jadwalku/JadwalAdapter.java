package com.nextgen.jadwalku;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URISyntaxException;
import java.text.BreakIterator;
import java.util.ArrayList;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.MyViewHolder> {
    //3
    Context context;
    ArrayList<Jadwal> jadwals;

    //4
    public JadwalAdapter(Context c, ArrayList<Jadwal> j){
        context = c;
        jadwals = j;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //6
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_jadwal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        //7
        myViewHolder.juduljadwal.setText(jadwals.get(position).getJuduljadwal());
        myViewHolder.deskjadwal.setText(jadwals.get(position).getDeskjadwal());
        myViewHolder.harijadwal.setText(jadwals.get(position).getHarijadwal());
        myViewHolder.jamjadwal.setText(jadwals.get(position).getJamjadwal());

        //1 video ketiga
        final String getJudulJadwal = jadwals.get(position).getJuduljadwal();
        final String getDeskJadwal = jadwals.get(position).getDeskjadwal();
        final String getHariJadwal = jadwals.get(position).getHarijadwal();
        final String getKeyJadwal = jadwals.get(position).getKeyjadwal();
        final String getJamJadwal = jadwals.get(position).getJamjadwal();


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditJadwalActivity.class);
                intent.putExtra("juduljadwal", getJudulJadwal);
                intent.putExtra("deskjadwal", getDeskJadwal);
                intent.putExtra("harijadwal", getHariJadwal);
                intent.putExtra("keyjadwal", getKeyJadwal);
                intent.putExtra("jamjadwal", getJamJadwal);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return jadwals.size();
    }


    //1
    class MyViewHolder extends RecyclerView.ViewHolder{

        //5
        TextView juduljadwal, deskjadwal, harijadwal, jamjadwal;
        //video 3
        TextView keyjadwal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //5
            juduljadwal = (TextView) itemView.findViewById(R.id.tv_juduljadwal);
            deskjadwal = (TextView) itemView.findViewById(R.id.tv_deskjadwal);
            harijadwal = (TextView) itemView.findViewById(R.id.tv_harijadwal);
            jamjadwal = (TextView) itemView.findViewById(R.id.tv_jamjadwal);

        }
    }
}
