/*
 * Copyright 2015 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.android.views.llm.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomListActivity extends FragmentActivity {

	private final Handler handler = new Handler();
	private Updater updater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_random_list);

		final MyAdapter adapter = new MyAdapter();

		final TextView counter = (TextView) findViewById(R.id.counter);
		adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
			@Override
			public void onItemRangeInserted(int positionStart, int itemCount) {
				updateCounter(counter, adapter);
			}

			@Override
			public void onItemRangeRemoved(int positionStart, int itemCount) {
				updateCounter(counter, adapter);
			}
		});

		final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView, LinearLayoutManager.VERTICAL, false);
		layoutManager.setOverScrollMode(ViewCompat.OVER_SCROLL_IF_CONTENT_SCROLLS);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
		recyclerView.setAdapter(adapter);

		final ImageView rotate = (ImageView) findViewById(R.id.rotate);
		rotate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final ViewGroup.LayoutParams lp = recyclerView.getLayoutParams();
				final int orientation = layoutManager.getOrientation();
				if (orientation == LinearLayoutManager.VERTICAL) {
					layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
					lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
					lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
				} else {
					layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
					lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
					lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
				}
				recyclerView.setLayoutParams(lp);
			}
		});

		final ImageView playPause = (ImageView) findViewById(R.id.playpause);
		playPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (updater.running) {
					updater.stop();
					playPause.setImageResource(R.drawable.ic_play_arrow_grey600_48dp);
				} else {
					updater.start();
					playPause.setImageResource(R.drawable.ic_pause_grey600_48dp);
				}
			}
		});

		updater = new Updater(adapter);
		updater.start();
	}

	private void updateCounter(TextView counter, MyAdapter adapter) {
		counter.setText("Items: " + adapter.getItemCount());
	}

	private static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

		private final List<String> items = new ArrayList<>();

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			final TextView itemView = (TextView) inflater.inflate(R.layout.list_item, parent, false);
			return new MyViewHolder(itemView);
		}

		@Override
		public void onBindViewHolder(MyViewHolder holder, int position) {
			((TextView) holder.itemView).setText(items.get(position));
		}

		@Override
		public int getItemCount() {
			return items.size();
		}

		public void add(int count) {
			final int sizeBefore = items.size();
			for (int i = 0; i < count; i++) {
				items.add("Item #" + (sizeBefore + i + 1));
			}
			notifyItemRangeInserted(sizeBefore, count);
		}

		public void removeLast(int count) {
			final int sizeBefore = items.size();
			if (count >= items.size()) {
				items.clear();
				notifyItemRangeRemoved(0, sizeBefore);
			} else {
				for (int i = 0; i < count; i++) {
					items.remove(items.size() - 1);
				}
				notifyItemRangeRemoved(items.size(), count);
			}
		}
	}

	private static class MyViewHolder extends RecyclerView.ViewHolder {
		public MyViewHolder(TextView textView) {
			super(textView);
		}
	}

	private class Updater implements Runnable {
		private final MyAdapter adapter;
		private final Random random = new Random();
		private boolean running;

		public Updater(MyAdapter adapter) {
			this.adapter = adapter;
		}

		@Override
		public void run() {
			final boolean canRemove = adapter.getItemCount() > 0;
			final boolean remove = canRemove && random.nextBoolean();
			final int count = random.nextInt(10);
			if (remove) {
				adapter.removeLast(count);
			} else {
				adapter.add(count);
			}
			handler.postDelayed(this, 2000L);
		}


		void start() {
			if (!running) {
				running = true;
				handler.post(this);
			}
		}

		void stop() {
			if (running) {
				running = false;
				handler.removeCallbacks(this);
			}
		}
	}
}