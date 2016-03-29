/*
 * Copyright 2016 Ivan Krizsan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.ivankrizsan.gatling.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.Body

/**
  * Example Gatling load test that sends one HTTP POST requests to a URL.
  * The body of the POST request contains the string "Farty Towels!".
  * Two ways of passing HTTP headers are shown.
  * Two URL parameters, "login" and "password", are passed in the URL of the request.
  * Note that this simulation should fail, since a non-expected HTTP status code should be returned.
  * Run this simulation with:
  * mvn -Dgatling.simulation.name=HttpSimulation5 gatling:execute
  *
  * @author Ivan Krizsan
  */
class HttpSimulation5 extends Simulation {

    val theHttpProtocolBuilder = http
        .baseURL("http://computer-database.gatling.io")
        .acceptHeader("application/xml, text/html, text/plain, application/json, */*")
        .acceptCharsetHeader("UTF-8")
        .acceptEncodingHeader("gzip, deflate")

    val theCommonHeaders = Map(
        "Accept" -> "application/xml, text/html, text/plain, application/json, */*",
        "Accept-Encoding" -> "gzip, deflate")

    val theBody : Body = StringBody("Farty Towels!")

    val theScenarioBuilder = scenario("Scenario1")
        .exec(
            http("Login and Post Data")
                .post("/computers")
                .body(theBody)
                .headers(theCommonHeaders)
                .queryParam("login", "admin")
                .queryParam("password", "secret")
        )

    setUp(
        theScenarioBuilder.inject(atOnceUsers(1))
    ).protocols(theHttpProtocolBuilder)
}
