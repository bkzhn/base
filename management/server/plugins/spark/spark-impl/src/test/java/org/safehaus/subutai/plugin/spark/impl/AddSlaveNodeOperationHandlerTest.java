package org.safehaus.subutai.plugin.spark.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.subutai.common.protocol.AbstractOperationHandler;
import org.safehaus.subutai.common.protocol.Agent;
import org.safehaus.subutai.common.tracker.ProductOperation;
import org.safehaus.subutai.common.tracker.ProductOperationState;
import org.safehaus.subutai.plugin.spark.api.SparkClusterConfig;
import org.safehaus.subutai.plugin.spark.impl.handler.AddSlaveNodeOperationHandler;
import org.safehaus.subutai.plugin.common.mock.CommonMockBuilder;

import org.safehaus.subutai.plugin.spark.impl.mock.SparkImplMock;
import java.util.Arrays;
import java.util.HashSet;

public class AddSlaveNodeOperationHandlerTest {

    private SparkImplMock mock;
    private AbstractOperationHandler handler;

    @Before
    public void setUp() {
        mock = new SparkImplMock();
        handler = new AddSlaveNodeOperationHandler(mock, "test-cluster", "test-host");
    }

    @Test
    public void testWithoutCluster() {
        handler.run();

        ProductOperation po = handler.getProductOperation();
        Assert.assertTrue( po.getLog().toLowerCase().contains( "not exist" ) );
        Assert.assertEquals(po.getState(), ProductOperationState.FAILED);
    }

    @Test
    public void testWithUnconnectedAgents() {
        SparkClusterConfig config = new SparkClusterConfig();
        config.setClusterName( "test-cluster" );
        config.setMasterNode( CommonMockBuilder.createAgent() );
        config.setSlaveNodes( new HashSet< Agent >( Arrays.asList( CommonMockBuilder.createAgent() ) ) );
        mock.setClusterConfig( config );

        handler.run();

        ProductOperation po = handler.getProductOperation();
        Assert.assertTrue(po.getLog().toLowerCase().contains("not connected"));
        Assert.assertEquals(po.getState(), ProductOperationState.FAILED);
    }
}
