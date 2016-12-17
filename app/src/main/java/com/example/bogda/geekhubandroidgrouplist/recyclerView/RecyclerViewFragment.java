package com.example.bogda.geekhubandroidgrouplist.recyclerView;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bogda.geekhubandroidgrouplist.data.People;
import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.userInfoActivity.GitHubUserInfoActivity;
import com.example.bogda.geekhubandroidgrouplist.userInfoActivity.GooglePlusUserInfoActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.bogda.geekhubandroidgrouplist.service.OnlineChecker.isOnline;
import static com.example.bogda.geekhubandroidgrouplist.service.RealmPeoples.getAllPeoples;
import static com.example.bogda.geekhubandroidgrouplist.service.RealmPeoples.savePeoples;

/**
 * Created by bogda on 29.10.2016.
 */

public class RecyclerViewFragment extends Fragment implements OnItemClickListener, SearchView.OnQueryTextListener {
    ArrayList<People> peoples = null;
    ArrayList<People> currPeoples = null;
    Realm realm;
    SearchView searchView;
    RecyclerView recyclerView;
    ItemTouchHelper swipeHelper;
    PeopleAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        final View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        peoples = new ArrayList<People>();
        currPeoples = new ArrayList<People>();

        ArrayList<People> tempPeoples = getAllPeoples();
        if (tempPeoples == null) {
            peoples.add(new People("Евгений Жданов", "113264746064942658029", "zhdanov-ek"));
            peoples.add(new People("Edgar Khimich", "102197104589432395674", "lyfm"));
            peoples.add(new People("Alexander Storchak", "106553086375805780685", "new15"));
            peoples.add(new People("Yevhenii Sytnyk", "101427598085441575303", "YevheniiSytnyk"));
            peoples.add(new People("Alyona Prelestnaya", "107382407687723634701", "HelenCool"));
            peoples.add(new People("Богдан Рибак", "103145064185261665176", "BogdanRybak1996"));
            peoples.add(new People("Ірина Смалько", "113994208318508685327", "IraSmalko"));
            peoples.add(new People("Владислав Винник", "117765348335292685488", "vlads0n"));
            peoples.add(new People("Ігор Пахаренко", "108231952557339738781", "IhorPakharenko"));
            peoples.add(new People("Андрей Рябко", "110288437168771810002", "RyabkoAndrew"));
            peoples.add(new People("Ivan Leshchenko", "111088051831122657934", "ivleshch"));
            peoples.add(new People("Микола Піхманець", "110087894894730430086", "NikPikhmanets"));
            peoples.add(new People("Ruslan Migal", "106331812587299981536", "rmigal"));
            peoples.add(new People("Руслан Воловик", "109719711261293841416", "RuslanVolovyk"));
            peoples.add(new People("Valerii Gubskyi", "107910188078571144657", "gvv-ua"));
            peoples.add(new People("Иван Сергеенко", "111389859649705526831", "dogfight81"));
            peoples.add(new People("Вова Лымарь", "109227554979939957830", "VovanNec"));
            peoples.add(new People("Даша Кириченко", "103130382244571139113", "dashakdsr"));
            peoples.add(new People("Michael Tyoply", "110313151428733681846", "RedGeekPanda"));
            peoples.add(new People("Павел Сакуров", "108482088578879737406", "sakurov"));
            savePeoples(peoples);
        }
        else{
            for(People p : tempPeoples){
                peoples.add(p);
            }
        }

        currPeoples = peoples;


        Collections.sort(currPeoples);

        adapter = new PeopleAdapter(getActivity(), currPeoples);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_data_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setClickable(true);

        swipeHelper = new ItemTouchHelper(getTouchHelper(adapter,currPeoples));
        swipeHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }

    @Override
    public void onClick(View view, int position) {
        final People people = currPeoples.get(position);
        if(isOnline(getActivity())) {
            Intent intent = new Intent(getContext(), GooglePlusUserInfoActivity.class);
            intent.setData(Uri.parse("https://plus.google.com/" + people.getGooglePlusId()));
            intent.putExtra("name", people.getName());
            getContext().startActivity(intent);
        }
        else {
            Toast.makeText(getActivity(),"Check internet connection",Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchmenu, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        currPeoples = new ArrayList<People>();
        for(People p : peoples){
            if(p.getName().toLowerCase().contains(query.toLowerCase())) {
                currPeoples.add(p);
            }
        }
        if(query.equals("")){
            currPeoples = peoples;
        }
        adapter = new PeopleAdapter(getActivity(),currPeoples);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        swipeHelper.attachToRecyclerView(null);
        swipeHelper = new ItemTouchHelper(getTouchHelper(adapter,currPeoples));
        swipeHelper.attachToRecyclerView(recyclerView);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        onQueryTextSubmit(newText);
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private ItemTouchHelper.SimpleCallback getTouchHelper(final PeopleAdapter adapter, final ArrayList<People> peoplesItems){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int swipeDir) {
                final int pos = viewHolder.getAdapterPosition();
                final People people = peoplesItems.get(pos);
                adapter.notifyItemRemoved(pos);
                adapter.notifyDataSetChanged();
                peoplesItems.remove(pos);
                peoples.remove(people);
                Snackbar.make(getView(), people.getName() + " deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                peoplesItems.add(pos, people);
                                peoples.add(people);
                                Collections.sort(peoples);
                                adapter.notifyItemRangeInserted(pos, 1);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }

        };

        return  simpleItemTouchCallback;
    }
}
