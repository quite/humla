package se.lublin.humla.model;

import se.lublin.humla.protobuf.Mumble;

public class ServerSettings implements IServerSettings{
    private boolean mAllowHtml;
    private int mMessageLength;
    private int mImageMessageLength;
    private int mMaxBandwidth;
    private int mMaxUsers;
    private String mWelcomeText;

    public ServerSettings(Mumble.ServerConfig msg){
        mAllowHtml = msg.getAllowHtml();
        mMessageLength = msg.getMessageLength();
        mImageMessageLength = msg.getImageMessageLength();
        mMaxBandwidth = msg.getMaxBandwidth();
        mMaxUsers = msg.getMaxUsers();
        mWelcomeText = msg.getWelcomeText();
    }

    @Override
    public boolean getAllowHtml() {
        return mAllowHtml;
    }

    @Override
    public int getMessageLength() {
        return mMessageLength;
    }

    @Override
    public int getImageMessageLength() {
        return mImageMessageLength;
    }

    @Override
    public int getMaxBandwidth() {
        return mMaxBandwidth;
    }

    @Override
    public int getMaxUsers() {
        return mMaxUsers;
    }

    @Override
    public String getWelcomeText() {
        return mWelcomeText;
    }
}
