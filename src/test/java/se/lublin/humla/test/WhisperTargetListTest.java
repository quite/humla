/*
 * Copyright (C) 2024 Nicolas Peugnet <n.peugnet@free.fr>
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

package se.lublin.humla.test;

import junit.framework.TestCase;
import static org.junit.Assert.assertThrows;

import se.lublin.humla.model.Channel;
import se.lublin.humla.model.WhisperTarget;
import se.lublin.humla.model.WhisperTargetChannel;
import se.lublin.humla.model.WhisperTargetList;

/**
 * Tests the Whisper target list model.
 */
public class WhisperTargetListTest extends TestCase {

    public void testWhisperTargetAddRemove() {
        // Initialisation.
        Channel root = new Channel(0, false);
        WhisperTarget expected, actual;
        WhisperTarget[] targets = new WhisperTarget[30];
        for (int i = 0; i < 30; i++) {
            targets[i] = new WhisperTargetChannel(root, false, false, null);
        }
        WhisperTargetList list = new WhisperTargetList();

        // Empty list
        actual = list.get((byte) 1);
        assertEquals("get on empty list", null, actual);

        // There should be space for 30 whisper targets.
        for (int i = 0; i < 30; i++) {
            int space = list.spaceRemaining();
            assertEquals("spaceRemaining should be 30-i", 30-i, space);
            byte id = list.append(targets[i]);
            assertEquals("id should be equal to i + 1", i+1, id);
            WhisperTarget target = list.get(id);
            assertEquals("get(id) should return the correct target", targets[i], target);
        }

        // But not 31.
        int space = list.spaceRemaining();
        assertEquals("spaceRemaining of full list", 0, space);
        byte id = list.append(targets[0]);
        assertEquals("id should be -1 when no space left", -1, id);

        // Freeing one target
        list.free((byte) 5);
        actual = list.get((byte) 5);
        assertEquals("get after free in same slot", null, actual);
        space = list.spaceRemaining();
        assertEquals("space should be 1 after freeing one slot", 1, space);
        expected = new WhisperTargetChannel(root, false, false, null);
        id = list.append(expected);
        assertEquals("append should have filled slot 5", 5, id);
        actual = list.get((byte) 5);
        assertEquals("slot 5 should contain our target", expected, actual);

        list.clear();
        space = list.spaceRemaining();
        assertEquals("there should be 30 slots remaining after clear", 30, space);
    }

    public void testExceptions() {
        WhisperTargetList list = new WhisperTargetList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get((byte) -1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get((byte) 31));
        assertThrows(IndexOutOfBoundsException.class, () -> list.free((byte) -1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.free((byte) 31));
    }
}

