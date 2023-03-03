/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2023-2023 Mastodon4J
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
package org.mastodon4j.core.api.entities;

/**
 * @param access_token the access token value
 * @param token_type the token type
 * @param scope the access scope
 * @param created_at the creation time
 */
public record AccessToken(String access_token,
                          String token_type,
                          String scope,
                          String created_at) {
    /**
     * Returns the authorization header value.
     *
     * @return the authorization header
     */
    public String authorization() {
        return String.format("%s %s", token_type, access_token);
    }

    /**
     * Creates a new authorization token with the given bearer token string value.
     *
     * @param accessToken the access token string
     * @return the authorization token instance
     */
    public static  AccessToken create(String accessToken) {
        return  new AccessToken(accessToken, "Bearer", null, null);
    }
}
