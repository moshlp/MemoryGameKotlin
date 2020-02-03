package com.damianperon.memorygamekotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.damianperon.memorygamekotlin.R
import com.wajahatkarim3.easyflipview.EasyFlipView


class CardsAdapter(
    private val list: List<Int>?,
    val context: Context) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var myEasyFlipView: EasyFlipView? = itemView.findViewById(R.id.flippingcard)
        lateinit var itemClickListener : ItemClickListener
        var front = itemView.findViewById(R.id.cardfront)as ImageView

        override fun onClick(v: View?) {
            myEasyFlipView!!.flipTheView();
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list?.get(position)
        item.let { holder.front.setImageResource(it!!) }
        holder.itemView.setOnClickListener{v ->
            holder.myEasyFlipView!!.flipTheView()
            if (holder.itemClickListener != null) {
                holder.itemClickListener.onItemClick(holder.myEasyFlipView, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card, parent, false))
    }


    interface ItemClickListener {
        fun onItemClick(item: View?, itemView: Int)
    }

}