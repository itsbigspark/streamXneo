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
package dev.bigspark.stage.destination.neodatavalidator;

import com.streamsets.pipeline.api.Batch;
import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.base.BaseTarget;
import com.streamsets.pipeline.api.base.OnRecordErrorException;
import com.streamsets.pipeline.api.impl.Utils;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.bigspark.stage.lib.neodatavalidator.Errors;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public abstract class NeoTarget extends BaseTarget implements AutoCloseable {

  private static final Logger LOG = LoggerFactory.getLogger(NeoTarget.class);
  /**
   * Gives access to the UI configuration of the stage provided by the {@link NeoDTarget} class.
   */
  public abstract String getAuntheticationType();
  public abstract String getURL();
  public abstract String getUsername();
  public abstract String getPassword();

  private Driver driver;

  /** {@inheritDoc} */
  @Override
  protected List<ConfigIssue> init() {
    LOG.info("targetlog :: init started");
    // Validate configuration values and open any required resources.
    List<ConfigIssue> issues = super.init();
    
    try {
      driver = GraphDatabase.driver(getURL(),AuthTokens.basic(getUsername(),getPassword()));
    } catch (Exception e) {
      LOG.error("targetlog :: init error =>",e);
    }
    
    return issues;
  }

  @Override
    public void close() throws Exception
    {
      LOG.info("targetlog :: connection closed");
      driver.close();
    }

  /** {@inheritDoc} */
  @Override
  public void destroy() {
    LOG.info("targetlog :: destroy");
    // Clean up any open resources.
    super.destroy();
  }

  /** {@inheritDoc} */
  @Override
  public void write(Batch batch) throws StageException {
    Iterator<Record> batchIterator = batch.getRecords();

    while (batchIterator.hasNext()) {
      Record record = batchIterator.next();
      try {
        //Call function to write record
        writeRecord(record);
      } 
      catch (Exception e) {
        switch (getContext().getOnErrorRecord()) {
          case DISCARD:
            break;
          case TO_ERROR:
            getContext().toError(record, Errors.ERROR_01, e.toString());
            break;
          case STOP_PIPELINE:
            throw new StageException(Errors.ERROR_01, e.toString());
          default:
            throw new IllegalStateException(
                Utils.format("Unknown OnError value '{}'", getContext().getOnErrorRecord(), e)
            );
        }
      }
    }
  }

  /**
   * Writes a single record to the destination.
   *
   * @param record the record to write to the destination.
   * @throws OnRecordErrorException when a record cannot be written.
   */
  private void writeRecord(Record record) throws OnRecordErrorException {
    // This is a contrived example, normally you may be performing an operation that could throw
    // an exception or produce an error condition. In that case you can throw an OnRecordErrorException
    // to send this record to the error pipeline with some details.

    LOG.info("targetlog :: writeRecord started");
    LOG.info("targetlog::  Input record => {}", record);
    LOG.info("targetlog :: Username => {} ",getUsername());
    LOG.info("targetlog :: Password => {} ",getPassword());
    LOG.info("targetlog :: URL => {} ",getURL());
    
    try {
    //Return all fields in record
    Set<String> fields = record.getEscapedFieldPaths();
  
    //Remove first item in set which is empty
    fields.removeIf(x -> (x ==""));

    String value = "";
    String query = "CREATE (a:Record {";
    Map<String,Object> params = new HashMap<>();

   
      for (String field : fields) {

        //Remove backslash from field name 
        field = field.replaceAll("/","");

        //Get value from record using field name
        value = record.get("/" + field).getValueAsString();

        //Attach new field and value to existing query
        query  += field + ": $" + field + " ,";

        //Add field and value to params to write to destination
        params.put(field, value);
    } 
    // remove last string and close query bracket
    query  = query.substring(0, query.length() - 1) + "})";

    //Writes record to Neo4j destination provided
    writeRecordToDestination(query,params);

    } 
    catch(Throwable t){
      LOG.error("targetlog :: writeRecord error => ",t);
    }
  }
  /** Write record to Neo4j destination provided */
  private void writeRecordToDestination(String query, Map<String,Object> params){

    LOG.info("targetlog :: writeRecordToDestination started");

    try{
      Session session = driver.session(); 
        session.writeTransaction( tx -> {
          tx.run(query,params);
          return 1;
      } );
    }       
    catch (Exception e) {
        e.printStackTrace();
        LOG.error("targetlog :: writeRecordToDestination error => ",e);
      }

  }
   
}
