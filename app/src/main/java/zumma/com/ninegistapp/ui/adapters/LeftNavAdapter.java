package zumma.com.ninegistapp.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import zumma.com.ninegistapp.R;


public class LeftNavAdapter extends BaseAdapter {
    private static final String TAG = LeftNavAdapter.class.getSimpleName();
    private Context context;
    private int[] icons = {R.drawable.ic_nav1, R.drawable.ic_nav2, R.drawable.ic_nav3, R.drawable.ic_nav4, R.drawable.ic_nav5};
    private String[] items;
    private int selected;

    public LeftNavAdapter(Context paramContext, String[] paramArrayOfString) {
        Log.d(TAG, "am LeftNavAdapter........"+paramArrayOfString.toString() +"  "+paramArrayOfString[0]);
        this.context = paramContext;
        this.items = paramArrayOfString;
    }

    public int getCount() {
        return this.items.length;
    }

    public String getItem(int paramInt) {
        return this.items[paramInt];
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null)
            paramView = LayoutInflater.from(this.context).inflate(R.layout.left_nav_item, null);
        TextView localTextView = (TextView) paramView;
        localTextView.setText(getItem(paramInt));
        localTextView.setCompoundDrawablesWithIntrinsicBounds(this.icons[paramInt], 0, 0, 0);
        if (paramInt == this.selected) {
            localTextView.setBackgroundColor(this.context.getResources().getColor(R.color.main_color_red_dk));
            return localTextView;
        }
        localTextView.setBackgroundResource(0);
        return localTextView;
    }

    public void setSelection(int paramInt) {
        this.selected = paramInt;
        notifyDataSetChanged();
    }
}
