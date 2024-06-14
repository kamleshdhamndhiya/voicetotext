package com.test.speechtotext.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.speechtotext.MainActivity;
import com.test.speechtotext.R;
import com.test.speechtotext.model.newModel.Child;
import com.test.speechtotext.model.newModel.Product.Modifier;

import java.util.List;

public class ModifierAdapter extends RecyclerView.Adapter<ModifierAdapter.MyViewHolder> {

    Context context;
    List<Modifier> CustomerLists;
    private int openPosition = -1;

    public ModifierAdapter(Context context, List<Modifier> customerList) {
        this.context = context;
        this.CustomerLists = customerList;


    }

    @Override
    public ModifierAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sizes_layout, parent, false);
        return new ModifierAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ModifierAdapter.MyViewHolder holder, final int position) {
        int Position = position + 1;
        Modifier itemSelected = CustomerLists.get(position);
        if (openPosition == position) {
            holder.inner_item_layout.setVisibility(View.VISIBLE);}
        else {
            holder.inner_item_layout.setVisibility(View.GONE);
        }

        if (itemSelected.getName() != null && !itemSelected.getName().equals("")) {
            holder.address_tv.setText("" + Position + ". " + itemSelected.getName());
        }

        if (itemSelected.getChildren() != null && itemSelected.getChildren().size() > 0) {
            holder.right_aarow_img.setVisibility(View.VISIBLE);

            if (itemSelected.getChildren().size() > 0) {
                holder.item_rcv.setVisibility(View.VISIBLE);
                ModifierItems_Adapter mainMenuAdapter = new ModifierItems_Adapter(context, itemSelected.getChildren());
                holder.item_rcv.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                holder.item_rcv.setItemAnimator(new DefaultItemAnimator());
                holder.item_rcv.scheduleLayoutAnimation();
                holder.item_rcv.setNestedScrollingEnabled(false);
                holder.item_rcv.setAdapter(mainMenuAdapter);
                holder.item_rcv.setHasFixedSize(true);
                mainMenuAdapter.notifyDataSetChanged();
            }
        } else {
            holder.right_aarow_img.setVisibility(View.GONE);
        }

        holder.right_aarow_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPosition = position;
                notifyDataSetChanged();

               /* if (openPosition == position) {
                    holder.inner_item_layout.setVisibility(View.VISIBLE);
                    notifyItemChanged(position);
                } else {
                    if (openPosition != position) {
                        holder.inner_item_layout.setVisibility(View.GONE);
                        notifyItemChanged(openPosition);
                    }
                    notifyItemChanged(position);
                    openPosition = position;
                }*/
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return CustomerLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address_tv;
        TextView subcategory_tv;
        ImageView right_aarow_img;
        LinearLayout inner_item_layout;
        RecyclerView item_rcv;


        public MyViewHolder(View view) {
            super(view);

            address_tv = view.findViewById(R.id.address_tv);
            subcategory_tv = view.findViewById(R.id.subcategory_tv);
            right_aarow_img = view.findViewById(R.id.right_aarow_img);
            inner_item_layout = view.findViewById(R.id.inner_item_layout);
            item_rcv = view.findViewById(R.id.item_rcv);

        }
    }


}