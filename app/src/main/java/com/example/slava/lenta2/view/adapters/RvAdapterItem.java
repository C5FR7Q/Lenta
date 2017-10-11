package com.example.slava.lenta2.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.slava.lenta2.R;
import com.example.slava.lenta2.model.data_client.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slava on 29.08.2017.
 */

public class RvAdapterItem extends RecyclerView.Adapter<RvAdapterItem.ViewHolder> {
    public interface OnItemSelectedListener{

        void onSelect(String link);
    }
    private final boolean includeDesc;
    private final OnItemSelectedListener listener;
    private final boolean isFullSized;
    private List<Data> datas = new ArrayList<>();
    public RvAdapterItem(List<Data> datas,
                         boolean includeDesc,
                         boolean isFullSized,
                         OnItemSelectedListener listener) {
        this.datas = datas;
        this.includeDesc = includeDesc;
        this.listener = listener;
        this.isFullSized = isFullSized;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inside_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data data = datas.get(position);

        holder.tvArticleDate.setText(data.getPubDate());
        holder.tvArticleTitle.setText(data.getTitle());
        if (includeDesc)
            holder.tvArticleBody.setText(data.getDescription());
        else
            holder.tvArticleBody.setVisibility(View.GONE);
        holder.tvArticleCategory.setText(data.getCategory());

        if (data.getPicLink() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(data.getPicLink())
                    .into(holder.iv);
        }
        else holder.iv.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> listener.onSelect(data.getLink()));
    }

    @Override
    public int getItemCount() {
        if (isFullSized)
            return datas.size();
        return 4;
    }

    public void refreshDatas(List<Data> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvArticleTitle, tvArticleBody, tvArticleDate, tvArticleCategory;
        private ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            tvArticleBody = (TextView) itemView.findViewById(R.id.tvArcticleBody);
            tvArticleCategory = (TextView) itemView.findViewById(R.id.tvArticleCategory);
            tvArticleDate = (TextView) itemView.findViewById(R.id.tvArticleDate);
            tvArticleTitle = (TextView) itemView.findViewById(R.id.tvArticleTitle);
            iv = (ImageView)itemView.findViewById(R.id.iv);
        }
    }

}
