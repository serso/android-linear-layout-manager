package org.solovyev.android.views.llm.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.ViewHolder> {

	private final DialogListFragment fragment;
	private final List<String> items;

	public DialogListAdapter(DialogListFragment fragment, List<String> items) {
		this.fragment = fragment;
		this.items = items;
	}

	public List<String> getItems() {
		return items;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
		View rootLayout =
				LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_list_item, viewGroup, false);
		return new ViewHolder(rootLayout);
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fragment.onClick(position);
			}
		});
		viewHolder.text.setText(items.get(position));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		TextView text;

		public ViewHolder(View itemView) {
			super(itemView);
			this.text = (TextView) itemView.findViewById(R.id.text);
		}
	}
}
