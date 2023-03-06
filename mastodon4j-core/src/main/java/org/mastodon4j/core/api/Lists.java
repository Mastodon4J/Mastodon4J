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
package org.mastodon4j.core.api;

import feign.Param;
import feign.RequestLine;
import org.mastodon4j.core.api.entities.Account;
import org.mastodon4j.core.api.entities.MList;

import java.util.List;

/**
 * Contains all lists related REST call methods.
 *
 * @see <a href="https://docs.joinmastodon.org/methods/lists/">mastodon/lists</a>
 */
public interface Lists {
    /**
     * <a href="https://docs.joinmastodon.org/methods/lists/#get">View your lists</a>
     * <p>
     * Fetch all lists that the user owns.
     *
     * @return list of defined user lists
     */
    @RequestLine("GET /api/v1/lists")
    List<MList> get();

    /**
     * <a href="https://docs.joinmastodon.org/methods/lists/#get-one">Show a single list</a>
     * <p>
     * Fetch the list with the given ID. Used for verifying the title of a list, and which replies to show within
     * that list.
     *
     * @param id the list id
     * @return list of defined user lists
     */
    @RequestLine("GET /api/v1/lists/{id}")
    MList get(@Param("id") String id);

    /**
     * <a href="https://docs.joinmastodon.org/methods/lists/#accounts">View accounts in a list</a>.
     * <p>
     * Accounts which within the given list id.
     *
     * @param id the account id
     * @return a list of list members
     */
    @RequestLine("GET /api/v1/lists/{id}/accounts")
    List<Account> accounts(@Param("id") String id);
}
