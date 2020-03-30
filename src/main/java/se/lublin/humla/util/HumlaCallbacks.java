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
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import se.lublin.humla.model.IChannel;
import se.lublin.humla.model.IMessage;
import se.lublin.humla.model.IUser;

/**
 * A composite wrapper around Humla observers to easily broadcast to each observer.
 * Created by andrew on 12/07/14.
 */
public class HumlaCallbacks implements IHumlaObserver {
    private final Set<IHumlaObserver> mCallbacks;

    public HumlaCallbacks() {
        mCallbacks = Collections.newSetFromMap(new ConcurrentHashMap<IHumlaObserver, Boolean>());
    }

    public void registerObserver(IHumlaObserver observer) {
        mCallbacks.add(observer);
    }

    public void unregisterObserver(IHumlaObserver observer) {
        mCallbacks.remove(observer);
    }

    @Override
    public void onConnected() {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onConnected();
        }
    }

    @Override
    public void onConnecting() {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onConnecting();
        }
    }

    @Override
    public void onDisconnected(HumlaException e) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onDisconnected(e);
        }
    }

    @Override
    public void onTLSHandshakeFailed(X509Certificate[] chain) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onTLSHandshakeFailed(chain);
        }
    }

    @Override
    public void onChannelAdded(IChannel channel) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onChannelAdded(channel);
        }
    }

    @Override
    public void onChannelStateUpdated(IChannel channel) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onChannelStateUpdated(channel);
        }
    }

    @Override
    public void onChannelRemoved(IChannel channel) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onChannelRemoved(channel);
        }
    }

    @Override
    public void onChannelPermissionsUpdated(IChannel channel) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onChannelPermissionsUpdated(channel);
        }
    }

    @Override
    public void onUserConnected(IUser user) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onUserConnected(user);
        }
    }

    @Override
    public void onUserStateUpdated(IUser user) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onUserStateUpdated(user);
        }
    }

    @Override
    public void onUserTalkStateUpdated(IUser user) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onUserTalkStateUpdated(user);
        }
    }

    @Override
    public void onUserJoinedChannel(IUser user, IChannel newChannel, IChannel oldChannel) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onUserJoinedChannel(user, newChannel, oldChannel);
        }
    }

    @Override
    public void onUserRemoved(IUser user, String reason) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onUserRemoved(user, reason);
        }
    }

    @Override
    public void onPermissionDenied(String reason) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onPermissionDenied(reason);
        }
    }

    @Override
    public void onMessageLogged(IMessage message) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onMessageLogged(message);
        }
    }

    @Override
    public void onVoiceTargetChanged(VoiceTargetMode mode) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onVoiceTargetChanged(mode);
        }
    }

    @Override
    public void onLogInfo(String message) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onLogInfo(message);
        }
    }

    @Override
    public void onLogWarning(String message) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onLogWarning(message);
        }
    }

    @Override
    public void onLogError(String message) {
        for (IHumlaObserver observer : mCallbacks) {
            observer.onLogError(message);
        }
    }
}
