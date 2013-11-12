/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.safehaus.kiskis.mgmt.shared.protocol;

import com.google.gson.Gson;
import org.safehaus.kiskis.mgmt.shared.protocol.api.CommandInterface;

/**
 * @author dilshat
 */
public class CommandJson {

    private static Gson gson = new Gson();

    public static Request getRequest(String json) {
        Command cmd = gson.fromJson(json, Command.class);
        if (cmd.command != null) {
            return (Request) cmd.command;
        }

        return null;
    }

    public static Response getResponse(String json) {
        Command cmd = gson.fromJson(json, Command.class);
        if (cmd.response != null) {
            return (Response) cmd.response;
        }
        return null;
    }

    public static String getJson(CommandInterface cmd) {
        return gson.toJson(cmd);
    }

//    public static String getJson(Request request) {
//        return gson.toJson(request);
//    }
}
