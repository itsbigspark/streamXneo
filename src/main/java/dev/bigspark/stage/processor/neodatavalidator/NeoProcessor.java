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

import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.base.SingleLaneRecordProcessor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NeoProcessor extends SingleLaneRecordProcessor {
  private static final Logger LOG = LoggerFactory.getLogger(NeoProcessor.class);
  /**
   * Gives access to the UI configuration of the stage provided by the {@link SampleDProcessor} class.
   */
  public abstract ArrayList<String> getRemoveList();
  public abstract String getFlatten();
  public abstract String getCreateQuery();
  public abstract String getMatchQuery();


  /** {@inheritDoc} */
  @Override
  protected List<ConfigIssue> init() {
    // Validate configuration values and open any required resources.
    List<ConfigIssue> issues = super.init();


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
    try {
      
      //Remove fields from record
      Record newrecord = applyRemove(record,getRemoveList());
      LOG.info("Output record: {}", newrecord);
      
      batchMaker.addRecord(newrecord);

    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  /** Remove field */
  public Record applyRemove(Record record,ArrayList<String> removelist){

    for (String field : removelist) {
      record.delete(field);
    }

    return record;
    
  }

 
  /** Flatten nested JSON data */
  public void applyFlatten(Record record){

  }

  /** Run Cypher query */
  public void runQuery(Record record,String query){
    
  }

}
