package com.example.slava.lenta2.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.slava.lenta2.Constants;
import com.example.slava.lenta2.OnRecyclerViewItemSelected;
import com.example.slava.lenta2.R;
import com.example.slava.lenta2.client.LentaClient;
import com.example.slava.lenta2.views.fragment_main.presenter.IFragmentPresenter;

import java.util.ArrayList;

/**
 * Created by slava on 29.08.2017.
 */

public class RvAdapterMain extends RecyclerView.Adapter<RvAdapterMain.ViewHolder> {
    private final ArrayList<String> titles;
    private final IFragmentPresenter fragmentPresenter;
    private final OnRecyclerViewItemSelected insideListener;

    public RvAdapterMain(ArrayList<String> titles,
                         IFragmentPresenter fragmentPresenter,
                         OnRecyclerViewItemSelected insideListener) {
        this.titles = titles;
        this.fragmentPresenter = fragmentPresenter;
        this.insideListener = insideListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final boolean includeDesc;
        holder.tvTitle.setText(titles.get(0));
        holder.btnView.setOnClickListener(v -> fragmentPresenter.onViewClicked(position));
        if (position == 0){
            holder.rv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            includeDesc = true;
        }
        else {
            holder.rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            includeDesc = false;
        }
        LentaClient.getInstance()
                .get(position)
                .subscribe(datas -> holder.rv.setAdapter(new RvAdapterItem(datas, includeDesc, false, insideListener)));
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