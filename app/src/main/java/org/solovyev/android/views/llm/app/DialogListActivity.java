package org.solovyev.android.views.llm.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class DialogListActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_dialog_list);

		if(savedInstanceState == null) {
			showListDialog();
		}
	}

	private void showListDialog() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.findFragmentByTag(DialogListFragment.DIALOG_TAG) != null) {
			return;
		}
		DialogListFragment dialogFragment = new DialogListFragment();
		dialogFragment.show(fragmentManager, DialogListFragment.DIALOG_TAG);
	}
}