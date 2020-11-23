package pro.kidsgaurd;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class pager extends FragmentStatePagerAdapter {

    int tabCount;

    public pager(FragmentManager fm , int tabCount)
    {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                Login tab1 = new Login();
                return tab1;
            case 1:
                Register tab2 = new Register();
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
