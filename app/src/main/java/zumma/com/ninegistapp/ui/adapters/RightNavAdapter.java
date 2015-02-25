package zumma.com.ninegistapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.model.Data;

public class RightNavAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Data> items;

    public RightNavAdapter(Context paramContext, ArrayList<Data> paramArrayList) {
        this.context = paramContext;
        this.items = paramArrayList;
    }

    public int getCount() {
        return this.items.size();
    }

    public Data getItem(int paramInt) {
        return (Data) this.items.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null)
            paramView = LayoutInflater.from(this.context).inflate(R.layout.right_nav_item, null);
        Data localData = getItem(paramInt);
        ((TextView) paramView.findViewById(R.id.lbl1)).setText(localData.getTitle());
        TextView localTextView = (TextView) paramView.findViewById(R.id.lbl2);
        if ((paramInt + 1) % 3 == 0) ;
        for (int i = 0; ; i = 8) {
            localTextView.setVisibility(View.VISIBLE);
            localTextView.setText(localData.getDesc());
            ((ImageView) paramView.findViewById(R.id.img)).setImageResource(localData.getImage());
            return paramView;
        }
    }
}

/* Location:           C:\Users\Okafor\workspace\imate\iMate_dex2jar.jar
 * Qualified Name:     com.imate.ui.RightNavAdapter
 * JD-Core Version:    0.6.0
 */