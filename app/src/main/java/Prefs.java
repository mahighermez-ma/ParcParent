import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;

public class Prefs extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        boolean b=prefs.getBoolean("FIRSTRUN",true);
    }
}
