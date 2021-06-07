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
package dev.bigspark.stage.processor.neodatavalidator;

import dev.bigspark.stage.lib.neodatavalidator.Errors;

import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.base.OnRecordErrorException;
import com.streamsets.pipeline.api.base.SingleLaneRecordProcessor;

import java.util.List;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NeoProcessor extends SingleLaneRecordProcessor {
  private static final Logger LOG = LoggerFactory.getLogger(NeoProcessor.class);
  /**
   * Gives access to the UI configuration of the stage provided by the {@link SampleDProcessor} class.
   */
  public abstract String getJSONValidator();
  public abstract String getRemoveKeep();
  public abstract String getFlatten();
  public abstract String getQuery();

  JSONObject jsonSchemaObject;
  Schema schema;

  /** {@inheritDoc} */
  @Override
  protected List<ConfigIssue> init() {
    // Validate configuration values and open any required resources.
    List<ConfigIssue> issues = super.init();

    // Ensure that data format is only JSON
    if (!getJSONValidator().equals("JSON")) {
      issues.add(
          getContext().createConfigIssue(
              Groups.JSONValidator.name(), "config", Errors.ERROR_03, "Here's what's wrong..."
          )
      );
    }
    //Confirm data input is of JSON format

    // If issues is not empty, the UI will inform the user of each configuration issue in the list.
    return issues;
  }

  /** {@inheritDoc} */
  @Override
  public void destroy() {
    // Clean up any open resources.
    super.destroy();
  }

  /** {@inheritDoc} */
  @Override
  protected void process(Record record, SingleLaneBatchMaker batchMaker) throws StageException {
    LOG.info("Input record: {}", record);

    //Read record value as string and tokenize for processing 

    try {
      JSONObject jsonObject = new JSONObject(
        new JSONTokener(record.get(getJSONValidator()).getValue().toString()));

      this.schema.validate(jsonObject);
      
    } catch (JSONException e) {
      throw new OnRecordErrorException(record, Errors.ERROR_02, e);
    } catch (ValidationException e) {
      throw new OnRecordErrorException(record, Errors.ERROR_02, e);
    }
    
    LOG.info("Output record: {}", record);
    
    // This example is a no-op
    batchMaker.addRecord(record);
  }

}
