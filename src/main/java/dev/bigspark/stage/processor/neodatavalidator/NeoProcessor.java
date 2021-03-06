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
  public abstract String getQuery();


  /** {@inheritDoc} */
  @Override
  protected List<ConfigIssue> init() {
    LOG.info("processorlog :: init started");
    // Validate configuration values and open any required resources.
    List<ConfigIssue> issues = super.init();

    // If issues is not empty, the UI will inform the user of each configuration issue in the list.
    return issues;
  }

  /** {@inheritDoc} */
  @Override
  public void destroy() {
    // Clean up any open resources.
    LOG.info("processorlog :: destroy");
    super.destroy();
  }

  /** {@inheritDoc} */
  @Override
  protected void process(Record record, SingleLaneBatchMaker batchMaker) throws StageException {
    LOG.info("processorlog :: process started");
    LOG.info("processorlog :: Input record: {}", record);
    try {
      //Remove fields from record
      Record newrecord = applyRemove(record,getRemoveList());
      LOG.info("processorlog :: output record: {}", newrecord);
      
      batchMaker.addRecord(newrecord);

    } catch (Exception e) {
      LOG.error("processorlog :: process error => ",e);
      
    }
    
  }

  /**
   * Removes fields from a given record
   *
   * @param record the record to write to the destination.
   * @param removelist list of records to be removed
   */
  public Record applyRemove(Record record,ArrayList<String> removelist){
    LOG.info("processorlog :: applyRemove started");
    try {
      for (String field : removelist) {
        record.delete(field);
      }
    } catch (Exception e) {
      LOG.error("processorlog :: applyRemove error => ",e);
    }
    return record;
    
  }
 
}
