package io.subutai.core.lxc.quota.cli;


import io.subutai.common.peer.ContainerHost;
import io.subutai.common.quota.DiskPartition;
import io.subutai.common.quota.DiskQuota;
import io.subutai.common.quota.DiskQuotaUnit;
import io.subutai.common.quota.QuotaType;
import io.subutai.core.identity.rbac.cli.SubutaiShellCommandSupport;
import io.subutai.core.lxc.quota.api.QuotaManager;
import io.subutai.core.peer.api.PeerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;


@Command( scope = "quota", name = "set-quota", description = "Sets specified quota to container" )
public class SetQuota extends SubutaiShellCommandSupport
{
    private static final Logger LOGGER = LoggerFactory.getLogger( SetQuota.class );
    private QuotaManager quotaManager;
    private PeerManager peerManager;

    @Argument( index = 0, name = "container name", required = true, multiValued = false, description = "specify "
            + "container name" )
    private String containerName;

    @Argument( index = 1, name = "quota type", required = true, multiValued = false, description = "specify quota "
            + "type" )
    private String quotaType;

    @Argument( index = 2, name = "quota value", required = true, multiValued = false, description = "set quota value" )
    private String quotaValue;


    public SetQuota( QuotaManager quotaManager, final PeerManager peerManager )
    {
        this.quotaManager = quotaManager;
        this.peerManager = peerManager;
    }


    public void setContainerName( final String containerName )
    {
        this.containerName = containerName;
    }


    public void setQuotaType( final String quotaType )
    {
        this.quotaType = quotaType;
    }


    public void setQuotaValue( final String quotaValue )
    {
        this.quotaValue = quotaValue;
    }


    @Override
    protected Object doExecute() throws Exception
    {
        QuotaType quota = QuotaType.getQuotaType( quotaType );
        ContainerHost targetContainer = peerManager.getLocalPeer().getContainerHostByName( containerName );
        switch ( quota )
        {
            case QUOTA_TYPE_RAM:
                quotaManager.setRamQuota( targetContainer.getId(), Integer.valueOf( quotaValue ) );
                break;
            case QUOTA_TYPE_DISK_ROOTFS:
                quotaManager.setDiskQuota( targetContainer.getId(),
                        new DiskQuota( DiskPartition.ROOT_FS, DiskQuotaUnit.MB, Double.valueOf( quotaValue ) ) );
                break;
            case QUOTA_TYPE_DISK_HOME:
                quotaManager.setDiskQuota( targetContainer.getId(),
                        new DiskQuota( DiskPartition.HOME, DiskQuotaUnit.MB, Double.valueOf( quotaValue ) ) );
                break;
            case QUOTA_TYPE_DISK_OPT:
                quotaManager.setDiskQuota( targetContainer.getId(),
                        new DiskQuota( DiskPartition.OPT, DiskQuotaUnit.MB, Double.valueOf( quotaValue ) ) );
                break;
            case QUOTA_TYPE_DISK_VAR:
                quotaManager.setDiskQuota( targetContainer.getId(),
                        new DiskQuota( DiskPartition.VAR, DiskQuotaUnit.MB, Double.valueOf( quotaValue ) ) );
                break;
            case QUOTA_TYPE_CPU:
                quotaManager.setCpuQuota( targetContainer.getId(), Integer.valueOf( quotaValue ) );
                break;
        }
        return null;
    }
}