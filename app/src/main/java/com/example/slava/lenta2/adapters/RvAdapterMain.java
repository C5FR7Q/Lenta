package com.example.slava.lenta2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.slava.lenta2.Constants;
import com.example.slava.lenta2.R;

import java.util.ArrayList;

/**
 * Created by slava on 29.08.2017.
 */

public class RvAdapterMain extends RecyclerView.Adapter<RvAdapterMain.ViewHolder> {
    private final ArrayList<View.OnClickListener> listeners;
    private final ArrayList<String> titles;

    public RvAdapterMain(ArrayList<String> titles, ArrayList<View.OnClickListener> listeners) {
        this.titles = titles;
        this.listeners = listeners;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(titles.get(0));
        holder.btnView.setOnClickListener(listeners.get(0));
    }

    @Override
    public int getItemCount() {
        return Constants.getTitles().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private Button btnView;
        private RecyclerView rv;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvFmtTitle);
            btnView = (Button) itemView.findViewById(R.id.btnFmtView);
            rv = (RecyclerView)itemView.findViewById(R.id.rv_item);
        }
    }

}
