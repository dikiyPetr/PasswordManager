package com.example.dikiy.passwordmain.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

        public interface OnItemClickListener {
        public void onItemClick(View view, int position);
            public void onLongClick(View view, int position);
    }

    GestureDetector mGestureDetector;
    GestureDetector mGestureDetector2;

    public RecyclerItemClickListener(Context context, final RecyclerView recycleView, final OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }


            @Override
            public void onLongPress(MotionEvent e) {

                View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && mListener!=null){
                    mListener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                }
            }
    });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {

        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }


        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}