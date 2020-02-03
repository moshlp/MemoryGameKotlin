package com.damianperon.memorygamekotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.damianperon.memorygamekotlin.R
import com.wajahatkarim3.easyflipview.EasyFlipView

class CardsAD(
    context: Context?,
    data: IntArray,
    mClickListener: ItemClickListener?
) : RecyclerView.Adapter<CardsAD.ViewHolder>() {
    private val mData: IntArray
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener?
    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = mInflater.inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val resource = mData[position]
        holder.front.setImageResource(resource)
        //holder.myEasyFlipView.flipTheView();
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var myEasyFlipView: EasyFlipView
        var front =
            itemView.findViewById<ImageView>(R.id.cardfront)

        override fun onClick(view: View) {
            myEasyFlipView.flipTheView()
            if (mClickListener != null) {
                mClickListener!!.onItemClick(myEasyFlipView, mData[adapterPosition])
            }
        }

        init {
            myEasyFlipView = itemView.findViewById(R.id.flippingcard)
            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Int {
        return mData[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: EasyFlipView?, position: Int)
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        mData = data
        this.mClickListener = mClickListener
    }
}