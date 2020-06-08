package c.example.communityreport;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class PotholeReportDialog extends AppCompatDialogFragment {
    private TextView date;
    private TextView gps;
    private TextView area;
    private TextView status;
    private TextView address;
    private Button confirm;
    private Button yes;
    private Button no;

    ArrayList<String> dialogdetails;

    //data variables
    String date_time;
    String gpsval;
    String  areaval;
    String addressval;
    String statusval;

    //Firebase vairables
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        dialogdetails = new ArrayList<String>();

        Bundle bundle = this.getArguments();
        dialogdetails = bundle.getStringArrayList("key");

        date_time = dialogdetails.get(0);
        gpsval = dialogdetails.get(1);
        areaval = dialogdetails.get(2);
        statusval = dialogdetails.get(3);
        addressval = dialogdetails.get(4);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pothole_report_dialog, null);

        date = view.findViewById(R.id.date);
        gps = view.findViewById(R.id.gps);
        area = view.findViewById(R.id.area);
        address = view.findViewById(R.id.address);
        status = view.findViewById(R.id.status);
        confirm = view.findViewById(R.id.confirm);
        yes = view.findViewById(R.id.yes);
        no = view.findViewById(R.id.no);
        confirm.setVisibility(View.GONE);
        yes.setVisibility(View.GONE);
        no.setVisibility(View.GONE);

        date.setText(date_time);
        gps.setText(gpsval);
        area.setText(areaval);
        address.setText(addressval);

        String status_text = null;
        switch (statusval) {
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
        status.setText(status_text);

        builder.setView(view)
                .setTitle("Report Details")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        if (Integer.valueOf(statusval) == 80){
            confirm.setVisibility(View.VISIBLE);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setVisibility(View.VISIBLE);
                no.setVisibility(View.VISIBLE);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Pothole_Reports").child(firebaseUser.getUid()).child(date_time).child("Status").setValue(100)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Status Updated !!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return builder.create();
    }

}
