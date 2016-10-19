package com.itheima.leon.qqdemo.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import com.itheima.leon.qqdemo.model.ContactItem;
import com.itheima.leon.qqdemo.widget.ContactItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 12:06
 * 描述：    TODO
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactItemViewHolder> implements SectionIndexer{
    public static final String TAG = "ContactListAdapter";

    private Context mContext;
    private List<ContactItem> mContactItems;

    private SparseIntArray mSectionFirstPositionBySectionIndex = new SparseIntArray();
    private OnItemClickListener mOnItemClickListener;

    public ContactListAdapter(Context context, List<ContactItem> items) {
        mContext = context;
        mContactItems = items;
    }

    @Override
    public ContactItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContactItemView itemView = new ContactItemView(mContext);
        itemView.setOnClickListener(mOnClickListener);
        itemView.setOnLongClickListener(mOnLongClickListener);
        return new ContactItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactItemViewHolder holder, int position) {
        holder.mItemView.bindView(mContactItems.get(position));
    }

    @Override
    public int getItemCount() {
        if (mContactItems == null) {
            return 0;
        }
        return mContactItems.size();
    }

    /**
     * 获取当前列表中的分区的集合
     * @return
     */
    @Override
    public String[] getSections() {
        Log.d(TAG, "getSections: ");
        ArrayList<String> sections = new ArrayList<>();
        for (int i = 0; i < mContactItems.size(); i++) {
            //如果是分区集合中还没有保存的分区，则保存
            String letter = mContactItems.get(i).getFirstLetterString();
            Log.d(TAG, "getSections: " + letter);
            if (!sections.contains(letter)) {
                sections.add(letter);
                mSectionFirstPositionBySectionIndex.put(sections.size() - 1, i);
            }
        }
        return sections.toArray(new String[sections.size()]);
    }

    /**
     *
     * @param sectionIndex 分区索引
     *
     * @return 返回分区索引对应分区的第一元素在整个列表中的位置
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionFirstPositionBySectionIndex.get(sectionIndex);
    }

    /**
     *
     * @param position 列表中的位置
     * @return 列表中的位置的元素所在分区在分区集合中索引
     */
    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    public class ContactItemViewHolder extends RecyclerView.ViewHolder {

        public ContactItemView mItemView;

        public ContactItemViewHolder(ContactItemView itemView) {
            super(itemView);
            mItemView = itemView;
        }
    }

    public List<ContactItem> getContactItems() {
        return mContactItems;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick();
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemLongClick();
                return true;
            }
            return false;
        }
    };

    public interface OnItemClickListener {
        void onItemClick();
        void onItemLongClick();
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }
}
