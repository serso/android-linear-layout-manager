package org.solovyev.android.views.llm;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

	private Drawable divider;
	private boolean first = false;
	private boolean last = false;

	@SuppressWarnings("UnusedDeclaration")
	public DividerItemDecoration(Context context, AttributeSet attrs) {
		final TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.listDivider});
		divider = a.getDrawable(0);
		a.recycle();
	}

	@SuppressWarnings("UnusedDeclaration")
	public DividerItemDecoration(Context context, AttributeSet attrs, boolean showFirstDivider,
								 boolean showLastDivider) {
		this(context, attrs);
		first = showFirstDivider;
		last = showLastDivider;
	}

	@SuppressWarnings("UnusedDeclaration")
	public DividerItemDecoration(Drawable divider) {
		this.divider = divider;
	}

	@SuppressWarnings("UnusedDeclaration")
	public DividerItemDecoration(Drawable divider, boolean showFirstDivider,
								 boolean showLastDivider) {
		this(divider);
		first = showFirstDivider;
		last = showLastDivider;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
							   RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		if (divider == null) {
			return;
		}

		if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
			outRect.top = divider.getIntrinsicHeight();
			outRect.bottom = -outRect.top;
		} else {
			outRect.left = divider.getIntrinsicWidth();
			outRect.right = -outRect.left;
		}
	}

	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		if (divider == null) {
			super.onDrawOver(c, parent, state);
			return;
		}

		int left = 0;
		int right = 0;
		int top = 0;
		int bottom = 0;

		final int orientation = getOrientation(parent);
		final int childCount = parent.getChildCount();

		final boolean vertical = orientation == LinearLayoutManager.VERTICAL;
		final int size;
		if (vertical) {
			size = divider.getIntrinsicHeight();
			left = parent.getPaddingLeft();
			right = parent.getWidth() - parent.getPaddingRight();
		} else {
			size = divider.getIntrinsicWidth();
			top = parent.getPaddingTop();
			bottom = parent.getHeight() - parent.getPaddingBottom();
		}

		for (int i = first ? 0 : 1; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
			if (vertical) {
				top = child.getTop() - params.topMargin;
				bottom = top + size;
			} else {
				left = child.getLeft() - params.leftMargin;
				right = left + size;
			}
			divider.setBounds(left, top, right, bottom);
			divider.draw(c);
		}

		if (last && childCount > 0) {
			final View child = parent.getChildAt(childCount - 1);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
			if (vertical) {
				top = child.getBottom() + params.bottomMargin;
				bottom = top + size;
			} else {
				left = child.getRight() + params.rightMargin;
				right = left + size;
			}
			divider.setBounds(left, top, right, bottom);
			divider.draw(c);
		}
	}

	private int getOrientation(RecyclerView parent) {
		final RecyclerView.LayoutManager lm = parent.getLayoutManager();
		if (lm instanceof LinearLayoutManager) {
			return ((LinearLayoutManager) lm).getOrientation();
		} else {
			throw new IllegalStateException("DividerItemDecoration can only be used with a LinearLayoutManager");
		}
	}
}
