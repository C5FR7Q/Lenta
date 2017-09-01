package com.example.slava.lenta2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by slava on 29.08.2017.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    public static final int COUNT = 4;
    private boolean includeDesc;
    private List<Data> datas = new ArrayList<>();
    private int size;
    private int cutSize;
    private int fulSize;
    private Context context;

    public RvAdapter(Context context, Observable<List<Data>> datas, boolean includeDescription) {
        this.context = context;
        this.includeDesc = includeDescription;
        datas.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    this.datas = data;
                    this.cutSize = COUNT;
                    this.size = cutSize;
                    fulSize = data.size();
                    notifyDataSetChanged();
                });
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_half1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data data = datas.get(position);
        holder.tvArticleDate.setText(data.getPubDate().substring(0, data.getPubDate().length() - 5));
        holder.tvArticleTitle.setText(data.getTitle());
//        if (includeDesc)
            holder.tvArticleBody.setText(data.getDescription());
//        else
//            holder.tvArticleBody.setVisibility(View.GONE);
        holder.tvArticleCategory.setText(data.getCategory());
        holder.itemView.setOnClickListener(v -> context.startActivity(new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(data.getLink()))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));

        if (data.getPicLink() != null)
            Glide.with(context)
                    .load(data.getPicLink())
                    .into(holder.iv);
        else holder.iv.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public void setFullSize() {
        this.size = fulSize;
        notifyDataSetChanged();
    }

    public void setCutSize() {
        this.size = cutSize;
        notifyDataSetChanged();
    }

    public void setIncludeDesc(boolean includeDesc) {
        this.includeDesc = includeDesc;
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
            if (!includeDesc)
                tvArticleBody.setVisibility(View.GONE);
            else tvArticleBody.setVisibility(View.VISIBLE);
        }
    }

}
