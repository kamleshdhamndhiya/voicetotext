package com.test.speechtotext.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.speechtotext.R;
import com.test.speechtotext.model.newModel.Child;
import com.test.speechtotext.model.newModel.Product.Product;

import java.util.List;

public class InnerProductItemAdapter extends RecyclerView.Adapter<InnerProductItemAdapter.MyViewHolder> {

    Context context;
    List<Child> CustomerLists;


    public InnerProductItemAdapter(Context context, List<Child> customerList) {
        this.context = context;
        this.CustomerLists = customerList;


    }

    @Override
    public InnerProductItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_adapter, parent, false);
        return new InnerProductItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InnerProductItemAdapter.MyViewHolder holder, final int position) {
        int Position = position + 1;
        Child itemSelected = CustomerLists.get(position);
        holder.right_aarow_img.setVisibility(View.GONE);
        if (itemSelected.getName() != null && !itemSelected.getName().equals("")) {
            holder.address_tv.setText("" + Position + ". " + itemSelected.getName());
        }if (itemSelected.getPrice() != null && !itemSelected.getPrice().equals("")) {
            holder.address_tv.setText("" + holder.address_tv.getText().toString()+" $"+itemSelected.getPrice());
        }

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


        public MyViewHolder(View view) {
            super(view);

            address_tv = view.findViewById(R.id.address_tv);
            subcategory_tv = view.findViewById(R.id.subcategory_tv);
            right_aarow_img = view.findViewById(R.id.right_aarow_img);

        }
    }


}