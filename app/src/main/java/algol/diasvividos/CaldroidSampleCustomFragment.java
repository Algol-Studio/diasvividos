package algol.diasvividos;

import com.roomorama.caldroid.CaldroidGridAdapter;

/**
 * Created by fabri on 6/21/2017.
 */

public class CaldroidSampleCustomFragment extends com.roomorama.caldroid.CaldroidFragment {

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        // TODO Auto-generated method stub
        return new CaldroidSampleCustomAdapter(getActivity(), month, year, getCaldroidData(), extraData);
    }
}
