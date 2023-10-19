package com.example.rv1

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CustomRecyclerAdapter(private val names: List<String>, private val spanCount: Int) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    private var selectedIdx = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.setOnKeyListener { _, _, p2 ->
            var result = false
            p2?.let { keyEvent ->
                recyclerView.layoutManager?.let { lm ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                        if (keyEvent.keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                            result = tryMoveSelection(lm, spanCount)
                        }
                        if (keyEvent.keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                            result = tryMoveSelection(lm, -spanCount)
                        }
                        if (keyEvent.keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                            result = tryMoveSelection(lm, -1)
                        }
                        if (keyEvent.keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                            result = tryMoveSelection(lm, 1)
                        }
                        if (result) {
                            recyclerView.smoothScrollToPosition(selectedIdx)
                        }
                    }
                }
            }
            result
        }
    }

    private fun tryMoveSelection(lm: RecyclerView.LayoutManager, direction: Int): Boolean {
        val prevSelectedIdx = selectedIdx
        val newSelectedIdx = selectedIdx + direction
        if (newSelectedIdx < 0 || newSelectedIdx >= names.size) {
            return false
        }
        if (lm is GridLayoutManager) {
            val fvp = lm.findFirstVisibleItemPosition()
            val lvp = lm.findLastVisibleItemPosition()
            if (fvp != RecyclerView.NO_POSITION && newSelectedIdx < (fvp - spanCount)) {
                return false
            }
            if (lvp != RecyclerView.NO_POSITION && newSelectedIdx > (lvp + spanCount)) {
                return false
            }
        }
        selectedIdx = newSelectedIdx
        notifyItemChanged(prevSelectedIdx);
        notifyItemChanged(selectedIdx);
        return true
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catNameTextView: TextView = itemView.findViewById(R.id.catNameTv)
        val mainCv: CardView = itemView.findViewById(R.id.mainCv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mainCv.isSelected = position == selectedIdx
        holder.catNameTextView.text = names[position]
    }
}