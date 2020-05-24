import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jishorough2.Entry;

import java.util.List;

public class MyViewModel extends ViewModel {
    private MutableLiveData<List<Entry>> mlist;

    public LiveData<List<Entry>> getList() {
        if(mlist == null) {
            mlist = new MutableLiveData<List<Entry>>();
        }
        return mlist;
    }
}
