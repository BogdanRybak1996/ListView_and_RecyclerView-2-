package com.example.bogda.geekhubandroidgrouplist.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bogda.geekhubandroidgrouplist.data.People;
import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.userInfoActivity.GitHubUserInfoActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.bogda.geekhubandroidgrouplist.service.OnlineChecker.isOnline;

/**
 * Created by bogda on 29.10.2016.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyHolder> {
    ArrayList<People> peoples;
    Context context;
    private OnItemClickListener clickListener;

    public PeopleAdapter(Context context, ArrayList<People> peoples) {
        this.context = context;
        this.peoples = peoples;
    }

    @Override
    public int getItemCount() {
        return peoples.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.nameTextView.setText(peoples.get(position).getName());
        final People people = peoples.get(position);
        holder.gitHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline(context)) {
                    Intent intent = new Intent(context, GitHubUserInfoActivity.class);
                    intent.setData(Uri.parse("https://github.com/" + people.getGitHubUserName()));
                    intent.putExtra("name", people.getName());
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context,"Check internet connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public Button gitHubButton;

        public MyHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.item_name_textview);
            gitHubButton = (Button) itemView.findViewById(R.id.item_git_button);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

}
