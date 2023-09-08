package com.example.clearlink

import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.clearlink.adapter.ContactListAdapter

class MyItemTouchHelperCallback(private val adapter: ContactListAdapter) : ItemTouchHelper.Callback() {

    var TAG = "PhoneBookListItemHelper"

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        Log.d(TAG, "Orign position : $dX,$dY")
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val view = (viewHolder as ContactListAdapter.ViewHolder).swipeLayout
            getDefaultUIUtil().onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive)
            Log.d(TAG, "moved position : $dX,$dY")
        }
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)

        getDefaultUIUtil().clearView((viewHolder as ContactListAdapter.ViewHolder).swipeLayout)
    }


    // 아이템을 스와이프할 수 있는지 여부를 지정
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.RIGHT // 스와이프 방향 설정
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    // 아이템을 드래그할 때 호출되는 메서드
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    // 아이템을 스와이프할 때 호출되는 메서드
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if(direction == ItemTouchHelper.RIGHT){
            val position = viewHolder.adapterPosition
            adapter.onItemSwipe(position, viewHolder.itemView.context)
        }
    }
}