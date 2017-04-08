package id.sch.smktelkom_mlg.project2.xirpl30717233235.expinder.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import id.sch.smktelkom_mlg.project2.xirpl30717233235.expinder.DataHelper;
import id.sch.smktelkom_mlg.project2.xirpl30717233235.expinder.InputActivity;
import id.sch.smktelkom_mlg.project2.xirpl30717233235.expinder.R;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarItem extends Fragment {

    public static final int RESULT_CODE_EDIT = 99;
    public static final int REQUEST_CODE_ADD = 88;
    public static DaftarItem di;
    protected Cursor cursor;
    String[] daftar;
    ListView ListView01;
    DataHelper dbcenter;

    public DaftarItem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_daftar_item, container, false);

        v.findViewById(R.id.fabAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAdd();
            }
        });


        di = this;
        dbcenter = new DataHelper(getActivity());
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RefreshList();
    }

    private void goAdd() {
        startActivityForResult(new Intent(getActivity(), InputActivity.class), REQUEST_CODE_ADD);
    }

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM item ", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView) getView().findViewById(R.id.listView1);
        ListView01.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"See Item", "Delete Item"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Options");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent i = new Intent(getActivity().getApplicationContext(), SeeItem.class);
                                i.putExtra("item", selection);
                                startActivity(i);
                                break;
                            case 1:
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from item where item = '" + selection + "'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetInvalidated();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: list");
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            RefreshList();
        } else if (resultCode == RESULT_CODE_EDIT && resultCode == RESULT_OK) {
            RefreshList();
        }
    }


}



