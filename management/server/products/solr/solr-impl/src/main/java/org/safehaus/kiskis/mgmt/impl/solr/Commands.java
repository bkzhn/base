/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.safehaus.kiskis.mgmt.impl.solr;

import java.util.Set;
import org.safehaus.kiskis.mgmt.api.commandrunner.Command;
import org.safehaus.kiskis.mgmt.api.commandrunner.RequestBuilder;
import org.safehaus.kiskis.mgmt.shared.protocol.Agent;
import org.safehaus.kiskis.mgmt.shared.protocol.Util;
import org.safehaus.kiskis.mgmt.shared.protocol.enums.OutputRedirection;

/**
 *
 * @author dilshat
 */
public class Commands {

    public static Command getInstallCommand(Set<Agent> agents) {
        return SolrImpl.getCommandRunner().createCommand(
                new RequestBuilder("sleep 10 ; apt-get --force-yes --assume-yes install ksks-solr")
                .withTimeout(90).withStdOutRedirection(OutputRedirection.NO),
                agents);
    }

    public static Command getStartCommand(Agent agent) {
        return SolrImpl.getCommandRunner().createCommand(
                new RequestBuilder("service solr start").withStdOutRedirection(OutputRedirection.NO),
                Util.wrapAgentToSet(agent));
    }

    public static Command getStopCommand(Agent agent) {
        return SolrImpl.getCommandRunner().createCommand(
                new RequestBuilder("service solr stop"),
                Util.wrapAgentToSet(agent));
    }

    public static Command getStatusCommand(Agent agent) {
        return SolrImpl.getCommandRunner().createCommand(
                new RequestBuilder("service solr status"),
                Util.wrapAgentToSet(agent));
    }

}
