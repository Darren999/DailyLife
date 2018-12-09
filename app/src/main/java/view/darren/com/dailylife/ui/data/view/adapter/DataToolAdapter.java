package view.darren.com.dailylife.ui.data.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;

import java.util.ArrayList;

public class DataToolAdapter extends BaseAdapter {

    private String[] toolName;
    private ArrayList<Integer> toolLogo;
    private MainActivity activity;

    public DataToolAdapter(MainActivity activity,String[] toolName,ArrayList<Integer> toolLogo){
        this.activity = activity;
        this.toolName = toolName;
        this.toolLogo = toolLogo;
    }

    @Override
    public int getCount() {
        return toolName.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            holder =new ViewHolder();

            convertView= View.inflate(activity, R.layout.item_vp_grid_iv,null);
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_new_seed_title);
            holder.iv_nul = (ImageView)convertView.findViewById(R.id.iv_new_seed_ic);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(toolName[i]);
        holder.iv_nul.setImageResource(toolLogo.get(i));
        return convertView;
    }

    private static class ViewHolder{
        private TextView tv_name;
        private ImageView iv_nul;
    }
}
