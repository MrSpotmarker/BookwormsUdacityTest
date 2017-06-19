package com.example.www.bookworms;

import java.util.List;

/**
 * Package private class for "Book" objects
 */

class Book {

    private String mTitle;
    private List<String> mAuthors;
    private String mDescription;
    private String mPreviewBook;
    private String mImgThumbUrl;

    Book(String title, List<String> authors, String description, String previewBook, String imgThumbUrl) {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mPreviewBook = previewBook;
        mImgThumbUrl = imgThumbUrl;
    }

    String getTitle() {
        return mTitle;
    }

    String getAuthors() {
        String authorsAsString = "";
        for (int i = 0; i < mAuthors.size(); i++) {
            authorsAsString = authorsAsString + mAuthors.get(i) + ", ";
        }
        authorsAsString = authorsAsString.substring(0, authorsAsString.length()-2);
        return authorsAsString;
    }

    String getDescription() {
        return mDescription;
    }

    String getPreviewBook() {
        return mPreviewBook;
    }

    String getImageThumbnailUrl() {
        return mImgThumbUrl;
    }

}
