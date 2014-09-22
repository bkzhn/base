package org.safehaus.subutai.common.protocol;


import java.util.UUID;


/**
 * Created by timur on 9/20/14.
 */
public class PeerCommandMessage
{
    private UUID agentId;
    private UUID peerId;


    public PeerCommandMessage()
    {

    }


    public PeerCommandMessage( UUID peerId, UUID agentId )
    {
        this.peerId = peerId;
        this.agentId = agentId;
    }


    public UUID getAgentId()
    {
        return agentId;
    }


    public void setAgentId( final UUID agentId )
    {
        this.agentId = agentId;
    }


    public UUID getPeerId()
    {
        return peerId;
    }


    public void setPeerId( final UUID peerId )
    {
        this.peerId = peerId;
    }
}
