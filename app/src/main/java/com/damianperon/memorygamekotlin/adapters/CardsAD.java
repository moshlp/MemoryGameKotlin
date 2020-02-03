package com.damianperon.memorygamekotlin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.damianperon.memorygamekotlin.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CardsAD extends RecyclerView.Adapter<CardsAD.ViewHolder> {

    private int[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CardsAD(Context context, int[] data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = mClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Integer resource = mData[position];
        holder.front.setImageResource(resource);
       //holder.myEasyFlipView.flipTheView();
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EasyFlipView myEasyFlipView;
        ImageView front = itemView.findViewById(R.id.cardfront);


        ViewHolder(View itemView) {
            super(itemView);
            myEasyFlipView = itemView.findViewById(R.id.flippingcard);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myEasyFlipView.flipTheView();
            if (mClickListener != null) {
                mClickListener.onItemClick(myEasyFlipView, mData[getAdapterPosition()]);
            }
        }
    }

    // convenience method for getting data at click position
    Integer getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(EasyFlipView view, int position);
    }
}