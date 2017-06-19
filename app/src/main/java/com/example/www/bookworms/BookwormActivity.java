package com.example.www.bookworms;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class BookwormActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOK_LOADER_ID = 1;
    public static String NO_DESCRIPTION;
    private static String NO_AUTHOR;
    private String queryUrl;
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mProgressBar;
    private ListView bookListView;
    private LoaderManager loaderManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookworm);

        // Get values for NO_AUTHORS and NO_DESCRIPTION
        NO_AUTHOR = getString(R.string.no_authors);
        NO_DESCRIPTION = getString(R.string.no_description);

        // Find a reference to the {@link ProgrssBar} in the layout
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(GONE);

        // Checks for internet connectivity
        final ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        loaderManager = getLoaderManager();

        // Find a reference to the {@link List} in the layout
        bookListView = (ListView) findViewById(R.id.list);

        // Set empty view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        // Find a reference to the {@link button} in the layout
        Button button = (Button) findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryUrl = "https://www.googleapis.com/books/v1/volumes?q=";
                EditText textInput = (EditText) findViewById(R.id.text_input);
                queryUrl = queryUrl + textInput.getText();
                mProgressBar.setVisibility(View.VISIBLE);
                mEmptyStateTextView.setText("");

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                final boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected) {
                    //restart loader
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, BookwormActivity.this);

                    // Create a new {@link BookAdapter} of books
                    mAdapter = new BookAdapter(getApplicationContext(), new ArrayList<Book>());

                    // Set the adapter on the {@link ListView}
                    // so the list can be populated in the user interface
                    bookListView.setAdapter(mAdapter);

                    // OnItemClickListener + method to open url from Earthquake object
                    bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Book book = mAdapter.getItem(i);
                            // Convert the String URL into a URI object (to pass into the Intent constructor)
                            Uri bookUri = Uri.parse(book.getPreviewBook());

                            // Create a new intent to view the earthquake URI
                            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                            // Send the intent to launch a new activity
                            startActivity(websiteIntent);
                        }
                    });

                } else {
                    // Show output that there's no internet connectivity
                    if (mAdapter != null) {
                        mAdapter.clear();
                    }
                    mProgressBar.setVisibility(GONE);
                    mEmptyStateTextView.setText(R.string.no_internet);
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, queryUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Set empty state text to display "No books found."
        if (books.size() == 0) {
            mEmptyStateTextView.setText(R.string.no_books);
        }
        mProgressBar.setVisibility(GONE);

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (!books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }

    static class GetStrings {
        static String getNoAuthor() {
            return NO_AUTHOR;
        }

        static String getNoDescription() {
            return NO_DESCRIPTION;
        }
    }
}
