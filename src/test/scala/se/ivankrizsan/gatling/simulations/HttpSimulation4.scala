/*
 * Copyright 2016-2020 Ivan Krizsan
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
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

import scala.concurrent.duration._

/**
  * Example Gatling load test simulating a number of users that rises up to 10 users over
  * a period of 20 seconds.
  * Run this simulation with:
  * mvn -Dgatling.simulation.name=HttpSimulation4 gatling:test
  *
  * @author Ivan Krizsan
  */
class HttpSimulation4 extends Simulation {

    val theHttpProtocolBuilder: HttpProtocolBuilder = http
        .baseUrl("http://computer-database.gatling.io")

    val theScenarioBuilder: ScenarioBuilder = scenario("Scenario1")
        .exec(
            http("myRequest1")
                .get("/"))

    setUp(
        /*
         * Increase the number of users that sends requests in the scenario Scenario1 to
         * ten users during a period of 20 seconds.
         */
        theScenarioBuilder.inject(rampUsers(20).during(5 seconds))
    ).protocols(theHttpProtocolBuilder)
}
