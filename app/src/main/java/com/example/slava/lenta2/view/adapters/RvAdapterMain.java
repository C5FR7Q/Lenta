package com.example.slava.lenta2.view.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.slava.lenta2.R;
import com.example.slava.lenta2.model.data_client.Data;
import com.example.slava.lenta2.presenter.IMainFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slava on 29.08.2017.
 */

public class RvAdapterMain extends RecyclerView.Adapter<RvAdapterMain.ViewHolder> {
    private final ArrayList<String> titles;
    private final IMainFragmentPresenter fragmentPresenter;
    private final RvAdapterItem.OnItemSelectedListener insideListener;
    private List<List<Data>> allDatas = new ArrayList<>();

    public RvAdapterMain(ArrayList<String> titles,
                         IMainFragmentPresenter fragmentPresenter,
                         RvAdapterItem.OnItemSelectedListener insideListener) {
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
        holder.tvTitle.setText(titles.get(position));
        holder.btnView.setOnClickListener(v -> fragmentPresenter.onViewClicked(position));
        if (position == 0){
            holder.rv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            includeDesc = true;
        }
        else {
            holder.rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            includeDesc = false;
        }
        if (!(allDatas.size() <= position))
            holder.rv.setAdapter(new RvAdapterItem(allDatas.get(position), includeDesc, false, insideListener));
    }

    /*public void setInsideAdapter(ViewHolder holder,
                                  List<Data> datas,
                                  boolean includeDesc,
                                  RvAdapterItem.OnItemSelectedListener insideListener){
        holder.rv.setAdapter(new RvAdapterItem(datas, includeDesc, false, insideListener));
    }*/

    @Override
    public int getItemCount() {
        return (titles == null) ? 0 : titles.size();
    }

    public void addDatas(List<Data> data){
        if (isValid(data)){
            this.allDatas.add(data);
            notifyDataSetChanged();
        }
    }

    public void setAllDatas(List<List<Data>> datas){
        if (isValid(datas)){
            this.allDatas = datas;
            notifyDataSetChanged();
        }
    }

    private boolean isValid(List datas){
        return !(datas == null || datas.isEmpty());
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
