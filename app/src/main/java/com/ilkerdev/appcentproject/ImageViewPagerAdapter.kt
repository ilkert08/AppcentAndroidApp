package com.ilkerdev.appcentproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ilkerdev.appcentproject.model.ResultsItem

//View pagerda api'den cekilen ilk 3 oyunu gostermek icin olusturulan view pager adapteri.

class ImageViewPagerAdapter() : RecyclerView.Adapter<ImageViewPagerAdapter.ViewPagerHolder>() {
    private var imagesList = emptyList<String>()
    private var resultsList = emptyList<ResultsItem>()
    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return ViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.view_pager_game_image).load(imagesList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsPage::class.java).apply {
                putExtra("gameId", resultsList[position].id.toString())
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun setData(list: List<ResultsItem>, ctx: Context){
        val tempList: List<String> = listOf(  //ilk 3 oyun gosteriliyor.
                list[0].background_image,
                list[1].background_image,
                list[2].background_image)
        imagesList = tempList
        resultsList = list
        context = ctx
        notifyDataSetChanged()
    }


    class ViewPagerHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}