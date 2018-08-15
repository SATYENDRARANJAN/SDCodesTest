package graycode.sdproject;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import graycode.sdproject.adapter.UserDetailsAdapter;
import graycode.sdproject.dto.User;
import graycode.sdproject.dto.UserDTO;
import graycode.sdproject.presenter.UserDetailPresenter;
import graycode.sdproject.view.UserDetailView;

public class MainActivity extends AppCompatActivity implements UserDetailView {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    UserDetailsAdapter userDetailsAdapter;
    LinearLayoutManager linearLayoutManager;
    SharedPreferencesManager preferencesManager;

    List<User> userList = new ArrayList<>();
    private boolean isLoading = false;
    private boolean isLastPage = false;
    FrameLayout ll_loading;
    ProgressBar progress_Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferencesManager.getInstance(this);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_bar);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        userDetailsAdapter = new UserDetailsAdapter(this);
        //get the items from the api
        //add them to the list
        //add the list to the adapter

        //get Offset and limit

        preferencesManager = SharedPreferencesManager.getInstance(this);
        preferencesManager.setOffset(0);
        final int offset = preferencesManager.getOffset();
        final int limit = preferencesManager.getLimit();
        UserDetailPresenter presenter = new UserDetailPresenter(this);
        presenter.getUserDetails(this, offset, limit);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userDetailsAdapter);

        //progress bar bottom
         ll_loading = findViewById(R.id.frame_progressLoad);
         progress_Loading = findViewById(R.id.load_more_progress);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //find the topmost element and elements visible on screen .
                //if sum > total elements ->
                int visibleCount = linearLayoutManager.getChildCount();
                int totalItem = linearLayoutManager.getItemCount();
                int firstVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (visibleCount + firstVisible >= totalItem) {
                    progress_Loading.setVisibility(View.VISIBLE);
                    ll_loading.setVisibility(View.VISIBLE);
                   if(!isLoading)
                    loadMoreItems(offset,limit);
                }
            }

        });
    }

    private void loadMoreItems(int offset,int limit){
        //fetch the new data with new offset and limit
        // add to adapter
        //notify
        //hide the progressBar
      isLoading =true;
        UserDetailPresenter presenter = new UserDetailPresenter(this);
        presenter.getUserDetails(this, preferencesManager.getOffset(), limit);
    }


    @Override
    public void onResponseSuccess(UserDTO userDTO) {
        int size = userDTO.data.users.size();
        preferencesManager.setOffset(preferencesManager.getOffset()+size);
        userList.addAll(userDTO.data.users);
        userDetailsAdapter.setUsers(userList);
        userDetailsAdapter.notifyDataSetChanged();

        if(progressBar.isShown())
            progressBar.setVisibility(View.GONE);
        if(progress_Loading.isShown())
            progress_Loading.setVisibility(View.GONE);
        isLoading =false;
    }

    @Override
    public void onResponseFailure(String message) {

    }
}
