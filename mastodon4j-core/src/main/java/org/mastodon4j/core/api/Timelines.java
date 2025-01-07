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
package org.mastodon4j.core.api;

import feign.Param;
import feign.RequestLine;
import org.mastodon4j.core.api.entities.Status;

import java.util.List;

/**
 * Contains all timeline related REST call methods.
 *
 * @see <a href="https://docs.joinmastodon.org/methods/timelines/">mastodon/timelines</a>
 */
public interface Timelines {
    /**
     * <a href="https://docs.joinmastodon.org/methods/timelines/#public">View public timeline</a>.
     *
     * @return a list containing statuses from the public timeline
     */
    @RequestLine("GET /api/v1/timelines/public")
    List<Status> pub();

    /**
     * <a href="https://docs.joinmastodon.org/methods/timelines/#tag">View hashtag timeline</a>.
     *
     * @param hashtag the tag id without the leading {@code #} symbol
     * @return a list containing statuses from the hashtag timeline
     */
    @RequestLine("GET /api/v1/timelines/tag/{hashtag}}")
    List<Status> tag(@Param("hashtag") String hashtag);

    /**
     * <a href="https://docs.joinmastodon.org/methods/timelines/#home">View home timeline</a>.
     *
     * @return a list containing statuses from the local timeline
     */
    @RequestLine("GET /api/v1/timelines/home")
    List<Status> home();

    /**
     * <a href="https://docs.joinmastodon.org/methods/timelines/#list">View list timeline</a>.
     *
     * @param listId the list id
     * @return a list containing statuses from the list timeline
     */
    @RequestLine("GET /api/v1/timelines/list/{listId}")
    List<Status> list(@Param("listId") String listId);
}
