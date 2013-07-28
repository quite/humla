/*
 * Copyright (C) 2013 Andrew Comminos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morlunk.jumble;

import com.morlunk.jumble.model.User;
import com.morlunk.jumble.model.Channel;
import com.morlunk.jumble.model.Server;
import com.morlunk.jumble.IJumbleObserver;

interface IJumbleService {
    void disconnect();
    boolean isConnected();

    // Data
    int getSession();
    Server getConnectedServer();
    User getUserWithId(int id);
    Channel getChannelWithId(int id);
    List getUserList();
    List getChannelList();

    // Actions
    void joinChannel(int channel);
    void createChannel(int parent, String name, String description, int position, boolean temporary);
    //void setTexture(byte[] texture);
    void requestBanList();
    void requestUserList();
    //void requestACL(int channel);
    void registerUser(int session);
    void kickBanUser(int session, String reason, boolean ban);
    void sendUserTextMessage(int session, String message);
    void sendChannelTextMessage(int channel, String message, boolean tree);
    void setUserComment(int session, String comment);
    void removeChannel(int channel);
    //void addChannelLink(int channel, int link);
    //void requestChannelPermissions(int channel);
    void setSelfMuteDeafState(boolean mute, boolean deaf);
    //void announceRecordingState(boolean recording);

    void registerObserver(in IJumbleObserver observer);
    void unregisterObserver(in IJumbleObserver observer);
}