package com.k.menu.interface1;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Karthik on 30-12-2017.
 */

public interface ItemClickListener extends AdapterView.OnItemClickListener {

    void OnClick(View view,int position, boolean isLongClick);

}
