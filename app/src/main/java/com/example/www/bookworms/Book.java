package com.example.www.bookworms;

import java.util.List;

/**
 * Created by Test on 15.06.2017.
 */

public class Book {

    private String mTitle;
    private List<String> mAuthors;
    private String mDescription;
    private String mPreviewBook;
    private String mImgThumbUrl;

    public Book(String title, List<String> authors, String description, String previewBook, String imgThumbUrl) {
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

    public String getPreviewBook() {
        return mPreviewBook;
    }

    public String getImgaeThumbnailUrl() {
        return mImgThumbUrl;
    }

}
