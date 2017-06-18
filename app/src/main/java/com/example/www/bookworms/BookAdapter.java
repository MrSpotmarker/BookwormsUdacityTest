package com.example.www.bookworms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Provides Layout for each item in a list of book objects.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_item, parent, false);
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

        // Find the TextView in the book_item.xml layout with the ID book_author.
        ImageView bookThumbnail = (ImageView) listItemView.findViewById(R.id.book_thumbnail);
        // Get the author from from the currentBook object and set this text
        bookThumbnail.setImageResource(R.drawable.placeholder);

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
