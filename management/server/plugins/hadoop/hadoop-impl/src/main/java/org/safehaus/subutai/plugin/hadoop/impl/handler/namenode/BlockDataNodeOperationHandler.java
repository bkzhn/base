package org.safehaus.subutai.plugin.hadoop.impl.handler.namenode;


import org.safehaus.subutai.common.protocol.AbstractOperationHandler;
import org.safehaus.subutai.common.protocol.Agent;
import org.safehaus.subutai.common.tracker.ProductOperation;
import org.safehaus.subutai.core.command.api.command.Command;
import org.safehaus.subutai.plugin.hadoop.api.HadoopClusterConfig;
import org.safehaus.subutai.plugin.hadoop.impl.HadoopImpl;


public class BlockDataNodeOperationHandler extends AbstractOperationHandler<HadoopImpl>
{

    private String lxcHostName;


    public BlockDataNodeOperationHandler( HadoopImpl manager, String clusterName, String lxcHostName )
    {
        super( manager, clusterName );
        this.lxcHostName = lxcHostName;
        productOperation = manager.getTracker().createProductOperation( HadoopClusterConfig.PRODUCT_KEY,
                String.format( "Blocking DataNode in %s", clusterName ) );
    }


    @Override
    public void run()
    {
        HadoopClusterConfig hadoopClusterConfig = manager.getCluster( clusterName );

        if ( hadoopClusterConfig == null )
        {
            productOperation.addLogFailed( String.format( "Installation with name %s does not exist", clusterName ) );
            return;
        }

        if ( hadoopClusterConfig.getJobTracker() == null )
        {
            productOperation.addLogFailed( String.format( "DataNode on %s does not exist", clusterName ) );
            return;
        }

        Agent node = manager.getAgentManager().getAgentByHostname( lxcHostName );
        if ( node == null )
        {
            productOperation.addLogFailed( "DataNode is not connected" );
            return;
        }

        Command removeCommand = manager.getCommands().getRemoveDataNodeCommand( hadoopClusterConfig, node );
        manager.getCommandRunner().runCommand( removeCommand );
        logCommand( removeCommand, productOperation );

        Command includeCommand = manager.getCommands().getIncludeDataNodeCommand( hadoopClusterConfig, node );
        manager.getCommandRunner().runCommand( includeCommand );
        logCommand( includeCommand, productOperation );

        Command refreshCommand = manager.getCommands().getRefreshNameNodeCommand( hadoopClusterConfig );
        manager.getCommandRunner().runCommand( refreshCommand );
        logCommand( refreshCommand, productOperation );

        hadoopClusterConfig.getBlockedAgents().add( node );
        manager.getPluginDAO()
               .saveInfo( HadoopClusterConfig.PRODUCT_KEY, hadoopClusterConfig.getClusterName(), hadoopClusterConfig );
        productOperation.addLogDone( "Cluster info saved to DB" );
    }


    private void logCommand( Command command, ProductOperation po )
    {
        if ( command.hasSucceeded() )
        {
            po.addLog( String.format( "Task's operation %s finished", command.getDescription() ) );
        }
        else if ( command.hasCompleted() )
        {
            po.addLogFailed( String.format( "Task's operation %s failed", command.getDescription() ) );
        }
        else
        {
            po.addLogFailed( String.format( "Task's operation %s timeout", command.getDescription() ) );
        }
    }
}
