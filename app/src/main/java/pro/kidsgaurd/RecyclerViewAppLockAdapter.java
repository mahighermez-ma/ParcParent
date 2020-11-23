package pro.kidsgaurd;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAppLockAdapter extends RecyclerView.Adapter<RecyclerViewAppLockAdapter.ViewHolder> {

    private ArrayList<String> appName;
    private ArrayList<String> pk;
    private ArrayList<Boolean> toggleStatus;
    private ArrayList<String> startTime;
    private ArrayList<String> finishTime;
    private Context context;

    public RecyclerViewAppLockAdapter(Context context, ArrayList<Boolean> togglesStat, ArrayList<String> appsName , ArrayList<String> startsTime, ArrayList<String> finishesTime, ArrayList<String> pk ){
        this.appName = appsName;
        this.toggleStatus = togglesStat;
        this.startTime = startsTime;
        this.finishTime = finishesTime;
        this.context=context;
        this.pk=pk;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViieeww;
        public ViewHolder (CardView v){
            super(v);
            cardViieeww = v;
        }
    }

    @Override
    public RecyclerViewAppLockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(pro.kidsgaurd.R.layout.card_view_toggle, parent , false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAppLockAdapter.ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
        Switch toggleButton = (Switch) cardView.findViewById(pro.kidsgaurd.R.id.togAppLock);
        toggleButton.setChecked(toggleStatus.get(position));

        TextView appNamee = (TextView)cardView.findViewById(pro.kidsgaurd.R.id.txtAppName);
        appNamee.setText(appName.get(position));

        final EditText edtStartTime = (EditText)cardView.findViewById(pro.kidsgaurd.R.id.edtStartTime);
        edtStartTime.setText(startTime.get(position));

        final EditText edtFinishTime = (EditText)cardView.findViewById(pro.kidsgaurd.R.id.edtFinishTime);
        edtFinishTime.setText(finishTime.get(position));

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch toggleButton1 = (Switch)v.findViewById(pro.kidsgaurd.R.id.togAppLock);
                if (toggleButton1.isChecked()) {


                    String ftime=edtStartTime.getText().toString();
                    String stime=edtFinishTime.getText().toString();
                    if (stime.equals("")|| stime.equals(" ")||ftime.equals("")|| ftime.equals(" ")){ftime="0";stime="24";}
                    blockAppdb blockAppdb=new blockAppdb(context);
                    blockAppdb.Insertjs(pk.get(position)+":"+"True"+":"+ftime+":"+stime);

                }else {
                    blockAppdb blockAppdb=new blockAppdb(context);
                    blockAppdb.Insertjs(pk.get(position)+":"+"False"+":"+" "+":"+" ");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return appName.size();
    }


}
