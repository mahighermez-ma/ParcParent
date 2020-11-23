package pro.kidsgaurd;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BlockappsActivity extends AppCompatActivity {
    ListView lstblockapps;
    ArrayList<String> namee=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_blockapps);
        lstblockapps=(ListView)findViewById(pro.kidsgaurd.R.id.lstblockapps);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final ArrayList<String> block = (ArrayList<String>) args.getSerializable("block");
        if (block.size()>0){
        int i=0;
        while (i < block.size()) {
            String[] nam=block.get(i).split("\\.");
            int length=nam.length;

            namee.add(nam[length-1]);
            i++;
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,namee);
        lstblockapps.setAdapter(adapter);
        AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String appnam=block.get(i);
                Toast.makeText(BlockappsActivity.this, "the block of"+appnam+"is cancel", Toast.LENGTH_LONG).show();

                blockAppdb blockAppdb=new blockAppdb(BlockappsActivity.this);
                blockAppdb.Insertjs(appnam+":"+"0"+":"+" "+":"+" ");

            }
        };
        lstblockapps.setOnItemClickListener(onItemClickListener);
        }else {
            Toast.makeText(this, "You don't have any blocked app", Toast.LENGTH_LONG).show();
        }
    }
}
