/*
 * SPDX-License-Identifier: MIT
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2023-2025 Mastodon4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.mastodon4j.core.impl;

import org.mastodon4j.core.MastodonException;
import org.mastodon4j.core.api.EventStream;
import org.mastodon4j.core.api.entities.Event;
import org.mastodon4j.core.api.entities.Subscription;

import java.net.http.WebSocket;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class MastodonEventStream implements EventStream, WebSocket.Listener {
    private final CopyOnWriteArrayList<Consumer<Event>> eventConsumers;
    private final StringBuilder buffer;
    private final AtomicReference<WebSocket> webSocketReference;

    MastodonEventStream() {
        this.buffer = new StringBuilder();
        this.eventConsumers = new CopyOnWriteArrayList<>();
        this.webSocketReference = new AtomicReference<>();
    }

    private void withWebsocket(Consumer<WebSocket> action) {
        final WebSocket webSocket = webSocketReference.get();
        if (webSocket != null) {
            action.accept(webSocket);
        }
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        webSocketReference.set(webSocket);
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        buffer.append(data);
        if (last) {
            final String eventContent = buffer.toString();
            buffer.setLength(0);
            final Event event = JsonUtil.fromJson(eventContent, Event.class);
            eventConsumers.forEach(statusConsumer -> statusConsumer.accept(event));
        }
        webSocket.request(1);
        return null;
    }

    @Override
    public void registerConsumer(Consumer<Event> statusConsumer) {
        eventConsumers.add(Objects.requireNonNull(statusConsumer, "statusConsumer must not be null"));
    }

    @Override
    public void changeSubscription(Subscription subscription) {
        final String message = JsonUtil.toJson(Objects.requireNonNull(subscription, "subscription must not be null"));
        withWebsocket(webSocket -> webSocket.sendText(message, true).join());
    }

    @Override
    public void close() throws MastodonException {
        withWebsocket(webSocket -> webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "").join());
    }
}
