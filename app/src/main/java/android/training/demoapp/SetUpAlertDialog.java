package android.training.demoapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SetUpAlertDialog extends DialogFragment  {

    private RadioGroup radioGroup;
    private RadioButton radioSelectedButton;
    private  View rootView;

    public interface SetUpAlertDialogListener  {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    SetUpAlertDialogListener listener;
//https://developer.android.com/guide/topics/ui/dialogs
//    https://stackoverflow.com/questions/54353232/get-value-of-radio-button-in-custom-alert-dialog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (SetUpAlertDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement SetUpAlertDialogListener");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = requireActivity().getLayoutInflater();


        final View dialogView = inflater.inflate(R.layout.dialog_setup_listado, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_setup_listado, null))
                //.setMessage("Mensjae")
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(SetUpAlertDialog.this);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(SetUpAlertDialog.this);
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}



//
//    RadioGroup radioGroup =  dialogView.findViewById(R.id.radioGroup); //here
////                        int selectedId = radioGroup.getCheckedRadioButtonId();
////                        radioSelectedButton =  dialogView.findViewById(selectedId);
//
//                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//@Override
//public void onCheckedChanged(RadioGroup group, int checkedId) {
//        int checkedRadio = group.getCheckedRadioButtonId();
//        RadioButton checkedRadioButton = dialogView.findViewById(checkedRadio);
//        String checkedBox = checkedRadioButton.getText().toString();
//        //textView.setText(checkedBox);
//        }
//        });