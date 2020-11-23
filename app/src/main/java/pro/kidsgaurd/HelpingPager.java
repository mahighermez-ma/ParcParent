package pro.kidsgaurd;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class HelpingPager extends FragmentStatePagerAdapter {

    int tabCount;

    public HelpingPager(FragmentManager fm , int tabCount)
    {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }



    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                BlankFragment tab1 = new BlankFragment();
                return tab1;
            case 1:
                BlankFragment2 tab2 = new BlankFragment2();
                return tab2;
            case 2:
                BlankFragment3 tab3 = new BlankFragment3();
                return tab3;
            case 3:
                BlankFragment4 tab4 = new BlankFragment4();
                return tab4;
            case 4:
                BlankFragment5 tab5 = new BlankFragment5();
                return tab5;
            case 5:
                BlankFragment6 tab6 = new BlankFragment6();
                return tab6;
            case 6:
                BlankFragment7 tab7 = new BlankFragment7();
                return tab7;
            case 7:
                BlankFragment8 tab8 = new BlankFragment8();
                return tab8;
            case 8:
                BlankFragment9 tab9 = new BlankFragment9();
                return tab9;
            case 9:
                BlankFragment10 tab10 = new BlankFragment10();
                return tab10;
            case 10:
                BlankFragment11 tab11 = new BlankFragment11();
                return tab11;

            default:
                return null;
        }
    }
}
