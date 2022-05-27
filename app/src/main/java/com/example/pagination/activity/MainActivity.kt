package com.example.pagination.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagination.adapter.PaginationAdapter
import com.example.pagination.databinding.ActivityMainBinding
import com.example.pagination.model.UserData
import com.example.pagination.network.ApiClient
import com.example.pagination.utils.PaginationScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var paginationAdapter: PaginationAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1
    private var totalPage = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        paginationAdapter = PaginationAdapter()
        linearLayoutManager = LinearLayoutManager(this)
        initiews()
    }

    private fun initiews() {
        binding.rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
        binding.rv.layoutManager = linearLayoutManager
        binding.rv.adapter = paginationAdapter
        loadFirstPage()
    }

    private fun loadFirstPage(){
        ApiClient.apiService.getUsers(currentPage).enqueue(object : Callback<UserData>{
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful){
                    hideProgressBar()
                    paginationAdapter.addAll(response.body()?.data ?: emptyList())

                    if (currentPage <=  totalPage){
                        paginationAdapter.editLoading()
                    }else{
                        isLoading = true
                    }
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {

            }
        })
    }

    private fun loadNextPage(){
        ApiClient.apiService.getUsers(currentPage).enqueue(object : Callback<UserData>{
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful){
                    paginationAdapter.removeLoading()
                    isLoading = false

                    paginationAdapter.addAll(response.body()?.data ?: emptyList())

                    if (currentPage <=  totalPage){
                        paginationAdapter.editLoading()
                    }else{
                        isLoading = true
                    }
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {

            }
        })
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }
}