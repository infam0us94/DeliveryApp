package com.project.deliveryapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.deliveryapp.adapter.MenuListAdapter;
import com.project.deliveryapp.model.Menu;
import com.project.deliveryapp.model.RestaurantModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {

    private List<Menu> menuList = null;
    private MenuListAdapter menuListAdapter;
    private List<Menu> itemsInCartList;
    private int totalItemInCart = 0;
    private TextView buttonCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        RestaurantModel restaurantModel = getIntent().getParcelableExtra("RestaurantModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(restaurantModel.getName());
        actionBar.setSubtitle(restaurantModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        menuList = restaurantModel.getMenus();
        initRecView();

        buttonCheckout = findViewById(R.id.button_checkout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Toast.makeText(RestaurantMenuActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                }
                restaurantModel.setMenus(itemsInCartList);
                Intent i = new Intent(RestaurantMenuActivity.this, PlaceYourOrderActivity.class);
                i.putExtra("RestaurantModel", restaurantModel);
                startActivityForResult(i, 1000);
            }
        });
    }

    private void initRecView() {
        RecyclerView recyclerView = findViewById(R.id.rec_view_in_menu);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuListAdapter = new MenuListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }

    @Override
    public void onAddToCartClick(Menu menu) {
        if (itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(menu);
        totalItemInCart = 0;

        for (Menu m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }
        buttonCheckout.setText("Checkout (" + totalItemInCart + ") items");
    }

    @Override
    public void onUpdateCartClick(Menu menu) {
        if (itemsInCartList.contains(menu)) {
            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, menu);

            totalItemInCart = 0;

            for (Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" + totalItemInCart + ") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if (itemsInCartList.contains(menu)) {
            itemsInCartList.remove(menu);

            totalItemInCart = 0;

            for (Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" + totalItemInCart + ") items");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {

        }
    }
}
