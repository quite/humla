/*
 * Copyright (C) 2014 Andrew Comminos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.lublin.humla.util;

import android.os.Parcel;
import android.os.Parcelable;

import se.lublin.humla.protobuf.Mumble;

/**
 * Created by andrew on 14/07/13.
 */
@SuppressWarnings("serial")
public class HumlaException extends Exception implements Parcelable {

    public static final Creator<HumlaException> CREATOR = new Creator<HumlaException>() {
        @Override
        public HumlaException createFromParcel(Parcel source) {
            return new HumlaException(source);
        }

        @Override
        public HumlaException[] newArray(int size) {
            return new HumlaException[size];
        }
    };

    private HumlaDisconnectReason mReason;
    /** Indicates that this exception was caused by a reject from the server. */
    private Mumble.Reject mReject;
    /** Indicates that this exception was caused by being kicked/banned from the server. */
    private Mumble.UserRemove mUserRemove;

    public HumlaException(String message, Throwable e, HumlaDisconnectReason reason) {
        super(message, e);
        mReason = reason;
    }

    public HumlaException(String message, HumlaDisconnectReason reason) {
        super(message);
        mReason = reason;
    }

    public HumlaException(Throwable e, HumlaDisconnectReason reason) {
        super(e);
        mReason = reason;
    }

    public HumlaException(Mumble.Reject reject) {
        super("Rejected: " + reject.getReason());
        mReject = reject;
        mReason = HumlaDisconnectReason.REJECT;
    }

    public HumlaException(Mumble.UserRemove userRemove) {
        super((userRemove.getBan() ? "Banned: " : "Kicked: ")+userRemove.getReason());
        mUserRemove = userRemove;
        mReason = HumlaDisconnectReason.USER_REMOVE;
    }

    private HumlaException(Parcel in) {
        super(in.readString(), (Throwable) in.readSerializable());
        mReason = HumlaDisconnectReason.values()[in.readInt()];
        mReject = (Mumble.Reject) in.readSerializable();
        mUserRemove = (Mumble.UserRemove) in.readSerializable();
    }

    public HumlaDisconnectReason getReason() {
        return mReason;
    }

    public Mumble.Reject getReject() {
        return mReject;
    }

    public Mumble.UserRemove getUserRemove() {
        return mUserRemove;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getMessage());
        dest.writeSerializable(getCause());
        dest.writeInt(mReason.ordinal());
        dest.writeSerializable(mReject);
        dest.writeSerializable(mUserRemove);
    }

    public enum HumlaDisconnectReason {
        REJECT,
        USER_REMOVE,
        CONNECTION_ERROR,
        OTHER_ERROR
    }
}
