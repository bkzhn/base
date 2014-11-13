package org.safehaus.subutai.core.network.cli;


import org.safehaus.subutai.core.network.api.NetworkManager;
import org.safehaus.subutai.core.network.api.NetworkManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import com.google.common.base.Preconditions;


@Command( scope = "net", name = "setup-n2n", description = "Sets up N2N connection with Hub" )
public class SetupN2NCommand extends OsgiCommandSupport
{
    private static final Logger LOG = LoggerFactory.getLogger( SetupN2NCommand.class.getName() );

    private final NetworkManager networkManager;

    @Argument( index = 0, name = "super node ip", required = true, multiValued = false,
            description = "super node ip" )
    String superNodeIp;
    @Argument( index = 1, name = "super node port", required = true, multiValued = false,
            description = "super node port" )
    int superNodePort;
    @Argument( index = 2, name = "tap interface", required = true, multiValued = false,
            description = "tap interface" )
    String tapInterfaceName;
    @Argument( index = 3, name = "community name", required = true, multiValued = false,
            description = "community name" )
    String communityName;
    @Argument( index = 4, name = "local peer IP", required = true, multiValued = false,
            description = "local peer IP" )
    String localIp;


    public SetupN2NCommand( final NetworkManager networkManager )
    {
        Preconditions.checkNotNull( networkManager );

        this.networkManager = networkManager;
    }


    @Override
    protected Object doExecute()
    {

        try
        {
            networkManager.setupN2NConnection( superNodeIp, superNodePort, tapInterfaceName, communityName, localIp );
            System.out.println( "OK" );
        }
        catch ( NetworkManagerException e )
        {
            System.out.println( e.getMessage() );
            LOG.error( "Error in SetupN2NCommand", e );
        }

        return null;
    }
}
