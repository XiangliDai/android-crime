package com.example.CriminalIntent;

import java.util.UUID;
import java.util.Date;
/**
 * Created with IntelliJ IDEA.
 * User: xdai
 * Date: 11/5/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public String toString(){
        return mTitle;
    }
}
