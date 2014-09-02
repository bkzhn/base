package org.safehaus.subutai.impl.solr.handler;


import org.safehaus.subutai.api.commandrunner.AgentResult;
import org.safehaus.subutai.api.commandrunner.Command;
import org.safehaus.subutai.api.solr.Config;
import org.safehaus.subutai.common.enums.NodeState;
import org.safehaus.subutai.common.protocol.AbstractOperationHandler;
import org.safehaus.subutai.impl.solr.SolrImpl;
import org.safehaus.subutai.common.protocol.Agent;


public class StartNodeOperationHandler extends AbstractOperationHandler<SolrImpl>
{
	private final String lxcHostname;


	public StartNodeOperationHandler(SolrImpl manager, String clusterName, String lxcHostname) {
		super(manager, clusterName);
		this.lxcHostname = lxcHostname;
		productOperation = manager.getTracker().createProductOperation(Config.PRODUCT_KEY,
				String.format("Starting node %s in %s", lxcHostname, clusterName));
	}


	@Override
	public void run() {
		Config config = manager.getCluster(clusterName);

		if (config == null) {
			productOperation.addLogFailed(
					String.format("Installation with name %s does not exist\nOperation aborted", clusterName));
			return;
		}

		Agent node = manager.getAgentManager().getAgentByHostname(lxcHostname);

		if (node == null) {
			productOperation.addLogFailed(
					String.format("Agent with hostname %s is not connected\nOperation aborted", lxcHostname));
			return;
		}

		if (!config.getNodes().contains(node)) {
			productOperation.addLogFailed(
					String.format("Agent with hostname %s does not belong to cluster %s", lxcHostname, clusterName));
			return;
		}

		productOperation.addLog("Starting node...");

		Command startCommand = manager.getCommands().getStartCommand(node);
		manager.getCommandRunner().runCommand(startCommand);
		Command statusCommand = manager.getCommands().getStatusCommand(node);
		manager.getCommandRunner().runCommand(statusCommand);
		AgentResult result = statusCommand.getResults().get(node.getUuid());
		NodeState nodeState = NodeState.UNKNOWN;

		if (result != null) {
			if (result.getStdOut().contains("is running")) {
				nodeState = NodeState.RUNNING;
			} else if (result.getStdOut().contains("is not running")) {
				nodeState = NodeState.STOPPED;
			}
		}

		if (NodeState.RUNNING.equals(nodeState)) {
			productOperation.addLogDone(String.format("Node on %s started", lxcHostname));
		} else {
			productOperation.addLogFailed(
					String.format("Failed to start node %s. %s", lxcHostname, startCommand.getAllErrors()));
		}
	}
}
