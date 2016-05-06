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
  * Example Gatling load test that sends one HTTP GET requests to a bad URL.
  * The resulting HTTP status code should be 404, which is verified in the simulation.
  * In addition, we also verify that the maximum response time during the simulation is less than 200 ms.
  * Run this simulation with:
  * mvn -Dgatling.simulation.name=HttpSimulation6 gatling:execute
  *
  * @author Ivan Krizsan
  */
class HttpSimulation6 extends Simulation {
    val theHttpProtocolBuilder = http
        .baseURL("http://computer-database.gatling.io")

    val theScenarioBuilder = scenario("Scenario1")
        .exec(
            http("Bad Request")
                .get("/unknown")
                /*
                 * Check the response of this request. It should be a HTTP status 404.
                 * Since the expected result is 404, the request will be verified as being OK
                 * and the simulation will thus succeed.
                 */
                .check(
                    status.is(404),
                    regex("Action not found").count.is(2)
                )
        )

    setUp(
        theScenarioBuilder.inject(atOnceUsers(1))
    )
        /*
         * This asserts that, for all the requests in all the scenarios in the simulation
         * the maximum response time should be less than 200 ms.
         * If this is not the case when the simulation runs, the simulation will considered to have failed.
         */
        .assertions(
            global.responseTime.max.lessThan(500),
            forAll.failedRequests.count.lessThan(5),
            details("Bad Request").successfulRequests.percent.greaterThan(90)
        )
        .protocols(theHttpProtocolBuilder)
}
