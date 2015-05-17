package com.deals.explore.deals.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.deals.explore.deals.R;
import com.deals.explore.deals.bean.Deal;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by tasneem on 17/5/15.
 */
public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealsViewHolder> {
    private static final String TAG = "Deals Adapter";

    private List<Deal> mDealsList;
    private Context mContext;

    /**
     * Parameterized constructor
     * @param context
     * @param dealsList
     */
    public DealsAdapter(Context context, List<Deal> dealsList) {
        mDealsList = dealsList;
        mContext = context;
    }

    /**
     * Called when RecyclerView needs a new {@link DealsViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(DealsViewHolder, int)}. Since it will be re-used to display different
     * items in the data set, it is a good idea to cache references to sub views of the View to
     * avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(DealsViewHolder, int)
     *
     */
    @Override
    public DealsAdapter.DealsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new DealsViewHolder(rootView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link DealsViewHolder#itemView} to reflect the item at
     * the given position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this
     * method again if the position of the item changes in the data set unless the item itself
     * is invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside this
     * method and should not keep a copy of it. If you need the position of an item later on
     * (e.g. in a click listener), use {@link DealsViewHolder#getAdapterPosition()} which will have
     * the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(DealsAdapter.DealsViewHolder holder, int position) {
        holder.title.setText(decodeHtmlText(mDealsList.get(position).getTitle()));
        holder.description.setText(decodeHtmlText(mDealsList.get(position).getDescription()));
        ImageLoader imageLoader = VolleyAppController.getInstance(mContext).getImageLoader();
        imageLoader.get(mDealsList.get(position).getUrl(),
                ImageLoader.getImageListener(holder.image, R.drawable.abc_btn_check_to_on_mtrl_015,
                        R.drawable.abc_btn_check_to_on_mtrl_015));
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mDealsList.size();
    }

    /**
     * View Holder to store components
     */
    public class DealsViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView description;
        ImageView image;

        public DealsViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            description = (TextView)itemView.findViewById(R.id.description);
            image = (ImageView)itemView.findViewById(R.id.image);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Item at position "+getAdapterPosition()+" clicked.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * - Replace \n to <br /> because HTML.fromHtml() method replaces \n to space.
     * - Decoded html text into android compatible text
     * @param text
     * @return String
     */
    public static String decodeHtmlText(String text) {
        String name = "";
        try {
            name = new String(text.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        }
        return Html.fromHtml(name.replace("\n", "<br />")).toString().trim();
    }
}
