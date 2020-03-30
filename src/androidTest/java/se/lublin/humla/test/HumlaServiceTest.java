/*
 * Copyright (C) 2015 Andrew Comminos
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

import android.content.Intent;
import android.os.RemoteException;
import android.test.ServiceTestCase;

import se.lublin.humla.HumlaService;
import se.lublin.humla.IHumlaService;
import se.lublin.humla.model.Server;

/**
 * Tests to ensure the integrity of {@link HumlaService}'s state.
 * Created by andrew on 02/02/15.
 */
public class HumlaServiceTest extends ServiceTestCase<HumlaService> {
    private static final Server DUMMY_SERVER = new Server(-1, "dummy","example.com", 64738,
            "dummy_user", "dummy_pass");

    public HumlaServiceTest() {
        super(HumlaService.class);
    }

    /**
     * Tests the state of a HumlaService prior to connection.
     */
    public void testPreconnection() throws RemoteException {
        Intent intent = new Intent(getContext(), HumlaService.class);
        intent.putExtra(HumlaService.EXTRAS_SERVER, DUMMY_SERVER);
        startService(intent);
        IHumlaService service = getService();
        assertFalse(service.isReconnecting());
        assertNull(service.getConnectionError());
        assertEquals(HumlaService.ConnectionState.DISCONNECTED, service.getConnectionState());
        assertEquals(DUMMY_SERVER, service.getTargetServer());
    }

}
