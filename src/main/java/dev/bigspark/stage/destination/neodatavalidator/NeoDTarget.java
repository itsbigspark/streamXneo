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

import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.ConfigGroups;
import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.api.StageDef;
import com.streamsets.pipeline.api.ValueChooserModel;
import com.streamsets.pipeline.api.ConfigDef.DisplayMode;

@StageDef(
    version = 1,
    label = "Neo4j Destination",
    description = "",
    icon = "neo4j-icon.png",
    recordsByRef = true,
    onlineHelpRefUrl = ""
)
@ConfigGroups(value = Groups.class)
@GenerateResourceBundle
public class NeoDTarget extends NeoTarget {
  
  /**Security Tab */

  //Authentication Type
  @ConfigDef(
    required = true,
    type = ConfigDef.Type.MODEL,
    label = "Authentication Type",
    defaultValue = "NONE",
    displayPosition = 10,
    group = "SECURITY",
    displayMode = DisplayMode.BASIC
  )
  @ValueChooserModel(AuthenticationValues.class)
  public String authenticationtype;
  
  //URL 
  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      defaultValue = " ",
      label = "URL",
      displayPosition = 10,
      group = "SECURITY",
      displayMode = DisplayMode.BASIC
  )
  public String url;

  //Username 
  @ConfigDef(
      required = false,
      type = ConfigDef.Type.CREDENTIAL,
      label = "Username",
      displayPosition = 10,
      dependsOn = "authenticationtype",
      triggeredByValue = "BASIC",
      group = "SECURITY",
      displayMode = DisplayMode.BASIC
  )
  public String username;

  //Password 
  @ConfigDef(
    required = false,
    type = ConfigDef.Type.CREDENTIAL,
    label = "Password",
    displayPosition = 10,
    dependsOn = "authenticationtype",
    triggeredByValue = "BASIC",
    group = "SECURITY",
    displayMode = DisplayMode.BASIC
  )
  public String password;

  //Database
  @ConfigDef(
    required = true,
    type = ConfigDef.Type.STRING,
    defaultValue = " ",
    label = "Database",
    displayPosition = 10,
    group = "SECURITY",
    displayMode = DisplayMode.BASIC
  )
  public String database;

  /**Advanced Properties */

  //Relationship
  @ConfigDef(
    required = false,
    type = ConfigDef.Type.STRING,
    defaultValue = " ",
    label = "Relationship",
    displayPosition = 10,
    group = "ADVANCED_PROPERTIES",
    displayMode = DisplayMode.ADVANCED
  )
  public String relationship;

 //Relationship Properties
 @ConfigDef(
  required = false,
  type = ConfigDef.Type.STRING,
  defaultValue = " ",
  label = "Save Strategy",
  displayPosition = 10,
  group = "ADVANCED_PROPERTIES",
  displayMode = DisplayMode.ADVANCED
)
  public String relationship_save_strategy;

  //Relationship Properties
 @ConfigDef(
  required = false,
  type = ConfigDef.Type.STRING,
  defaultValue = " ",
  label = "Properties",
  displayPosition = 10,
  group = "ADVANCED_PROPERTIES",
  displayMode = DisplayMode.ADVANCED
)
  public String properties;


  //Relationship Target Save Strategy
  @ConfigDef(
    required = false,
    type = ConfigDef.Type.STRING,
    defaultValue = " ",
    label = "Target Labels",
    displayPosition = 10,
    group = "ADVANCED_PROPERTIES",
    displayMode = DisplayMode.ADVANCED
  )
  public String relationship_target_labels;

  //Relationship Target Save Strategy
  @ConfigDef(
    required = false,
    type = ConfigDef.Type.STRING,
    defaultValue = " ",
    label = "Save Strategy Mode",
    displayPosition = 10,
    group = "ADVANCED_PROPERTIES",
    displayMode = DisplayMode.ADVANCED
  )
  public String relationship_target_save_mode;

  //Relationship Source Node Keys
  @ConfigDef(
    required = false,
    type = ConfigDef.Type.STRING,
    defaultValue = " ",
    label = "Source Node Keys",
    displayPosition = 10,
    group = "ADVANCED_PROPERTIES",
    displayMode = DisplayMode.ADVANCED
  )
  public String relationship_target_node_keys;


  /**Runtime Parameters */

  //Retry Timeout 
  @ConfigDef(
    required = false,
    type = ConfigDef.Type.STRING,
    defaultValue = " ",
    label = "Retry Timeout",
    displayPosition = 10,
    group = "RUNTIME_PARAMETERS",
    displayMode = DisplayMode.ADVANCED
  )
  public String retry_timeout;

  //Retry Timeout 
  @ConfigDef(
    required = false,
    type = ConfigDef.Type.STRING,
    defaultValue = " ",
    label = "Retry",
    displayPosition = 10,
    group = "RUNTIME_PARAMETERS",
    displayMode = DisplayMode.ADVANCED
  )
  public String retry;


  /** {@inheritDoc} */
  @Override
  public String getAuntheticationType() {
    return authenticationtype;
  }

  /** {@inheritDoc} */
  @Override
  public String getURL(){
    return url;
  }

  /** {@inheritDoc} */
  @Override
  public String getUsername() {
    return username;
  }

  /** {@inheritDoc} */
  @Override
  public String getPassword() {
    return password;
  }

  /** {@inheritDoc} */
  @Override
  public String getDatabase() {
    return database;
  }
 

  /** {@inheritDoc} */
  @Override
  public String getRelationship() {
    return relationship;
  }

  /** {@inheritDoc} */
  @Override
  public String getSaveStrategy() {
    return relationship_save_strategy;
  }


  /** {@inheritDoc} */
  @Override
  public String getProperties() {
    return properties;
  }

  /** {@inheritDoc} */
  @Override
  public String getTargetLabels() {
    return relationship_target_labels;
  }

  /** {@inheritDoc} */
  @Override
  public String getSaveMode() {
    return relationship_target_save_mode;
  }

  /** {@inheritDoc} */
  @Override
  public String getNodeKeys() {
    return relationship_target_node_keys;
  }

  /** {@inheritDoc} */
  @Override
  public String getRetryTimeout() {
    return retry_timeout;
  }

  /** {@inheritDoc} */
  @Override
  public String getRetry() {
    return retry;
  }
}
