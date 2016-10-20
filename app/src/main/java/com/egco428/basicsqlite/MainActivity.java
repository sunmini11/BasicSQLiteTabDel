package com.egco428.basicsqlite;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private CommentsDataSource dataSource;
    EditText firstN;
    EditText LastN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new CommentsDataSource(this);
        dataSource.open();
        List<Comment> values = dataSource.getAllComments();
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);
        firstN = (EditText)findViewById(R.id.editFirst);
        LastN = (EditText)findViewById(R.id.editLast);
        String name = firstN.getText().toString() + LastN.getText().toString();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>)getListAdapter();
        Comment comment = null;

        if(getListAdapter().getCount()>0){
            comment = (Comment)getListAdapter().getItem(position);
            dataSource.deleteComment(comment);
            adapter.remove(comment);
        }
    }


    public void onClick(View view){
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>)getListAdapter();
        Comment comment = null;
        switch (view.getId()){
            case R.id.addBtn:
                String[] comments = new String[] {"Good","Cool", "Whatever", "Very nice"};
                int nextInt = new Random().nextInt(4); //Random comments
                String name = firstN.getText().toString() + " " + LastN.getText().toString();
                comment = dataSource.createComment(name);
                adapter.add(comment);
                break;
//            case R.id.deleteBtn:
//                if(getListAdapter().getCount()>0){
//                    comment = (Comment)getListAdapter().getItem(0);
//                    dataSource.deleteComment(comment);
//                    adapter.remove(comment);
//                }
//                break;
        }
        adapter.notifyDataSetChanged(); //refresh for updating values
    }

    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onPause();
    }
}
