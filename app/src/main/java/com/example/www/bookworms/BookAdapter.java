package com.example.www.bookworms;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Provides Layout for each item in a list of book objects.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    private static final String LOG_TAG = Utils.class.getSimpleName();

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) listItemView.findViewById(R.id.book_thumbnail);
            listItemView.setTag(viewHolder);
        }

        // Get book object at location in the list
        Book currentBook = getItem(position);

        // Find the TextView in the book_item.xml layout with the ID book_title.
        TextView bookTitle = (TextView) listItemView.findViewById(R.id.book_title);
        // Get the Title from from the currentBook object and set this text
        bookTitle.setText(currentBook.getTitle());

        // Find the TextView in the book_item.xml layout with the ID book_author.
        TextView bookAuthor = (TextView) listItemView.findViewById(R.id.book_author);
        // Get the author from from the currentBook object and set this text
        bookAuthor.setText(currentBook.getAuthors());

        // Find the TextView in the book_item.xml layout with the ID book_author.
        TextView bookDescription = (TextView) listItemView.findViewById(R.id.book_description_body);
        // Get the author from from the currentBook object and set this text
        bookDescription.setText(currentBook.getDescription());

        viewHolder = (ViewHolder) listItemView.getTag();
        viewHolder.imageUrl = currentBook.getImageThumbnailUrl();
        viewHolder.imageView.setImageResource(android.R.color.transparent);
        new DownloadAsyncTask().execute(viewHolder);

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }

    //constructor for inner class ViewHolder
    private static class ViewHolder {
        ImageView imageView;
        String imageUrl;
        Bitmap bitmap;
    }

    private class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            //load image directly
            ViewHolder viewHolder = params[0];
            try {
                URL imageURL = new URL(viewHolder.imageUrl);
                viewHolder.bitmap = BitmapFactory.decodeStream(imageURL.openStream());
            } catch (IOException e) {
                Log.e("error", "Downloading Image Failed");
                viewHolder.bitmap = null;
            }

            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder result) {
            if (result.bitmap == null) {
                //fallback if download fails
                result.imageView.setImageResource(R.drawable.placeholder);
            } else {
                result.imageView.setImageBitmap(result.bitmap);
            }
        }
    }
}
