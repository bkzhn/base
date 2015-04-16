package org.safehaus.subutai.core.peer.cli;


import org.safehaus.subutai.common.peer.ContainerHost;
import org.safehaus.subutai.common.peer.PeerException;
import org.safehaus.subutai.core.identity.rbac.cli.SubutaiShellCommandSupport;
import org.safehaus.subutai.core.peer.api.LocalPeer;
import org.safehaus.subutai.core.peer.api.PeerManager;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;


@Command( scope = "peer", name = "stop-container" )
public class StopLxcCommand extends SubutaiShellCommandSupport
{

    private PeerManager peerManager;


    public void setPeerManager( final PeerManager peerManager )
    {
        this.peerManager = peerManager;
    }


    @Argument( index = 0, name = "hostname", multiValued = false, description = "LXC name" )
    private String hostname;


    @Override
    protected Object doExecute() throws Exception
    {

        LocalPeer localPeer = peerManager.getLocalPeer();

        ContainerHost host = localPeer.getContainerHostByName( hostname );

        if ( host == null )
        {
            System.out.println( "Container not found." );
        }

        try
        {
            localPeer.stopContainer( host );
            System.out.println( "Container stopped successfully" );
        }
        catch ( PeerException e )
        {
            System.out.println( "Could not stop container. Error occurred: " + e.toString() );
        }
        return null;
    }
}
