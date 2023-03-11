/*
 * SPDX-License-Identifier: MIT
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2023 Mastodon4J
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
package org.mastodon4j.core;

import feign.Feign;
import feign.Response;
import feign.RequestTemplate;
import feign.Util;
import feign.http2client.Http2Client;
import org.mastodon4j.core.api.Accounts;
import org.mastodon4j.core.api.Apps;
import org.mastodon4j.core.api.BaseMastodonApi;
import org.mastodon4j.core.api.BaseStreaming;
import org.mastodon4j.core.api.Lists;
import org.mastodon4j.core.api.MastodonApi;
import org.mastodon4j.core.api.Notifications;
import org.mastodon4j.core.api.Statuses;
import org.mastodon4j.core.api.Streaming;
import org.mastodon4j.core.api.Timelines;
import org.mastodon4j.core.api.entities.AccessToken;
import org.mastodon4j.core.api.entities.Instance;
import org.mastodon4j.core.api.entities.Search;
import org.mastodon4j.core.impl.JsonUtil;
import org.mastodon4j.core.impl.MastodonStreaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class MastodonClient implements MastodonApi {
    public static final String USER_AGENT_NAME = "Mastodon4J";
    private final HttpClient httpClient;
    private final Feign.Builder builder;
    private final String restUrl;
    private final Supplier<String> authorizationSupplier;
    private final BaseMastodonApi globals;

    private Accounts accounts;
    private Apps apps;
    private Lists lists;
    private Notifications notifications;
    private Statuses statuses;
    private Streaming streaming;
    private Timelines timelines;

    MastodonClient(final HttpClient httpClient, final Feign.Builder builder, final String restUrl, final Supplier<String> authorizationSupplier) {
        this.httpClient = httpClient;
        this.builder = builder;
        this.restUrl = restUrl;
        this.authorizationSupplier = authorizationSupplier;
        this.globals = builder.target(BaseMastodonApi.class, restUrl);
    }

    /**
     * Creates an mastodon API instance for the given restUrl and access token.
     *
     * @param restUrl     the base URL of a mastodon server
     * @param accessToken the access token for the given instance
     * @return a new API ineraction instance
     */
    public static MastodonApi create(String restUrl, AccessToken accessToken) {
        requireNonNull(restUrl);
        requireNonNull(accessToken);

        final HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        final Feign.Builder builder = Feign.builder()
                .client(new Http2Client(httpClient))
                .encoder(MastodonClient::encode)
                .decoder(MastodonClient::decode)
                .requestInterceptor(template -> template.header("User-Agent", USER_AGENT_NAME))
                .requestInterceptor(template -> template.header("Authorization", accessToken.authorization()));

        return new MastodonClient(httpClient, builder, restUrl, accessToken::authorization);
    }

    private static Object decode(Response response, Type type) throws IOException {
        if (response.status() == 404 || response.status() == 204) {
            return Util.emptyValueOf(type);
        } else if (response.body() == null) {
            return null;
        }
        try (Reader reader = response.body().asReader(response.charset())) {
            // special handling for basic java types
            if (String.class.equals(type)) {
                StringWriter sw = new StringWriter();
                reader.transferTo(sw);
                return sw.toString();
            }
            return JsonUtil.fromJson(reader, type);
        }
    }

    private static void encode(Object object, Type bodyType, RequestTemplate template) {
        template.body(JsonUtil.toJson(object));
    }

    @Override
    public Accounts accounts() {
        if (accounts == null) {
            accounts = builder.target(Accounts.class, restUrl);
        }
        return accounts;
    }

    @Override
    public Apps apps() {
        if (apps == null) {
            apps = builder.target(Apps.class, restUrl);
        }
        return apps;
    }

    @Override
    public Lists lists() {
        if (lists == null) {
            lists = builder.target(Lists.class, restUrl);
        }
        return lists;
    }

    @Override
    public Notifications notifications() {
        if (notifications == null) {
            notifications = builder.target(Notifications.class, restUrl);
        }
        return notifications;
    }

    @Override
    public Statuses statuses() {
        if (statuses == null) {
            statuses = builder.target(Statuses.class, restUrl);
        }
        return statuses;
    }

    @Override
    public Streaming streaming() {
        if (streaming == null) {
            final WebSocket.Builder webSocketBuilder = httpClient
                    .newWebSocketBuilder()
                    .header("User-Agent", USER_AGENT_NAME)
                    .header("Authorization", authorizationSupplier.get());
            final String baseStreamingUri = instance().configuration().urls().streaming();
            final String streamingRestUrl = baseStreamingUri.replaceFirst("ws", "http");
            final BaseStreaming baseStreaming = builder.target(BaseStreaming.class, streamingRestUrl);
            streaming = new MastodonStreaming(baseStreaming, webSocketBuilder, baseStreamingUri);
        }
        return streaming;
    }

    @Override
    public Timelines timelines() {
        if (timelines == null) {
            timelines = builder.target(Timelines.class, restUrl);
        }
        return timelines;
    }

    @Override
    public Instance instance() {
        return globals.instance();
    }

    @Override
    public Search search(String query) {
        return globals.search(QueryOptions.of(query));
    }

    @Override
    public Search search(QueryOptions queryOptions) {
        return globals.search(queryOptions);
    }
}
