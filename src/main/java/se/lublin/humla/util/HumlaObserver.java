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

import java.security.cert.X509Certificate;

import se.lublin.humla.model.IChannel;
import se.lublin.humla.model.IMessage;
import se.lublin.humla.model.IUser;

/**
 * Stub class for Humla service observation.
 * Created by andrew on 31/07/13.
 */
public class HumlaObserver implements IHumlaObserver {
    @Override
    public void onConnected() {

    }

    @Override
    public void onConnecting() {

    }

    @Override
    public void onDisconnected(HumlaException e) {

    }

    @Override
    public void onTLSHandshakeFailed(X509Certificate[] chain) {

    }

    @Override
    public void onChannelAdded(IChannel channel) {

    }

    @Override
    public void onChannelStateUpdated(IChannel channel) {

    }

    @Override
    public void onChannelRemoved(IChannel channel) {

    }

    @Override
    public void onChannelPermissionsUpdated(IChannel channel) {

    }

    @Override
    public void onUserConnected(IUser user) {

    }

    @Override
    public void onUserStateUpdated(IUser user) {

    }

    @Override
    public void onUserTalkStateUpdated(IUser user) {

    }

    @Override
    public void onUserJoinedChannel(IUser user, IChannel newChannel, IChannel oldChannel) {

    }

    @Override
    public void onUserRemoved(IUser user, String reason) {

    }

    @Override
    public void onPermissionDenied(String reason) {

    }

    @Override
    public void onMessageLogged(IMessage message) {

    }

    @Override
    public void onVoiceTargetChanged(VoiceTargetMode mode) {

    }

    @Override
    public void onLogInfo(String message) {

    }

    @Override
    public void onLogWarning(String message) {

    }

    @Override
    public void onLogError(String message) {

    }
}
