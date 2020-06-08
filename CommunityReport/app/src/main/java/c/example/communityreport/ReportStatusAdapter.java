package c.example.communityreport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class ReportStatusAdapter extends ArrayAdapter<String> {

    private ArrayList<String> itemData;
    private ArrayList<String> itemGps;
    private ArrayList<String> itemWidth;
    private ArrayList<String> itemHeight;
    private ArrayList<String> itemTimeStamp;
    private ArrayList<String> itemStatus;
    private ArrayList<String> itemCount;
    private ArrayList<String> itemAddress;
    private Context mContext;

    public ReportStatusAdapter(Context context, ArrayList<String> itemTimeStamp, ArrayList<String> itemGps, ArrayList<String> itemStatus, ArrayList<String> itemCount, ArrayList<String> itemWidth, ArrayList<String> itemHeight, ArrayList<String> itemAddress) {
        super(context, R.layout.listview_item);
        this.itemGps = itemGps;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.itemTimeStamp = itemTimeStamp;
        this.itemStatus = itemStatus;
        this.itemCount = itemCount;
        this.mContext = context;
        this.itemAddress = itemAddress;
    }

    @Override
    public int getCount() {
        return itemTimeStamp.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            viewHolder.timestamp =  convertView.findViewById(R.id.timestamp);
            viewHolder.address =  convertView.findViewById(R.id.address);
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.progressBar = convertView.findViewById(R.id.progressBar);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String status_text = null;
        switch (itemStatus.get(position)) {
            case "0":
                status_text = "Report Rejected";
                break;
            case "10":
                status_text = "Report Submitted";
                break;
            case "50":
                status_text = "Pothole(s) Detected and Report Accepted";
                break;
            case "60":
                status_text = "Report Acknowledged and Fixing Process Started";
                break;
            case "70":
                status_text = "Engineer Assigned for fixing the issue";
                break;
            case "80":
                status_text = "Issue has been Resolved";
                break;
            case "100":
                status_text = "Issue Resolved and Confirmed by you !";
                break;
        }

        viewHolder.timestamp.setText(itemTimeStamp.get(position));
        viewHolder.address.setText(itemAddress.get(position));
        viewHolder.status.setText(status_text);
        viewHolder.progressBar.setProgress(Integer.parseInt(itemStatus.get(position)));

        return convertView;
    }

    static class ViewHolder{
        TextView timestamp;
        TextView address;
        TextView status;
        ProgressBar progressBar;
    }
}
