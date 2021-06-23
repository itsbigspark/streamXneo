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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.bigspark.stage.lib.neodatavalidator.Errors;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import static org.neo4j.driver.Values.parameters;

public abstract class NeoTarget extends BaseTarget implements AutoCloseable {

  private static final Logger LOG = LoggerFactory.getLogger(NeoTarget.class);
  /**
   * Gives access to the UI configuration of the stage provided by the {@link NeoDTarget} class.
   */
  public abstract String getAuntheticationType();
  public abstract String getURL();
  public abstract String getUsername();
  public abstract String getPassword();
  public abstract String getQuery();

  private Driver driver;

  /** {@inheritDoc} */
  @Override
  protected List<ConfigIssue> init() {
    // Validate configuration values and open any required resources.
    List<ConfigIssue> issues = super.init();
    LOG.info("targetlog :: init");
    try {
      driver = GraphDatabase.driver(getURL(),AuthTokens.basic(getUsername(),getPassword()));
      Session session = driver.session(); 
    } catch (Exception e) {
      LOG.error("targetlog :: init error =>",e);
    }
    
    return issues;
  }

  @Override
    public void close() throws Exception
    {
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
    LOG.info("targetlog :: write batch 1");
    Iterator<Record> batchIterator = batch.getRecords();

    while (batchIterator.hasNext()) {
      LOG.info("targetlog :: write batch 2");
      Record record = batchIterator.next();
      try {
        LOG.info("targetlog :: write batch 3");
        //Call function to write record
        writeRecord(record);
      } 
      catch (Exception e) {
        LOG.info("targetlog :: write batch 4");
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

    LOG.info("targetlog :: write record process started");
    LOG.info("targetlog:: Input record => {}", record);
    LOG.info("targetlog :: Username => {} ",getUsername());
    LOG.info("targetlog :: Password => {} ",getPassword());
    LOG.info("targetlog :: URL => {} ",getURL());
    LOG.info("targetlog :: Query => {} ",getQuery());
    
    // Writes records to final destination
    try {
      //runQuery2(getQuery());
    } 
    catch(Throwable t){
      LOG.error("targetlog :: writeRecord error => ",t);
    }
  }

  private void runQuery2(String query){
    LOG.info("targetlog :: runQuery2 started");
    Map<String,Object> params = new HashMap<>();
    params.put("name", "Maison");
    params.put("location", "London");
    params.put("country", "UK" );

    try{
      Session session = driver.session(); 
      String greeting = session.writeTransaction( new TransactionWork<String>(){
        @Override
        public String execute( Transaction tx ){
          Result result = tx.run(getQuery(), params);
          return result.single().get(0).asString();
        }
      } );
            System.out.println(greeting);
    }       
    catch (Exception e) {
        e.printStackTrace();
        LOG.error("targetlog :: runQuery2 error => ",e);
      }

  }
  private void runQuery(String query){
    {
      try ( Session session = driver.session() )
      {
          String greeting = session.writeTransaction(new TransactionWork<String>()
          {
              @Override
              public String execute( Transaction tx )
              {
                  Result result = tx.run(query);
                  return result.single().get( 0 ).asString();
              }
          } );
          LOG.info("targetlog :: greeting {} ",greeting);
      }
    }
  }
  

  private void runQuery(Record record, String url,String username, String password, String query) throws SQLException{
    //CREATE (ee:Person { name: \"Emil\", from: \"Sweden\", klout: 99 })

      // Connecting
      LOG.error("targetlog :: runQuery 1");
      final String finalurl = "jdbc:neo4j:bolt://"+url;

      LOG.info("targetlog :: Final URL {} ",finalurl);

      try (Connection con = DriverManager.getConnection(finalurl, username, password)) {
        LOG.error("targetlog :: runQuery 2");

        try (PreparedStatement stmt = con.prepareStatement(query)) {
          LOG.error("targetlog :: runQuery 3");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                  LOG.info("targetlog :: query response => {}", rs.getString(1));
                   
                }
            }
        }
      }
  }

  

  private void processQuery(){
    String query = "name = 3,age = Jame,amount >= 50";
    String[] queryparts = query.split(",");
    for (int i = 0;i< queryparts.length;i++){
      System.out.println("queryparts[i] : " + queryparts[i]);
      System.out.println();
      
      String[] queryparts_temp = queryparts[i].split("\\s+");
      
      for (String string : queryparts_temp) {
          System.out.println("queryparts[j] : " + string);
      }
    }
  }

  
}
