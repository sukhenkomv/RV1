package com.example.rv1

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomRecyclerAdapter(private val names: List<String>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    var selectedIdx = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.setOnKeyListener { _, _, p2 ->
            var result = false
            p2?.let { keyEvent ->
                recyclerView.layoutManager?.let { lm ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                        if (keyEvent.keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                            result = tryMoveSelection(lm, 1)
                        }
                        if (keyEvent.keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                            result = tryMoveSelection(lm, -1)
                        }
                    }
                }
            }
            result
        }
    }

    private fun tryMoveSelection(lm: RecyclerView.LayoutManager, direction: Int): Boolean {
        val prevSelectedItem = selectedIdx
        val newSelectedIdx = selectedIdx + direction
        if (newSelectedIdx < 0 || newSelectedIdx >= names.size) {
            return false
        }
        selectedIdx = newSelectedIdx
        notifyItemChanged(prevSelectedItem);
        notifyItemChanged(selectedIdx);
        lm.scrollToPosition(selectedIdx);
        return true
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)
        val mainLinearLayout: LinearLayout = itemView.findViewById(R.id.mainLl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mainLinearLayout.isSelected = position == selectedIdx
        holder.largeTextView.text = names[position]
        holder.smallTextView.text = "кот"
    }
}