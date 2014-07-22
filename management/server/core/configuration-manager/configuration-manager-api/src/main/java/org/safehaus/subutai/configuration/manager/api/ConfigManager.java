/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.safehaus.subutai.configuration.manager.api;


import org.safehaus.subutai.shared.protocol.Agent;

import com.google.gson.JsonObject;


/**
 *
 */
public interface ConfigManager {

    public void injectConfiguration( Agent agent, JsonObject config );

    public String getProperty( JsonObject config, String path );

    public void setProperty( JsonObject config, String path, String value );

    public JsonObject getConfiguration( Agent agent, String configPathFilename, ConfigTypeEnum configTypeEnum );

    public JsonObject getConfigurationJson( String configPathFilename, ConfigTypeEnum configTypeEnum );
}
