package com.example.www.bookworms;

import java.net.URL;
import java.util.List;

import static android.R.attr.author;

/**
 * Created by Test on 15.06.2017.
 */

public class Book {

    private String mTitle;
    private List<String> mAuthors;
    private String mDescription;
    private URL mPreviewBook;
    private URL mImgThumbUrl;

    public Book (String title, List<String> authors, String description, URL previewBook, URL imgThumbUrl) {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mPreviewBook = previewBook;
        mImgThumbUrl = imgThumbUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthors() {
        String authorsAsString = "";
        for (int i = 0; i < mAuthors.size(); i++) {
            authorsAsString = authorsAsString + mAuthors.get(i) + ", ";
        }
        authorsAsString = authorsAsString.substring(0, authorsAsString.length()-2);
        return authorsAsString;
    }

    public String getDescription() {
        return mDescription;
    }

    public URL getPreviewBook() {
        return mPreviewBook;
    }

    public URL getImgaeThumbnailUrl() {
        return mImgThumbUrl;
    }

}
