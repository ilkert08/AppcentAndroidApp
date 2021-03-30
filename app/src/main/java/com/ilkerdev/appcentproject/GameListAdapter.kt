package com.ilkerdev.appcentproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ilkerdev.appcentproject.model.ResultsItem

class GameListAdapter: RecyclerView.Adapter<GameListAdapter.ModelViewHolder>() {

    //Ana sayfa ve favori sayfasindaki recyclerview icin olsturulan adapter.

    private var dataSet = emptyList<ResultsItem>()
    private lateinit var context: Context

    class ModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val gameName: TextView = view.findViewById(R.id.game_name)
        private val gameRatingAndReleaseDate: TextView = view.findViewById(R.id.game_rating_release)
        private val gameImage: ImageView = view.findViewById(R.id.game_image)

        fun bindItems(item: ResultsItem) {
            gameName.text = item.name
            gameRatingAndReleaseDate.text = "${item.rating} - ${item.released}"
            gameImage.load(item.background_image) //Using coil library.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_list_item, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindItems(dataSet[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsPage::class.java).apply {
                putExtra("gameId", dataSet[position].id.toString())
            }
            context.startActivity(intent)
        }

    }

    fun setData(dataSet: List<ResultsItem>, ctx: Context){ //Ana sayfa icin veri ayarlama fonksiyonu.
        this.dataSet = dataSet.subList(3, dataSet.lastIndex) //3. indexten son indexe kadar olanlar. ilk 3 tane view pager'da olacak.
        this.context = ctx
        notifyDataSetChanged()
    }

    fun setDataForFavorites(dataSet: List<ResultsItem>, ctx: Context){ //Favori sayfasi icin veri ayarlama fonksiyonu.
        this.dataSet = dataSet
        this.context = ctx
        notifyDataSetChanged()
    }

    fun filterData(dataSet: List<ResultsItem>){
        this.dataSet = dataSet
        notifyDataSetChanged()
    }



}