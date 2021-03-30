package com.ilkerdev.appcentproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ilkerdev.appcentproject.*
import com.ilkerdev.appcentproject.model.ResultsItem
import com.ilkerdev.appcentproject.repository.Repository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {  //Api'den cekilen oyunları gosteren ana fragment.

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: GameListViewModel
    private val gameListAdapter by lazy {
        GameListAdapter() //Oyunları gosteren recyclerview'in adapter'i yukleniyor.
    }

    private val imageViewPagerAdapter  by lazy{
        ImageViewPagerAdapter() //Oyunlarıi gosteren viewpager adapteri yukleniyor.
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

        //Fragmenttaki bölümlerin referanslari.
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val rv : RecyclerView = view.findViewById(R.id.game_list_recyclerview)
        val vp : ViewPager2 = view.findViewById(R.id.viewPager2)
        val sv : SearchView = view.findViewById(R.id.searchView)
        val tv : TextView = view.findViewById(R.id.noRecordTextView)
        //

        setupRecycleView(rv)  //Recyclerview adapteri ayarlaniyor.
        setupViewPager(vp)    //Viewpager adapteri ayarlaniyor.

        val repository = Repository()
        val viewModelFactory = GameListViewModelFactory(repository) //Viewmodel ayarlaniyor.
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameListViewModel::class.java)
        viewModel.getGames() //Oyunlar apiden cekiliyor.
        viewModel.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){  //Response basarili ise recyclerview ve viewpagera veri atamalari yapiliyor.
                response.body()?.let {
                    val viewPagerList: List<ResultsItem> = listOf(
                            it.results[0],
                            it.results[1],
                            it.results[2])
                    context?.let { it1 -> imageViewPagerAdapter.setData(viewPagerList, it1) }
                    context?.let { it1 -> gameListAdapter.setData(it.results, it1) }



                    sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {  //Searchview arama sartlari tanımlaniyor.

                        override fun onQueryTextChange(newText: String): Boolean {
                            if (newText.length > 2){ //Eger query string boyutu 2'den buyukse arama yapilir.

                                val params = rv.layoutParams as ConstraintLayout.LayoutParams
                                params.topToBottom = sv.id
                                rv.requestLayout()
                                vp.visibility = View.INVISIBLE

                                val filteredResults = mutableListOf<ResultsItem>()
                                for(item in it.results){
                                    if(item.name.toLowerCase().contains(newText.toLowerCase())){
                                        filteredResults.add(item)
                                    }
                                }
                                gameListAdapter.filterData(filteredResults)

                                if(filteredResults.size == 0){
                                    tv.visibility = View.VISIBLE
                                    rv.visibility = View.INVISIBLE
                                }else{
                                    tv.visibility = View.INVISIBLE
                                    rv.visibility = View.VISIBLE
                                }

                            }else{

                                var params = rv.layoutParams as ConstraintLayout.LayoutParams
                                params.topToBottom = vp.id
                                rv.requestLayout()
                                vp.visibility = View.VISIBLE
                                rv.visibility = View.VISIBLE
                                tv.visibility = View.INVISIBLE

                                context?.let { it1 -> gameListAdapter.setData(it.results, it1) }
                            }
                            return false
                        }

                        override fun onQueryTextSubmit(query: String): Boolean {
                            // task HERE
                            return false
                        }

                    })


                }
            }else{
                Log.d("Response Error: ", response.errorBody().toString())
            }
        })

        sv.setOnClickListener(View.OnClickListener { sv.isIconified = false }) //Searchview'in herhangi bir yerine tiklandiginda arama islemi yapılmasini saglar.





        // Inflate the layout for this fragment
        return view
    }

    private fun setupRecycleView(rv: RecyclerView){
        rv.adapter = gameListAdapter
        rv.layoutManager = LinearLayoutManager(context)
    }

    private fun setupViewPager(vp: ViewPager2){
        vp.adapter = imageViewPagerAdapter
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}



