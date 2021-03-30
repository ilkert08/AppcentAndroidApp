package com.ilkerdev.appcentproject.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilkerdev.appcentproject.GameListAdapter
import com.ilkerdev.appcentproject.R
import com.ilkerdev.appcentproject.GameListViewModel
import com.ilkerdev.appcentproject.GameListViewModelFactory
import com.ilkerdev.appcentproject.databaseWork.GameDB
import com.ilkerdev.appcentproject.model.ResultsItem
import com.ilkerdev.appcentproject.repository.Repository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: GameListViewModel //Viewmodel tanimi


    private val gameListAdapter by lazy {
        GameListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        setupFavoriteList(view) //Favori oyunlar listesi adapteri ayarlaniyor.

        return view
    }


    private fun setupFavoriteList(view: View){
        val rv : RecyclerView = view.findViewById(R.id.favorite_list_recyclerview)

        val ctx: Context? = context
        val likedGamesDB = ctx?.let { GameDB.getLikedGamesDB(it) }  //Yerel veritabanindan daha once begenilen oyun listesi aliniyor.

        setupRecycleView(rv) //Recyclerview ayarlari.


        val repository = Repository()
        val viewModelFactory = GameListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameListViewModel::class.java)
        viewModel.getGames()
        viewModel.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){   //Response basarili ise
                response.body()?.let {
                    if (likedGamesDB != null) {
                        val newList = getLikedGames(likedGamesDB, it.results)  //Yerel veritabaninda olan oyunlarin bilgileri apiden aliniyor.
                        gameListAdapter.setDataForFavorites(newList, ctx) //Recyclerview verisi ataniyor.
                    }
                }

            }else{
                Log.d("Response Error: ", response.errorBody().toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        view?.let { setupFavoriteList(it) }  //Geri tusuna basildiginda recyclerview yeniden ayarlaniyor.

    }

    private fun setupRecycleView(rv: RecyclerView){
        rv.adapter = gameListAdapter
        rv.layoutManager = LinearLayoutManager(context)
    }

    private fun getLikedGames(likedGamesDB: GameDB, resultList: List <ResultsItem> ) : MutableList<ResultsItem>{
        val likedGamesList = likedGamesDB.gameDao().getAllLikedGames()
        val newList : MutableList<ResultsItem> = mutableListOf()

        //Apiden gelen veri ile yerel veritabanindaki listelerden eslesenlerin bilgileri recycler viewe girilmek uzere donduruluyor.
        for(item1 in resultList) {
            for (item2 in likedGamesList) {
                if (item1.id.toString() == item2.gameId) {
                    newList.add(item1)
                }
            }
        }
        return newList
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}