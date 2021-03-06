/*
 *
 *  Copyright 2017-2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package springfox.test.contract.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bug1767ListingScanner implements ApiListingScannerPlugin {

  // tag::api-listing-plugin[]
  @Override
  public List<ApiDescription> apply(DocumentationContext context) {
    return new ArrayList<ApiDescription>(
        Arrays.asList( //<1>
            new ApiDescription(
                "/bugs/1767",
                "This is a bug",
                Arrays.asList( //<2>
                    new OperationBuilder(
                        new CachingOperationNameGenerator())
                        .authorizations(new ArrayList())
                        .codegenMethodNameStem("bug1767GET") //<3>
                        .method(HttpMethod.GET)
                        .notes("This is a test method")
                        .parameters(
                            Arrays.asList( //<4>
                                new ParameterBuilder()
                                    .description("search by description")
                                    .type(new TypeResolver().resolve(String.class))
                                    .name("description")
                                    .parameterType("query")
                                    .parameterAccess("access")
                                    .required(true)
                                    .modelRef(new ModelRef("string")) //<5>
                                    .build()))
                        .build()),
                false)));
  }
  // tag::api-listing-plugin[]

  @Override
  public boolean supports(DocumentationType delimiter) {
    return DocumentationType.SWAGGER_2.equals(delimiter);
  }

}
