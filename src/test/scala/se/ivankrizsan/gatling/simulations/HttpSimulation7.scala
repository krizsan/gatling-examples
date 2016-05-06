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
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

/**
  * Example Gatling load test that sends one HTTP GET requests to a URL.
  * The resulting HTTP status code should be one of the status codes in a list of expected status codes.
  * The response body is examined and the request is considered to have failed if the specified regular
  * expression is not matched.
  * Run this simulation with:
  * mvn -Dgatling.simulation.name=HttpSimulation7 gatling:execute
  *
  * @author Ivan Krizsan
  */
class HttpSimulation7 extends Simulation {
    val theHttpProtocolBuilder = http
        .baseURL("http://computer-database.gatling.io")

    val theScenarioBuilder = scenario("Scenario1")
        .exec(
            http("Request Computers List")
                .get("/computers")
                /* Several checks on the response can be specified. */
                .check(
                    /* Check that the HTTP status returned is 200 or 201. */
                    status.find.in(200, 202),
                    /* Check that there is at least one match of the supplied regular expression in the response body. */
                    regex("Computer database").count.greaterThanOrEqual(1)
            )
        )

    setUp(
        theScenarioBuilder.inject(atOnceUsers(1))
    ).protocols(theHttpProtocolBuilder)
}
