package pro.kidsgaurd;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NotblockappsActivity extends AppCompatActivity {
    ListView lstnotblock;

    ArrayList<String> namee=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_notblockapps);
        final Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final ArrayList<String> apps = (ArrayList<String>) args.getSerializable("notblock");
        int i=0;
        while (i < apps.size()) {
            String[] nam=apps.get(i).split("\\.");
            int length=nam.length;
            namee.add(nam[length-1]);
            i++;
        }
        lstnotblock=(ListView)findViewById(pro.kidsgaurd.R.id.lstntblckapp);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,namee);
        lstnotblock.setAdapter(adapter);
        AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent1=new Intent(NotblockappsActivity.this,blockTimeActivity.class);
                intent1.putExtra("appname",apps.get(i));
                startActivity(intent1);
            }
        };
        lstnotblock.setOnItemClickListener(onItemClickListener);
    }
}
