/**
 * Copyright (c) Codice Foundation
 * <p/>
 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. A copy of the GNU Lesser General Public License
 * is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package org.codice.alliance.video.stream.mpegts.plugins;

import java.io.IOException;

import org.codice.alliance.video.stream.mpegts.Context;

public class FlushPacketBufferStreamShutdownPlugin extends BaseStreamShutdownPlugin {
    @Override
    protected void doOnShutdown(Context context) throws StreamShutdownException {
        try {
            context.getUdpStreamProcessor()
                    .getPacketBuffer()
                    .flushAndRotate()
                    .ifPresent(file -> context.getUdpStreamProcessor()
                            .doRollover(file));
        } catch (IOException e) {
            throw new StreamShutdownException(
                    "unable to rotate and ingest final data during shutdown",
                    e);
        }
    }
}
