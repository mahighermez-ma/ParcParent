package pro.kidsgaurd;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewGetchildAdapter extends RecyclerView.Adapter<RecyclerViewGetchildAdapter.ViewHolder> {

    private ArrayList<String> childName;
    private ArrayList<Boolean> activeStatus;
    private ArrayList<String> token;
    private int i;
    private Context context;

    public RecyclerViewGetchildAdapter(ArrayList<String> childName, ArrayList<Boolean> activeStatus, ArrayList<String> token, Context context,int i) {
        this.childName = childName;
        this.activeStatus = activeStatus;
        this.token = token;
        this.context = context;
        this.i=i;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViieeww;
        public ViewHolder (CardView v){
            super(v);
            cardViieeww = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(context).inflate(pro.kidsgaurd.R.layout.card_view_getchild, parent , false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
        final Switch toggleButton = (Switch) cardView.findViewById(pro.kidsgaurd.R.id.switchchild);
        toggleButton.setChecked(activeStatus.get(position));

        TextView appNamee = (TextView)cardView.findViewById(pro.kidsgaurd.R.id.txtChildName);
        appNamee.setText(childName.get(position));

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Switch toggleButton1 = (Switch)v.findViewById(pro.kidsgaurd.R.id.switchchild);
                if (toggleButton1.isChecked()) {

                    if (i==1){toggleButton1.setChecked(false);
                    Alert.shows(context,"","At first, you should inactivate another option","ok","");
                    }else {
                    if (token.get(position).equals("1")){
                        Animation shake = AnimationUtils.loadAnimation(context, pro.kidsgaurd.R.anim.shake);
                        cardView.startAnimation(shake);
                        Toast.makeText(context, "Sorry, the kid's device is not active, please active that", Toast.LENGTH_LONG).show();
                        toggleButton1.setChecked(false);
                    }
                    else {
                        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
                        if (ctok.getctoken().equals("12")){
                            ctok.Insertctoken(token.get(position));
                        }else {
                            ctok.Updatectoken(token.get(position));
                            i++;
                        }

                        context.startActivity(new Intent(context,WelcomeActivity.class));
                    }
                }

                }else {
                    i--;
                    if (activeStatus.get(position)){
                        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
                        ctok.delall();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return childName.size();
    }


}


