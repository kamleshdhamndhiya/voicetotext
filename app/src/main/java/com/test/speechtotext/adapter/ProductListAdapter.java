package com.test.speechtotext.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.speechtotext.R;
import com.test.speechtotext.model.newModel.Child;
import com.test.speechtotext.model.newModel.Menu;
import com.test.speechtotext.model.newModel.Product.Modifier;
import com.test.speechtotext.model.newModel.Product.Product;
import com.test.speechtotext.model.newModel.Product.ProductWithQuantity;
import com.test.speechtotext.model.newModel.Product.Size;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    Context context;
    List<Product> CustomerLists;


    public ProductListAdapter(Context context, List<Product> customerList) {
        this.context = context;
        this.CustomerLists = customerList;


    }

    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_adapter, parent, false);
        return new ProductListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductListAdapter.MyViewHolder holder, final int position) {
        int Position = position + 1;
        Product itemSelected = CustomerLists.get(position);

        if (itemSelected.getName() != null && !itemSelected.getName().equals("") && itemSelected.getPrice() != null) {
            holder.address_tv.setText("" + Position + ". " + itemSelected.getName() + "  $" + itemSelected.getPrice());

        }

        if (itemSelected.getSizes() != null && itemSelected.getSize() > 0 || itemSelected.getModifiers() != null && itemSelected.getModifiers().size() > 0) {
            holder.modifier_layout.setVisibility(View.VISIBLE);
            if (itemSelected.getSizes() != null && itemSelected.getSizes().size() > 0) {
                holder.sizes_btn.setVisibility(View.VISIBLE);
            } else {
                holder.sizes_btn.setVisibility(View.GONE);
            }
            if (itemSelected.getModifiers() != null && itemSelected.getModifiers().size() > 0) {
                holder.modifier_btn.setVisibility(View.VISIBLE);
            } else {
                holder.modifier_btn.setVisibility(View.GONE);
            }

        } else {
            holder.modifier_layout.setVisibility(View.GONE);
        }

        holder.modifier_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifier(itemSelected.getModifiers());
            }
        });
        holder.sizes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sizes(itemSelected.getSizes());
            }
        });

    }

    public void modifier(List<Modifier> getModifiers) {

        Dialog updateDialog = new Dialog(context);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setContentView(R.layout.modifiers_category_popup);
        updateDialog.setCanceledOnTouchOutside(false);
        /*WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(updateDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        updateDialog.getWindow().setAttributes(lp);*/


        TextView title_tv = updateDialog.findViewById(R.id.title_tv);
        RecyclerView modifier_rcv = updateDialog.findViewById(R.id.modifier_rcv);

        title_tv.setText("Modifiers");


        ModifierAdapter modifierAdapter = new ModifierAdapter(context, getModifiers);

        modifier_rcv.setLayoutManager(new LinearLayoutManager(context));
        modifier_rcv.setItemAnimator(new DefaultItemAnimator());
        modifier_rcv.scheduleLayoutAnimation();
        modifier_rcv.setNestedScrollingEnabled(false);
        modifier_rcv.setAdapter(modifierAdapter);
        modifier_rcv.setHasFixedSize(true);
        modifier_rcv.setItemViewCacheSize(10);
        modifierAdapter.notifyDataSetChanged();


        // updateDialog.setCancelable(false);
        if (updateDialog != null)
            updateDialog.show();

    }

    public void sizes(List<Size> getSizes) {

        Dialog updateDialog = new Dialog(context);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setContentView(R.layout.modifiers_category_popup);
        updateDialog.setCanceledOnTouchOutside(false);
        /*WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(updateDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        updateDialog.getWindow().setAttributes(lp);*/


        TextView title_tv = updateDialog.findViewById(R.id.title_tv);
        RecyclerView modifier_rcv = updateDialog.findViewById(R.id.modifier_rcv);

        title_tv.setText("Sizes");


        SizesAdapter modifierAdapter = new SizesAdapter(context, getSizes);

        modifier_rcv.setLayoutManager(new LinearLayoutManager(context));
        modifier_rcv.setItemAnimator(new DefaultItemAnimator());
        modifier_rcv.scheduleLayoutAnimation();
        modifier_rcv.setNestedScrollingEnabled(false);
        modifier_rcv.setAdapter(modifierAdapter);
        modifier_rcv.setHasFixedSize(true);
        modifier_rcv.setItemViewCacheSize(10);
        modifierAdapter.notifyDataSetChanged();


        // updateDialog.setCancelable(false);
        if (updateDialog != null)
            updateDialog.show();

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
        LinearLayout modifier_layout;
        Button modifier_btn;
        Button sizes_btn;


        public MyViewHolder(View view) {
            super(view);

            address_tv = view.findViewById(R.id.address_tv);
            subcategory_tv = view.findViewById(R.id.subcategory_tv);
            modifier_layout = view.findViewById(R.id.modifier_layout);
            modifier_btn = view.findViewById(R.id.modifier_btn);
            sizes_btn = view.findViewById(R.id.sizes_btn);

        }
    }


}
