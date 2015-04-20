package org.safehaus.subutai.core.peer.impl.entity;


import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class PeerDataTest
{
    private static final String ID = "id";
    private static final String SOURCE = "source";
    private static final String INFO = "info";

    PeerData peerData = new PeerData();


    @Test
    public void testSetNGetId() throws Exception
    {
        peerData.setId( ID );

        assertEquals( ID, peerData.getId() );
    }


    @Test
    public void testSetNGetSource() throws Exception
    {
        peerData.setSource( SOURCE );

        assertEquals( SOURCE, peerData.getSource() );
    }


    @Test
    public void testSetNGetInfo() throws Exception
    {
        peerData.setInfo( INFO );

        assertEquals( INFO, peerData.getInfo() );
    }
}
