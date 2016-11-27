package com.example.bogda.geekhubandroidgrouplist.listView;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.data.People;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.ArrayList;
import java.util.Collections;

public class ListViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        final ArrayList<People> peoples = new ArrayList<People>();

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

        Collections.sort(peoples);

        final ListView listView = (ListView) rootView.findViewById(R.id.data_list_view);
        final PeopleAdapter adapter = new PeopleAdapter(getActivity(), peoples);
        listView.setAdapter(adapter);
        listView.setClickable(true);

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

                Snackbar.make(rootView, people.getName() + " deleted", Snackbar.LENGTH_LONG)
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

        return rootView;
    }

}
