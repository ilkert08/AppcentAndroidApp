package com.ilkerdev.appcentproject

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.ilkerdev.appcentproject.databaseWork.DbItem
import com.ilkerdev.appcentproject.databaseWork.GameDB
import com.ilkerdev.appcentproject.repository.Repository


//Oyun detaylarinin gosterildigi sayfanin kotlin dosyasi.

class DetailsPage : AppCompatActivity() {
    private lateinit var viewModel: GameListViewModel
    private lateinit var likedGamesList: List<DbItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_page)
        val gameId = intent.getStringExtra("gameId") //Onceki fragmentlardan gelen oyunId'si degiskeni.
        //Bu degisken sayesinde burada detay apisi id'ye gore cagrilabilir.

        val likedGamesDB = GameDB.getLikedGamesDB(this)
        //Begenme butonunun rengini ayarlamak icin begenilen oyun listesini cekmek icin veritabani degiskeni.


        //Sayfa bilesenlerini tutan degiskenler.
        val tvGameName : TextView = findViewById(R.id.gameName)
        val tvGameRate : TextView = findViewById(R.id.gameRate)
        val tvReleaseDate : TextView = findViewById(R.id.releaseDate)
        val tvGameDetail : TextView = findViewById(R.id.gameDetailString)
        val gameImage : ImageView = findViewById(R.id.game_image_details)
        val likeButton : Button = findViewById(R.id.like_button)


        val repository = Repository()
        val viewModelFactory = GameListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameListViewModel::class.java)
        if (gameId != null) {
            viewModel.getGameDetails(gameId = gameId)  //GameId'sine gore detay apisine istek yapan fonksiyon.
        }


        //Viewmodele gore sayfadaki detay bolumlerinin ayarlandigi kodlar.
        viewModel.myDetailRespone.observe(this, Observer { response ->
            if(response.isSuccessful){
                response.body()?.let {
                    tvGameName.text = it.name
                    tvGameDetail.text = it.description //.removeSurrounding("<p>", "</p>") //Html taglerini kaldırır.
                    tvGameDetail.text = tvGameDetail.text.toString().replace("<p>", "")
                    tvGameDetail.text = tvGameDetail.text.toString().replace("</p>", "\n")
                    tvGameDetail.text = tvGameDetail.text.toString().replace("<br />", "\n")
                    tvGameDetail.movementMethod = ScrollingMovementMethod()

                    tvReleaseDate.text = "Release Date: " + it.released
                    tvGameRate.text = "Metacritic Rate: " + it.metacritic.toString()
                    gameImage.load(it.background_image)
                }
            }else{
                Log.d("Response Error: ", response.errorBody().toString())
            }
        })

        var isLiked : Boolean = false
        likedGamesList = likedGamesDB?.gameDao()?.getAllLikedGames()!!

        for(item in likedGamesList){
            if(item.gameId == gameId){
                isLiked = true
                break
            }
        }


        if(isLiked){
            val drawable = ContextCompat.getDrawable(baseContext, R.drawable.liked_game_icon)
            likeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
        }

        if(!isLiked){
            val drawable = ContextCompat.getDrawable(baseContext, R.drawable.disliked_game_icon)
            likeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
        }

        likeButton.setOnClickListener {

            isLiked = false
            likedGamesList = likedGamesDB.gameDao().getAllLikedGames()
            for(item in likedGamesList){
                if(item.gameId == gameId){
                    isLiked = true
                    break
                }
            }

            if(gameId != null){
                if(isLiked){  //Begenme butonu ayarlaniyor. Kisi begendiyse ve tekrar tikladiysa begenmedi olarak isaretleniyor.
                    likedGamesDB.gameDao().delete(DbItem(gameId)) //Oyun veritabanından siliniyor.
                    val drawable = ContextCompat.getDrawable(baseContext, R.drawable.disliked_game_icon)
                    likeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
                }
            }else{
                Log.d("Kaydetme hatasi: ", "GameId Null")
            }

            if(gameId != null){
                if(!isLiked){  //Kisi begenmediyse ve tekrar tikladiysa begendi olarak isaretleniyor.
                    likedGamesDB.gameDao().insert(DbItem(gameId))  //Oyun veritabanina ekleniyor.
                    val drawable = ContextCompat.getDrawable(baseContext, R.drawable.liked_game_icon)
                    likeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)

                }
            }else{
                Log.d("Kaydetme hatasi: ", "GameId Null")
            }

            //likedGamesList = likedGamesDB.gameDao().getAllLikedGames()

        }


    }
}