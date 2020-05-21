package com.foobles.kotlinnum.storage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foobles.kotlinnum.databinding.PdfCardItemBinding
import com.google.firebase.storage.StorageReference

class StorageAdapter(val function: (storageReference: StorageReference) -> Unit) :
    RecyclerView.Adapter<StorageAdapter.ViewHolder>() {

    private val storageReferenceArrayList = ArrayList<StorageReference>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PdfCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val storageReference = storageReferenceArrayList[position]
        holder.bind(storageReference, function)
    }

    fun setData(arrayList: MutableList<StorageReference>) {
        storageReferenceArrayList.clear()
        storageReferenceArrayList.addAll(arrayList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = storageReferenceArrayList.size

    class ViewHolder(private val pdfsCardItemBinding: PdfCardItemBinding) :
        RecyclerView.ViewHolder(pdfsCardItemBinding.root) {
        fun bind(
            storageReference: StorageReference,
            function: (storageReference: StorageReference) -> Unit
        ) {
            pdfsCardItemBinding.textviewTitle.text = storageReference.name
            pdfsCardItemBinding.root.setOnClickListener { function(storageReference) }
        }
    }
}