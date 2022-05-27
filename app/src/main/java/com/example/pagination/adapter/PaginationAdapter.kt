package com.example.pagination.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pagination.databinding.ItemDataBinding
import com.example.pagination.databinding.ItemLoadBinding
import com.example.pagination.model.Data
import com.squareup.picasso.Picasso

class PaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<Data>()
    private val LOADING = 0
    private val DATA = 1
    private var isLoadingAdded = false

    inner class LoadingVIewHolder(val itemLoadBinding: ItemLoadBinding) :
        RecyclerView.ViewHolder(itemLoadBinding.root) {
            fun onBind(){
                itemLoadBinding.progressBar.visibility = View.VISIBLE
            }
    }

    inner class DataVIewHolder(val itemDataBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(itemDataBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(data: Data){
            Picasso.get().load(data.avatar).into(itemDataBinding.ivAvatar)
            itemDataBinding.tvFullname.text = "${data.firstName} ${data.lastName}"
            itemDataBinding.tvEmail.text = data.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LOADING) {
            return LoadingVIewHolder(
                ItemLoadBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        return DataVIewHolder(
            ItemDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if(itemViewType == LOADING){
            val loadingHolder = holder as LoadingVIewHolder
            loadingHolder.onBind()
        }else{
            val dataHolder = holder as DataVIewHolder
            dataHolder.onBind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size - 1 && isLoadingAdded) LOADING else DATA
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(otherList : List<Data>){
        list.addAll(otherList)
        notifyDataSetChanged()
    }

    fun editLoading() {
        isLoadingAdded = true
    }

    fun removeLoading() {
        isLoadingAdded = false
    }
}