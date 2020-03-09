/*
 * Copyright 2017 Herman Cheung
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package hkhc.electricspock.runner

import hkhc.electricspock.runner.testdata.NoSpecification
import hkhc.electricspock.runner.testdata.OneNonSpec
import hkhc.electricspock.runner.testdata.OneSpecification
import hkhc.electricspock.runner.testdata.TwoSpecifications
import spock.lang.Specification
import spock.lang.Title

import static hkhc.electricspock.runner.SpecUtils.getSpecClasses

@Title("find all Specification inner classes")
class GetSpecClassesSpec extends Specification {

  def "Class without inner Specification class"() {
    expect:
    getSpecClasses(NoSpecification).size() == 0
  }

  def "Class with one inner Specification class"() {
    expect:
    getSpecClasses(OneSpecification).size() == 1
  }

  def "Class with one inner non-Specification class"() {
    expect:
    getSpecClasses(OneNonSpec).size() == 0
  }

  def "Class with two inner Specification classes"() {
    expect:
    getSpecClasses(TwoSpecifications).size() == 2
  }
}