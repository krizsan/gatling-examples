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

import scala.concurrent.duration._

/**
  * Example Gatling load test that sends two HTTP GET requests with a short pause between.
  * The first request will be redirected, again making it look like there were two requests sent.
  * The second request will not be redirected.
  * Run this simulation with:
  * mvn -Dgatling.simulation.name=HttpSimulation2 gatling:execute
  *
  * @author Ivan Krizsan
  */
class HttpSimulation2 extends Simulation {

    val theHttpProtocolBuilder = http
        .baseURL("http://computer-database.gatling.io")

    /*
     * This scenario consists of two GET requests; one to the base URL and one to /computers relative
     * to the base URL.
     * Between the requests there will be a pause for five seconds.
     * Note that in order to get access to different durations, we must add the following import:
     * import scala.concurrent.duration._
     */
    val theScenarioBuilder = scenario("Scenario1")
        .exec(
            http("GET to base URL")
                .get("/"))
        .pace(4 seconds)
        .exec(
            http("GET to /computers")
                .get("/computers"))

    setUp(
        theScenarioBuilder.inject(atOnceUsers(1))
    ).protocols(theHttpProtocolBuilder)
}
