package com.project.deliveryapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.deliveryapp.R;
import com.project.deliveryapp.model.Menu;
import com.project.deliveryapp.model.RestaurantModel;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<Menu> menuList;
    private MenuListClickListener clickListener;

    public MenuListAdapter(List<Menu> menuList, MenuListClickListener clickListener) {
        this.menuList = menuList;
        this.clickListener = clickListener;
    }

    public void updateData(List<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_rec_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListAdapter.MyViewHolder holder, int position) {
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Price: $" + menuList.get(position).getPrice());
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = menuList.get(position);
                menu.setTotalInCart(1);
                clickListener.onAddToCartClick(menu);
                holder.addMoreLayout.setVisibility(View.VISIBLE);
                holder.addToCartButton.setVisibility(View.GONE);
                holder.tvCount.setText(menu.getTotalInCart() + "");
            }
        });

        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = menuList.get(position);
                int total = menu.getTotalInCart();
                total--;
                if (total > 0) {
                    menu.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menu);
                    holder.tvCount.setText(total + "");
                } else {
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                    menu.setTotalInCart(total);
                    clickListener.onRemoveFromCartClick(menu);
                }
            }
        });

        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = menuList.get(position);
                int total = menu.getTotalInCart();
                total++;
                if (total <= 10) {
                    menu.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menu);
                    holder.tvCount.setText(total + "");
                }
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickListener.onAddToCartClick(menuList.get(position));
//            }
//        });
        Glide.with(holder.thumbImage)
                .load(menuList.get(position).getUrl())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView menuName;
        TextView menuPrice;
        TextView addToCartButton;
        ImageView thumbImage;
        LinearLayout addMoreLayout;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView tvCount;

        public MyViewHolder(View view) {
            super(view);

            menuName = view.findViewById(R.id.menu_name);
            menuPrice = view.findViewById(R.id.menu_price);
            addToCartButton = view.findViewById(R.id.add_to_cart_button);
            thumbImage = view.findViewById(R.id.thumb_image);
            addMoreLayout = view.findViewById(R.id.add_more_layout);
            tvCount = view.findViewById(R.id.tv_count);
            imageMinus = view.findViewById(R.id.image_minus);
            imageAddOne = view.findViewById(R.id.image_add);
        }
    }

    public interface MenuListClickListener {
        public void onAddToCartClick(Menu menu);

        public void onUpdateCartClick(Menu menu);

        public void onRemoveFromCartClick(Menu menu);
    }
}
