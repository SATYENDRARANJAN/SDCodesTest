package graycode.sdproject.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.nfc.cardemulation.OffHostApduService;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import graycode.sdproject.Constants;
import graycode.sdproject.R;
import graycode.sdproject.SharedPreferencesManager;
import graycode.sdproject.Util;
import graycode.sdproject.dto.User;
import graycode.sdproject.dto.UserDTO;

/**
 * Created by Satyendra.Ranjan on 14-08-2018.
 */

public class UserDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_EVEN = 0;
    private static final int ITEM_ODD = 1;
    private static final int LOADING = 2;

    List<User> users;
    private Context mContext;
    private boolean isLoading;

    public UserDetailsAdapter(Context context) {
        mContext =context;
        users = new ArrayList<>();
    }

    public void setUsers(List<User> users){
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM_EVEN:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case ITEM_ODD:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                viewHolder = getLoadingHolder(parent, inflater);
                break;
        }
        return viewHolder;
    }

    private RecyclerView.ViewHolder getLoadingHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder holder;
        View view = inflater.inflate(R.layout.item_progress, parent, false);
        holder = new LoadingHolder(view);
        return holder;
    }

    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder holder;
        View view = inflater.inflate(R.layout.row, parent, false);
        holder = new UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = users.get(position);
        final UserViewHolder uvh = (UserViewHolder) holder;
        if (!TextUtils.isEmpty(user.getName()))
            uvh.userName.setText(user.getName());
        //Load image via glide in Profile_image
        if(uvh.profileImage!=null)
        Glide.with(uvh.profileImage.getContext())
                .load(user.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .into(uvh.profileImage);

        switch (getItemViewType(position)) {
            case ITEM_EVEN:
                uvh.setIsRecyclable(false);
                addEvenViewsConstraint(user.getItems(), uvh, position);
                break;
            case ITEM_ODD:
                //get list items
                //add odd no. of views to linearlayout dynamically
                uvh.setIsRecyclable(false);
               addOddViewsConstraint(user.getItems(),uvh,position);
                break;
            case LOADING:
                break;
        }
    }

    private void addOddViewsConstraint(List<String> items, UserViewHolder uvh, int position) {
        LinearLayout parentLayout  = (LinearLayout)uvh.linearLayout;
        //create a view and add it to the linear layout
        View viewFirstOdd = View.inflate(mContext,R.layout.row_odd_first,null);
            parentLayout.addView(viewFirstOdd);
        ImageView imageFirstOdd = viewFirstOdd.findViewById(R.id.image_odd1);
        Glide.with(imageFirstOdd.getContext()).load(items.get(0)).apply(new RequestOptions().centerCrop()
                .placeholder(R.drawable.placeholder)).into(imageFirstOdd);


        for(int i= 1;i<items.size();i=i+2){
            View view = View.inflate(mContext,R.layout.row_even,null);
            parentLayout.addView(view);
            ImageView imageView1 = view.findViewById(R.id.image1);
            ImageView imageView2 = view.findViewById(R.id.image2);
            Glide.with(imageView1.getContext()).load(items.get(i)).apply(new RequestOptions().centerCrop()
                    .placeholder(R.drawable.placeholder)).into(imageView1);
            Glide.with(imageView2.getContext()).load(items.get(i+1)).apply(new RequestOptions().centerCrop()
                    .placeholder(R.drawable.placeholder)).into(imageView2);
        }

    }

    private void addEvenViewsConstraint(List<String> items, UserViewHolder uvh, int position) {
        LinearLayout parentLayout  = (LinearLayout)uvh.linearLayout;
        //create a view and add it to the linear layout

        for(int i= 0;i<items.size();i=i+2) {
            View view = View.inflate(mContext,R.layout.row_even,null);
            parentLayout.addView(view);
            ImageView imageView1 = view.findViewById(R.id.image1);
            ImageView imageView2 = view.findViewById(R.id.image2);
            Glide.with(imageView1.getContext()).load(items.get(i)).apply(new RequestOptions().centerCrop()
                    .placeholder(R.drawable.placeholder)).into(imageView1);
            Glide.with(imageView2.getContext()).load(items.get(i + 1)).apply(new RequestOptions().centerCrop()
                    .placeholder(R.drawable.placeholder)).into(imageView2);
        }
    }

    private void addOddViews(List<String> userItems, UserViewHolder holder , int position){
        LinearLayout parenLayout = holder.linearLayout;
        parenLayout.setPadding(2,2,2,2);
        parenLayout.setOrientation(LinearLayout.VERTICAL);

        int screenWidth = Util.getScreenWidth(mContext);
        int imageViewWidth = ((screenWidth-2*(R.dimen.screen_padding))-2)/2;


        //add 0th element as the big square banner
        LinearLayout.LayoutParams childLayoutBannerParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, screenWidth-2*R.dimen.screen_padding );
        LinearLayout childLayoutBanner = new LinearLayout(mContext);
        childLayoutBanner.setLayoutParams(childLayoutBannerParams);
        childLayoutBanner.setOrientation(LinearLayout.HORIZONTAL);

        //add ImageView to 0th Childview
        LinearLayout.LayoutParams bannerParams = new LinearLayout.LayoutParams(
                screenWidth-2*R.dimen.screen_padding ,screenWidth-2*R.dimen.screen_padding );
        ImageView i0= new ImageView(mContext);
        i0.setLayoutParams(bannerParams);
        childLayoutBanner.addView(i0);
        parenLayout.addView(childLayoutBanner);


        for(int i =1; i<userItems.size();i=i+2){
            //add a child linear layout in the outer layout
            LinearLayout childLayout = new LinearLayout(mContext);
            childLayout.setLayoutParams(childLayoutBannerParams);
            childLayout.setOrientation(LinearLayout.HORIZONTAL);

            //add 2 imageViews to childViews
            addTwoChildViews(childLayout,userItems,i,imageViewWidth);
            //add childLayout to ParentLayout
            parenLayout.addView(childLayout);
        }
    }


    private void addEvenViews(List<String> userItems, UserViewHolder holder, int position) {
        //get list items
        //add even no. of views to linearlayout dynamically
        LinearLayout parentLayout = (LinearLayout)holder.linearLayout;
        parentLayout.setPadding(2,2,2,2);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        //get Params
        int screenWidth = Util.getScreenWidth(mContext);

        int imageViewWidth = ((screenWidth-2*((int)(mContext.getResources().getDimension(R.dimen.screen_padding))))-2)/(int)(2*(mContext.getResources().getDisplayMetrics().density));

        for(int i = 0; i<userItems.size(); i=i+2) {
                //add a child linear layout in the outer layout
                LinearLayout.LayoutParams childLayoutpParams = new LinearLayout.LayoutParams(
                        parentLayout.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout childLayout = new LinearLayout(mContext);
                childLayout.setLayoutParams(childLayoutpParams);
                childLayout.setOrientation(LinearLayout.HORIZONTAL);

                //add 2 imageViews to childViews
                addTwoChildViews(childLayout,userItems,i ,imageViewWidth);
                //add childLayout to ParentLayout
                parentLayout.addView(childLayout);

            // Adds the view to the layout
            //layout.addView(image);
            //  parentLayout.addView();
        }
    }

    private void addTwoChildViews(LinearLayout childLayout, List<String> userItems, int i,int imageViewWidth) {
        //show item i and i+1 in 2 image Views
       // int imageViewWidth=childLayout.getWidth()/2-2;
        ImageView i1 = new ImageView(mContext);
        i1.setLayoutParams(new LinearLayout.LayoutParams(imageViewWidth,imageViewWidth,0f));
        View viewLine = new View(mContext);
        viewLine.setLayoutParams(new LinearLayout.LayoutParams(2,imageViewWidth));
        ImageView i2= new ImageView(mContext);
        i2.setLayoutParams(new LinearLayout.LayoutParams(imageViewWidth,imageViewWidth,0f));

        childLayout.addView(i1);
        childLayout.addView(viewLine);
        childLayout.addView(i2);

        Glide.with(i1.getContext()).load(userItems.get(i)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)).into(i1);
        Glide.with(i2.getContext()).load(userItems.get(i+1)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)).into(i2);
    }

    @Override
    public int getItemViewType(int position) {
        if (!isLoading) {
            if (users.get(position).getItems().size() % 2 == 0) {
                return ITEM_EVEN;
            } else {
                return ITEM_ODD;
            }
        } else {
            if (position == users.size() - 1) {
                return LOADING;
            }
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addAll(List<User> users) {
        for (User user : users) {
            add(user);
        }
    }

    public void add(User user){
        users.add(user);
    }

    //ViewHolders
    private class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileImage;
        public TextView userName;
        public LinearLayout linearLayout;

        public UserViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.user_name);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

    private class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(View itemView) {
            super(itemView);
        }
    }

    ///Helper methods to add , addAll , remove
}
