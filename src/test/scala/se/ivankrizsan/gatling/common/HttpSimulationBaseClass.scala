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
package se.ivankrizsan.gatling.common

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

import scala.concurrent.duration._

/**
  * Abstract Gatling simulation class that specifies properties common to multiple Gatling simulations.
  * Certain simulation parameters have been extracted to class variables which allows for subclasses
  * to modify certain behaviour of the basic simulation by setting new values of these variables.
  *
  * @author Ivan Krizsan
  */
abstract class HttpSimulationBaseClass extends Simulation {
    /* Base URL of requests sent in scenario 1. */
    var scenario1BaseURL = ""
    /* Additional request path appended after the base URL in scenario 1. */
    var scenario1RequestPath = ""
    /* Final number of users in the simulation. */
    var finalUserCount = 10
    /* Time period after which the number of users is to reach the final number of users. */
    var userCountRampUpTime : FiniteDuration = (20 seconds)

    /*
     * Performs initialization of the simulation before it is executed.
     * Initialization must be performed this way in order to allow for subclasses to
     * modify instance variables and for those modifications to affect the resulting
     * simulation configuration.
     */
    before {
        doSetUp()
    }

    /**
      * Creates the HTTP protocol builder used in the simulation.
      */
    def createHttpProtocolBuilder(): HttpProtocolBuilder = {
        val httpProtocolBuilder = http
            .baseURL(scenario1BaseURL)
            .acceptHeader("text/plain")
            .userAgentHeader("Gatling")
        httpProtocolBuilder
    }

    /**
      * Creates the scenario builder used in the simulation.
      */
    def createScenarioBuilder(): ScenarioBuilder = {
        val scenarioBuilder = scenario("Scenario1")
            .exec(
                http("myRequest1")
                    .get("/" + scenario1RequestPath)
            )
        scenarioBuilder
    }

    /**
      * Performs setup of the simulation.
      */
    def doSetUp(): Unit = {
        val theScenarioBuilder = createScenarioBuilder()
        val theHttpProtocolBuilder = createHttpProtocolBuilder()

        setUp(
            theScenarioBuilder.inject(rampUsers(finalUserCount).over(userCountRampUpTime))
        ).protocols(theHttpProtocolBuilder)
    }
}
