package org.safehaus.kiskis.mgmt.impl.hive.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.kiskis.mgmt.api.hive.Config;
import org.safehaus.kiskis.mgmt.impl.hive.mock.HiveImplMock;
import org.safehaus.kiskis.mgmt.product.common.test.unit.mock.CommonMockBuilder;
import org.safehaus.kiskis.mgmt.shared.operation.ProductOperation;
import org.safehaus.kiskis.mgmt.shared.operation.ProductOperationState;

public class InstallHandlerTest {

    private HiveImplMock mock = new HiveImplMock();
    private AbstractHandler handler;

    @Before
    public void setUp() {
        mock = new HiveImplMock();
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullConfig() {
        handler = new InstallHandler(mock, null);
        handler.run();
    }

    @Test
    public void testWithExistingConfig() {
        Config config = new Config();
        config.setClusterName("test-cluster");
        mock.setConfig(config);

        handler = new InstallHandler(mock, config);
        handler.run();

        ProductOperation po = handler.getProductOperation();
        Assert.assertTrue(po.getLog().toLowerCase().contains("exists"));
        Assert.assertTrue(po.getLog().contains(config.getClusterName()));
        Assert.assertEquals(po.getState(), ProductOperationState.FAILED);
    }

    @Test
    public void testWithoutServerNode() {
        Config config = new Config();
        config.setClusterName("test-cluster");
        config.setServer(CommonMockBuilder.createAgent());

        handler = new InstallHandler(mock, config);
        handler.run();

        ProductOperation po = handler.getProductOperation();
        Assert.assertTrue(po.getLog().toLowerCase().contains("not connected"));
        Assert.assertTrue(po.getLog().contains(config.getServer().getHostname()));
        Assert.assertEquals(po.getState(), ProductOperationState.FAILED);
    }

}
