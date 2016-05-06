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

import se.ivankrizsan.gatling.common.HttpSimulationBaseClass

import scala.concurrent.duration._

/**
  * Example Gatling load test that is based on an abstract base class.
  * Run this simulation with:
  * mvn -Dgatling.simulation.name=HttpSimulation8 gatling:execute
  *
  * @author Ivan Krizsan
  */
class HttpSimulation8 extends HttpSimulationBaseClass {
    scenario1BaseURL = "http://computer-database.gatling.io"
    scenario1RequestPath = "computers"
    finalUserCount = 4
    userCountRampUpTime = (5 seconds)
}
