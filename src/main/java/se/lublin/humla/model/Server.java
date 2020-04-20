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

package se.lublin.humla.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.common.net.InetAddresses;

import org.minidns.hla.ResolverApi;
import org.minidns.hla.SrvResolverResult;
import org.minidns.record.SRV;
import org.minidns.util.SrvUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import se.lublin.humla.Constants;

public class Server implements Parcelable {
    private long mId;
    private String mName;
    private String mHost;
    private int mPort;
    private String mUsername;
    private String mPassword;

    private String mResolvedHost = null;
    private int mResolvedPort;

    public static final Parcelable.Creator<Server> CREATOR = new Parcelable.Creator<Server>() {

        @Override
        public Server createFromParcel(Parcel parcel) {
            return new Server(parcel);
        }

        @Override
        public Server[] newArray(int i) {
            return new Server[i];
        }
    };

    public Server(long id, String name, String host, int port, String username, String password) {
        mId = id;
        mName = name;
        mHost = host;
        mPort = port;
        mUsername = username;
        mPassword = password;
        mResolvedHost = null;
    }

    private Server(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mName);
        parcel.writeString(mHost);
        parcel.writeInt(mPort);
        parcel.writeString(mUsername);
        parcel.writeString(mPassword);
    }

    private void readFromParcel(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mHost = in.readString();
        mPort = in.readInt();
        mUsername = in.readString();
        mPassword = in.readString();
        mResolvedHost = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    /**
     * Returns a user-defined name for the server, or the host if the user-defined name is not set.
     * @return A user readable name for the server.
     */
    public String getName() {
        return (mName != null && mName.length() > 0) ? mName : mHost;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String mHost) {
        this.mHost = mHost;
        this.mResolvedHost = null;
    }

    public int getPort() {
        return mPort;
    }

    public void setPort(int mPort) {
        this.mPort = mPort;
        this.mResolvedHost = null;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    /**
     * Returns whether or not the server is stored in a database.
     * @return true if the server's ID is in the database.
     */
    public boolean isSaved() {
        return mId != -1;
    }

    public String getSrvHost() {
        srvResolve();
        return mResolvedHost;
    }

    public int getSrvPort() {
        srvResolve();
        return mResolvedPort;
    }

    private synchronized void srvResolve() {
        if (mResolvedHost != null) {
            return;
        }
        // if we have a port then don't bother with SRV
        if (mPort != 0) {
            mResolvedHost = mHost;
            mResolvedPort = mPort;
            return;
        }
        // skip also IP addresses and Tor Onion Services (a pseudo-TLD)
        if (InetAddresses.isInetAddress(mHost)
                || mHost.endsWith(".onion")) {
            mResolvedHost = mHost;
            mResolvedPort = Constants.DEFAULT_PORT;
            return;
        }
        // set to our fallback values in case of no SRV or resolve fail
        final AtomicReference<String> srvHost = new AtomicReference<>(mHost);
        final AtomicInteger srvPort = new AtomicInteger(Constants.DEFAULT_PORT);
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String lookup = "_mumble._tcp." + srvHost.get();
                        SrvResolverResult res = ResolverApi.INSTANCE.resolveSrv(lookup);
                        if (!res.wasSuccessful()) {
                            Log.d(Constants.TAG, "resolveSrv " + lookup + ": " + res.getResponseCode());
                            return;
                        }
                        Set<SRV> answers = res.getAnswersOrEmptySet();
                        if (answers.isEmpty()) {
                            Log.d(Constants.TAG, "resolveSrv " + lookup + ": empty answer");
                            return;
                        }
                        List<SRV> srvs = SrvUtil.sortSrvRecords(answers);
                        for (SRV srv : srvs) {
                            Log.d(Constants.TAG, "resolved " + lookup + " SRV: " + srv.toString());
                            srvHost.set(srv.target.toString());
                            srvPort.set(srv.port);
                            // TODO SRV just picking the first record.
                            return;
                        }
                    } catch (IOException e) {
                        Log.d(Constants.TAG, "resolveSRV() run() " + e);
                    }
                }
            });
            t.start();
            t.join();
        }
        catch (Exception e) {
            Log.d(Constants.TAG, "resolveSRV() " + e);
        }
        mResolvedHost = srvHost.get();
        mResolvedPort = srvPort.get();
    }
}
