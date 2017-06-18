package com.example.www.bookworms;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class BookwormActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private String queryUrl;
    private static final int BOOK_LOADER_ID = 1;

    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mProgressBar;
    private ListView bookListView;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookworm);

        // Find a reference to the {@link ProgrssBar} in the layout
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(GONE);

        // Checks for internet connectivity
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

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

                if (isConnected) {
                    //restart loader
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, BookwormActivity.this);

                    // Create a new {@link BookAdapter} of books
                    mAdapter = new BookAdapter(getApplicationContext(), new ArrayList<Book>());

                    // Set the adapter on the {@link ListView}
                    // so the list can be populated in the user interface
                    bookListView.setAdapter(mAdapter);

                } else {
                    // Show output that there's no internet connectivity
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
        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_books);
        mProgressBar.setVisibility(GONE);

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
