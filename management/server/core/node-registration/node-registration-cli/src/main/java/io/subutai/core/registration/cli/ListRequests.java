package io.subutai.core.registration.cli;


import java.util.List;

import org.apache.karaf.shell.commands.Command;

import io.subutai.core.identity.rbac.cli.SubutaiShellCommandSupport;
import io.subutai.core.registration.api.RegistrationManager;
import io.subutai.core.registration.api.service.RequestedHost;


/**
 * Created by talas on 8/25/15.
 */
@Command( scope = "node", name = "list", description = "approve new registration request" )
public class ListRequests extends SubutaiShellCommandSupport
{
    private RegistrationManager registrationManager;


    public ListRequests( final RegistrationManager registrationManager )
    {
        this.registrationManager = registrationManager;
    }


    @Override
    protected Object doExecute() throws Exception
    {
        List<RequestedHost> requestedHosts = registrationManager.getRequests();
        for ( final RequestedHost requestedHost : requestedHosts )
        {
            System.out.println( requestedHost.toString() );
            System.out.println( "==========" );
        }
        return null;
    }
}