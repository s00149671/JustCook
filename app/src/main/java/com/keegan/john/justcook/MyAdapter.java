package com.keegan.john.justcook;

/**
 * Created by Johnk on 17/02/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Johnk on 17/02/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public List<ListItem> listItems;
    public Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int Position);
    }

   public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
   }

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);

        holder.recipe_name.setText(listItem.getHead());
        Picasso.with(context)
                .load(listItem.getImageUrl())
                .into(holder.imageView);
        holder.ingred.setText(listItem.getIngred());

       // holder.linearLayout.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
             //   Toast.makeText(context, "You Clicked " +listItem.getHead(), Toast.LENGTH_SHORT).show();
           // }
       // });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recipe_name;
        public ImageView imageView;
        public TextView ingred;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            recipe_name = (TextView) itemView.findViewById(R.id.recipe_name);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
              ingred = (TextView) itemView.findViewById(R.id.txtIngredients);
//            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
