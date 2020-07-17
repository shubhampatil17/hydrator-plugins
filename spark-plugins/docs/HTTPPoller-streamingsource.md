<!--- 
 * Copyright © 2020 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 -->
# HTTP Poller Streaming Source

Description
-----------
This is a streaming source that will fetch data from a specified URL at a given interval and
pass the results to the next plugin. This source will return one record for each request to
the specified URL. The record will contain a timestamp, the URL that was requested, the response code
of the response and the set of response headers in a map<string, string> format, and the body of the response.

Use Case
--------
The source is used whenever you need to fetch data from a URL at a regular interval. It could be
used to fetch Atom or RSS feeds regularly, or to fetch the status of an external system.


Properties
----------
**referenceName:** This will be used to uniquely identify this source for lineage, annotating metadata, etc.

**url:** Required The URL to fetch data from.

**interval:** Required The time to wait between fetching data from the URL in seconds.

**requestHeaders:** An optional string of header values to send in each request where the keys and values are
delimited by a colon (":") and each pair is delimited by a newline ("\n").

**charset:** The charset of the content returned by the URL. Defaults to UTF-8.

**followRedirects:** Whether to automatically follow redirects. Defaults to true.

**connectTimeout:** The time in milliseconds to wait for a connection. Set to 0 for infinite. Defaults to 60000 (1 minute).

**readTimeout:** The time in milliseconds to wait for a read. Set to 0 for infinite. Defaults to 60000 (1 minute).

Example
-------
This example fetches data from a URL every hour using a custom user agent:

```json
{
    "name": "HTTPPoller",
    "type": "streamingsource",
    "properties": {
        "url": "http://example.com/sampleEndpoint",
        "interval": "60",
        "requestHeaders": "User-Agent:HydratorPipeline\nAccept:application/json"
    }
}
```

The contents will output records with this schema:

| field name     | type                |
| -------------- | ------------------- |
| ts             | long                |
| url            | string              |
| responseCode   | int                 |
| headers        | map<string, string> |
| body           | string              |

All fields will be always be included, but the body might be an empty string.
