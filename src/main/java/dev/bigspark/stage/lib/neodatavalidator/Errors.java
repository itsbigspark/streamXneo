/*
 * Copyright 2017 StreamSets Inc.
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
 */
package dev.bigspark.stage.lib.neodatavalidator;

import com.streamsets.pipeline.api.ErrorCode;
import com.streamsets.pipeline.api.GenerateResourceBundle;

@GenerateResourceBundle
public enum Errors implements ErrorCode {

  ERROR_00("A configuration issue has occured: {}"),
  ERROR_01("Specific reason writing record failed: {}"),
  ERROR_02("JSON supplied not parsed: {}"),
  ERROR_03("A different data type found whilst JSON was exepected: {}"),
  ;
  private final String msg;

  Errors(String msg) {
    this.msg = msg;
  }

  /** {@inheritDoc} */
  @Override
  public String getCode() {
    return name();
  }

  /** {@inheritDoc} */
  @Override
  public String getMessage() {
    return msg;
  }
}
