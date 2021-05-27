package com.example.newsbreezer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsbreezer.R;
import com.example.newsbreezer.activity.ReadActivity;
import com.example.newsbreezer.model.Article;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    private Context context;
    private List<Article> newsList;
    private setOnSaveButtonClickListener saveButtonListener;

    public NewsListAdapter(Context context, List<Article> newsList,setOnSaveButtonClickListener saveButtonListener) {
        this.context = context;
        this.newsList = newsList;
        this.saveButtonListener=saveButtonListener;
    }

    public void setNewsList(List<Article> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("onCreateViewHolder", "here" );
        View view= LayoutInflater.from(context).inflate(R.layout.news_row,parent,false);
        return new MyViewHolder(view) ;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.MyViewHolder holder, int position) {
        Log.e("inViewBinder", this.newsList.get(position).getTitle() );
        final Article temp=this.newsList.get(position);
        holder.titleTextView.setText(this.newsList.get(position).getTitle());
        holder.descriptionTextView.setText(this.newsList.get(position).getDescription());
        Glide.with(context)
                .load(this.newsList.get(position).getUrlToImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);
        if(this.newsList.get(position).getUrlToImage()==null)
            holder.noPreview.setVisibility(View.VISIBLE);
        else{
            holder.noPreview.setVisibility(View.GONE);
        }
        holder.imageView.setClipToOutline(true);
        if(this.newsList.get(position).getSaveState()==null){
            this.newsList.get(position).setSaveState(false);
        }

        if(this.newsList.get(position).getSaveState()){
            holder.saveIcon.setImageResource(R.drawable.save_green);
        }
        else{
            holder.saveIcon.setImageResource(R.drawable.save_black);
        }
        holder.saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!temp.getSaveState()) {
                    holder.saveIcon.setImageResource(R.drawable.save_green);
                    temp.setSaveState(true);
                }
                else
                {
                    holder.saveIcon.setImageResource(R.drawable.save_black);
                    temp.setSaveState(false);
                }
                saveButtonListener.onSaveButtonClicked(temp);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(this.newsList!= null){
            return this.newsList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;
        TextView noPreview;
        ImageView saveIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView= (TextView) itemView.findViewById(R.id.title);
            descriptionTextView= (TextView) itemView.findViewById(R.id.description);
            imageView= (ImageView) itemView.findViewById(R.id.image_view);
            noPreview=(TextView) itemView.findViewById(R.id.no_preview);
            saveIcon= (ImageView) itemView.findViewById(R.id.save_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Article a= newsList.get(getLayoutPosition());
                    Intent i  = new Intent(context,ReadActivity.class);
                    i.putExtra("article",a);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });
        }

    }

    public interface setOnSaveButtonClickListener{

        public void onSaveButtonClicked(Article article);

    }
}
