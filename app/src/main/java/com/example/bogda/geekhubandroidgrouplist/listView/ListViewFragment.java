package com.example.bogda.geekhubandroidgrouplist.listView;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.data.People;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.bogda.geekhubandroidgrouplist.service.RealmPeoples.*;


//TODO: Refactor
public class ListViewFragment extends Fragment implements SearchView.OnQueryTextListener {
    Realm realm;
    SearchView searchView;
    ListView listView;
    ArrayList<People> peoples;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        final View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);
        peoples = new ArrayList<People>();


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

        Collections.sort(peoples);

        listView = (ListView) rootView.findViewById(R.id.data_list_view);
        final PeopleAdapter adapter = new PeopleAdapter(getActivity(), peoples);
        listView.setClickable(true);
        setSwipeAdapter(adapter);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
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
        ArrayList<People> searchPeoples = new ArrayList<People>();
        PeopleAdapter adapter = new PeopleAdapter(getActivity(), searchPeoples);
        for(People p : peoples){
            if(p.getName().toLowerCase().contains(query.toLowerCase())){
                searchPeoples.add(p);
            }
            listView.setAdapter(adapter);
        }
        if(query.equals("")){
            adapter = new PeopleAdapter(getActivity(), peoples);
            setSwipeAdapter(adapter);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        onQueryTextSubmit(newText);
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void setSwipeAdapter(final PeopleAdapter adapter){
        final SwipeActionAdapter swipeAdapter = new SwipeActionAdapter(adapter);
        swipeAdapter.setListView(listView);
        listView.setAdapter(swipeAdapter);
        swipeAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {
            @Override
            public boolean hasActions(int position, SwipeDirection direction) {
                if (direction.isLeft()) return true;
                if (direction.isRight()) return true;
                return false;
            }

            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction) {
                return true;
            }

            @Override
            public void onSwipe(int[] position, SwipeDirection[] direction) {
                final int curPos = position[0];
                final People people = peoples.get(curPos);
                peoples.remove(curPos);
                swipeAdapter.notifyDataSetChanged();
                Snackbar.make(getView(), people.getName() + " deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                peoples.add(curPos, people);
                                swipeAdapter.notifyDataSetChanged();
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });
    }
}
