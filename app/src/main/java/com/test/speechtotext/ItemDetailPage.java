package com.test.speechtotext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.test.speechtotext.adapter.InnerProductItemAdapter;
import com.test.speechtotext.adapter.InnerProductsItemsAdapter;
import com.test.speechtotext.adapter.MainMeuChildrenAdapter;
import com.test.speechtotext.adapter.SecondCategoryAdapter;
import com.test.speechtotext.adapter.SecondCategoryProductAdapter;
import com.test.speechtotext.adapter.SecondSubCategoryAdapter;
import com.test.speechtotext.adapter.SecondSubProductAdapter;
import com.test.speechtotext.databinding.ActivityItemDetailPageBinding;
import com.test.speechtotext.databinding.ActivityMainBinding;
import com.test.speechtotext.model.newModel.Child;
import com.test.speechtotext.model.newModel.Menu;

public class ItemDetailPage extends AppCompatActivity {
    ActivityItemDetailPageBinding binding;
    InnerProductItemAdapter innerProductItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_item_detail_page);
        binding = ActivityItemDetailPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("main_menu") != null) {
                Menu itemSelected = (Menu) bundle.getSerializable("main_menu");

                if (itemSelected.getChildren().size() > 0) {
                    binding.titleTv.setText("Menu SubCategory");
                    innerProductItemAdapter = new InnerProductItemAdapter(ItemDetailPage.this, itemSelected.getChildren());
                    binding.itemRcv.setLayoutManager(new LinearLayoutManager(ItemDetailPage.this, RecyclerView.VERTICAL, false));
                    binding.itemRcv.setItemAnimator(new DefaultItemAnimator());
                    binding.itemRcv.scheduleLayoutAnimation();
                    binding.itemRcv.setNestedScrollingEnabled(false);
                    binding.itemRcv.setAdapter(innerProductItemAdapter);
                    binding.itemRcv.setHasFixedSize(true);
                    innerProductItemAdapter.notifyDataSetChanged();
                }else if (itemSelected.getProducts().size() > 0) {
                    binding.titleTv.setText("Menu SubCategory Product");
                    InnerProductsItemsAdapter innerProductItemAdapter = new InnerProductsItemsAdapter(ItemDetailPage.this, itemSelected.getProducts());
                    binding.itemRcv.setLayoutManager(new LinearLayoutManager(ItemDetailPage.this, RecyclerView.VERTICAL, false));
                    binding.itemRcv.setItemAnimator(new DefaultItemAnimator());
                    binding.itemRcv.scheduleLayoutAnimation();
                    binding.itemRcv.setNestedScrollingEnabled(false);
                    binding.itemRcv.setAdapter(innerProductItemAdapter);
                    binding.itemRcv.setHasFixedSize(true);
                    innerProductItemAdapter.notifyDataSetChanged();
                }
            }

            else if (bundle.getSerializable("main_menu_child") != null) {
                Child itemSelected = (Child) bundle.getSerializable("main_menu_child");

                if (itemSelected.getChildren().size() > 0) {
                    binding.titleTv.setText("Items");
                    SecondCategoryAdapter innerProductItemAdapter = new SecondCategoryAdapter(ItemDetailPage.this, itemSelected.getChildren());
                    binding.itemRcv.setLayoutManager(new LinearLayoutManager(ItemDetailPage.this, RecyclerView.VERTICAL, false));
                    binding.itemRcv.setItemAnimator(new DefaultItemAnimator());
                    binding.itemRcv.scheduleLayoutAnimation();
                    binding.itemRcv.setNestedScrollingEnabled(false);
                    binding.itemRcv.setAdapter(innerProductItemAdapter);
                    binding.itemRcv.setHasFixedSize(true);
                    innerProductItemAdapter.notifyDataSetChanged();
                }else if (itemSelected.getProducts().size() > 0) {
                    binding.titleTv.setText("Items");
                    SecondCategoryProductAdapter innerProductItemAdapter = new SecondCategoryProductAdapter(ItemDetailPage.this, itemSelected.getProducts());
                    binding.itemRcv.setLayoutManager(new LinearLayoutManager(ItemDetailPage.this, RecyclerView.VERTICAL, false));
                    binding.itemRcv.setItemAnimator(new DefaultItemAnimator());
                    binding.itemRcv.scheduleLayoutAnimation();
                    binding.itemRcv.setNestedScrollingEnabled(false);
                    binding.itemRcv.setAdapter(innerProductItemAdapter);
                    binding.itemRcv.setHasFixedSize(true);
                    innerProductItemAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}