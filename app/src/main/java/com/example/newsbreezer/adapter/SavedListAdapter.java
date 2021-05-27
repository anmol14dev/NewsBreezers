package com.example.newsbreezer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.MyViewHolder> {
    Context context;
    List<Article> savedList;

    public SavedListAdapter(Context context, List<Article> savedList ) {
        this.context = context;
        this.savedList = savedList;
    }
    public void setSavedList(List<Article> savedList){
        this.savedList=savedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public SavedListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.saved_row,parent,false);
        return new MyViewHolder(view) ;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull SavedListAdapter.MyViewHolder holder, int position) {
        String date="";
        holder.title.setText(savedList.get(position).getTitle());
        if(savedList.get(position).getPublishedAt()!=null){
           int index = savedList.get(position).getPublishedAt().indexOf('T');
           date=savedList.get(position).getPublishedAt().substring(0,index);
        }
        holder.date.setText(date);
        holder.author.setText(savedList.get(position).getAuthor());
        Glide.with(context)
                .load(this.savedList.get(position).getUrlToImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);
        holder.imageView.setClipToOutline(true);



    }

    @Override
    public int getItemCount() {
        if(this.savedList!=null){
            return this.savedList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView date;
        TextView author;
        ImageView cancelButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.image_view2);
            title=(TextView)itemView.findViewById(R.id.title);
            date=(TextView)itemView.findViewById(R.id.date);
            author=(TextView)itemView.findViewById(R.id.author);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Article a=savedList.get(getLayoutPosition());
                    Intent i = new Intent(context, ReadActivity.class);
                    i.putExtra("article",a);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                            });

        }

    }

}
