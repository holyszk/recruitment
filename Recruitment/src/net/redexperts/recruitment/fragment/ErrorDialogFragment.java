package net.redexperts.recruitment.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ErrorDialogFragment extends DialogFragment {
	
	private Dialog dialog;
	
	public ErrorDialogFragment() {
		super();
		this.dialog = null;
	}
	
	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return dialog;
	}

}
