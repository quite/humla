/*
 * Copyright (C) 2015 Andrew Comminos <andrew@comminos.com>
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
 * Created by andrew on 18/10/15.
 */
public interface IHumlaObserver {
    void onConnected();

    void onConnecting();

    void onDisconnected(HumlaException e);

    void onTLSHandshakeFailed(X509Certificate[] chain);

    void onChannelAdded(IChannel channel);

    void onChannelStateUpdated(IChannel channel);

    void onChannelRemoved(IChannel channel);

    void onChannelPermissionsUpdated(IChannel channel);

    void onUserConnected(IUser user);

    void onUserStateUpdated(IUser user);

    void onUserTalkStateUpdated(IUser user);

    void onUserJoinedChannel(IUser user, IChannel newChannel, IChannel oldChannel);

    void onUserRemoved(IUser user, String reason);

    void onPermissionDenied(String reason);

    void onMessageLogged(IMessage message);

    void onVoiceTargetChanged(VoiceTargetMode mode);

    void onLogInfo(String message);

    void onLogWarning(String message);

    void onLogError(String message);
}
