package com.example.appbooking.page.customer;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbooking.R;

import java.util.List;

//public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
//
//    private Context context;
//    private List<String> imageList; // Danh sách đường dẫn ảnh (URL hoặc local path)
//
//    public ImagesAdapter(Context context, List<String> imageList) {
//        this.context = context;
//        this.imageList = imageList;
//    }
//
//    @NonNull
//    @Override
//    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // Inflate layout item_image.xml
//        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
//        return new ImageViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
//        // Lấy đường dẫn ảnh từ danh sách
//        String imagePath = imageList.get(position);
//
//        // Gắn ảnh vào ImageView
//        try {
//            // Kiểm tra nếu ảnh là đường dẫn URL hoặc local
//            if (imagePath.contains("http") || imagePath.contains("www")) {
//                // Nếu là URL, sử dụng thư viện Glide (hoặc Picasso)
//                Glide.with(context).load(imagePath).into(holder.imageView);
//            } else {
//                // Nếu là đường dẫn local, sử dụng URI
//                holder.imageView.setImageURI(Uri.parse(imagePath));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Gắn ảnh mặc định nếu có lỗi
//            holder.imageView.setImageResource(R.drawable.anh_phong);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return imageList.size(); // Số lượng ảnh
//    }
//
//    // ViewHolder class cho từng item
//    public static class ImageViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//
//        public ImageViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.itemImage); // ID trong item_image.xml
//        }
//    }
//}


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
    private Context context;
    private List<String> images;

    public ImagesAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = images.get(position);
        // Giả sử bạn dùng Glide để tải ảnh
        Glide.with(context).load(imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slider_image);
        }
    }
}
