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

import java.util.ArrayList;

import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.ConfigGroups;
import com.streamsets.pipeline.api.FieldSelectorModel;
import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.api.StageDef;
import com.streamsets.pipeline.api.ValueChooserModel;

@StageDef(
    version = 1,
    label = "Neo4j Processor",
    description = "Custom Processor for Neo4j",
    icon = "neo4j-icon.png",
    onlineHelpRefUrl = ""
    
)
@ConfigGroups(Groups.class)
@GenerateResourceBundle
public class NeoDProcessor extends NeoProcessor {
  
  /** Data Preprocessor Config */
  @ConfigDef(
    required = false,
    type = ConfigDef.Type.MODEL,
    label = "Remove",
    displayPosition = 10,
    group = "DATAPREPROCESSOR"
  )
  @FieldSelectorModel(singleValued = false)
  public ArrayList<String> removelist;

  @ConfigDef(
    required = false,
    type = ConfigDef.Type.MODEL,
    defaultValue = "No",
    label = "Flatten",
    displayPosition = 10,
    group = "DATAPREPROCESSOR"
  )

  @ValueChooserModel(BinaryResponseValues.class)
  public String flatten;

  /** Cypher Config */
   @ConfigDef(
    required = false,
    type = ConfigDef.Type.TEXT,
    defaultValue = "Enter create query here",
    label = "Create",
    displayPosition = 10,
    group = "CYPHER"
  )
  public String createquery;

   /** Cypher Config */
   @ConfigDef(
    required = false,
    type = ConfigDef.Type.TEXT,
    defaultValue = "Enter create query here",
    label = "Match",
    displayPosition = 10,
    group = "CYPHER"
  )
  public String matchquery;

  /** {@inheritDoc} */
  @Override
  public ArrayList<String> getRemoveList() {
    return removelist;
  }

  /** {@inheritDoc} */
   @Override
   public String getFlatten() {
     return flatten;
   }

   /** {@inheritDoc} */
   @Override
   public String getCreateQuery() {
     return createquery;
   }

   /** {@inheritDoc} */
   @Override
   public String getMatchQuery() {
     return matchquery;
   }


}
