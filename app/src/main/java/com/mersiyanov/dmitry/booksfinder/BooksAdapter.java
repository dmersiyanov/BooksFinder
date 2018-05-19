package com.mersiyanov.dmitry.booksfinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mersiyanov.dmitry.booksfinder.pojo.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private List<Item> itemList = new ArrayList<>();
    private OnBookClickListener clickListener;

    public void clear() {
        final int size = itemList.size();
        itemList.clear();
        notifyItemRangeRemoved(0, size);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(layoutInflater.inflate(R.layout.book_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Item book = itemList.get(position);

        holder.bookTitle.setText(book.getVolumeInfo().getTitle());
        holder.bookDesc.setText(book.getVolumeInfo().getDescription());
        if(book.getVolumeInfo().getImageLinks().getSmallThumbnail() != null) {
            Picasso.get().load(book.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(holder.bookCover);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null) {
                    clickListener.onBookClick(book);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView bookTitle;
        private final ImageView bookCover;
        private final TextView bookDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookDesc = itemView.findViewById(R.id.book_desc);
        }
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void setClickListener(OnBookClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnBookClickListener {
        void onBookClick(Item item);
    }

}
