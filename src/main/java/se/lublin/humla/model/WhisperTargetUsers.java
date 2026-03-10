/*
 * Copyright (C) 2016 Andrew Comminos <andrew@comminos.com>
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

import java.util.List;
import se.lublin.humla.protobuf.Mumble;

/**
 * Created by andrew on 28/04/16.
 *
 * Represents a WhisperTarget that targets a specific list of user session IDs.
 * <p>
 * This allows whispering directly to selected users rather than to a channel or group.
 * It is useful in cases where individual control over recipients is required.
 * </p>
 *
 * Example usage:
 * <pre>
 *     List&lt;Integer&gt; sessions = Arrays.asList(12, 15, 23);
 *     WhisperTarget target = new WhisperTargetUsers(sessions);
 *     VoiceTarget.Target protobufTarget = target.createTarget();
 * </pre>
 *
 * @author Alex
 */
public class WhisperTargetUsers implements WhisperTarget {

    /**
     * List of Mumble session IDs for target users.
     */
    private final List<Integer> sessionIds;

    /**
     * Constructs a whisper target with the given list of session IDs.
     *
     * @param sessionIds the list of session IDs to whisper to
     */
    public WhisperTargetUsers(List<Integer> sessionIds) {
        this.sessionIds = sessionIds;
    }

    /**
     * Creates the protobuf voice target used by the server to route audio to the specified users.
     *
     * @return a protobuf {@link Mumble.VoiceTarget.Target} object configured with the session IDs
     */
    @Override
    public Mumble.VoiceTarget.Target createTarget() {
        Mumble.VoiceTarget.Target.Builder builder = Mumble.VoiceTarget.Target.newBuilder();
        for (int sessionId : sessionIds) {
            builder.addSession(sessionId);
        }
        return builder.build();
    }

    /**
     * Returns a string representation for this whisper target, typically for UI display.
     *
     * @return a string describing the target (e.g., "Users: [12, 15, 23]")
     */
    @Override
    public String getName() {
        return "Users: " + sessionIds.toString();
    }
}
