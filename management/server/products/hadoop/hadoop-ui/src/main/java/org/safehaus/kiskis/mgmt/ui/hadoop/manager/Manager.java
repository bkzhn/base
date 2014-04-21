package org.safehaus.kiskis.mgmt.ui.hadoop.manager;

import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import org.safehaus.kiskis.mgmt.ui.hadoop.manager.components.ClusterNode;

/**
 * Created by daralbaev on 12.04.14.
 */
public class Manager extends Panel {
    private HorizontalLayout horizontalLayout, buttonsLayout;
    private Label indicator;
    private Button refreshButton, destroyButton, addButton, includeButton, excludeButton;
    private HadoopTable table;

    public Manager() {
        setSizeFull();

        horizontalLayout = new HorizontalLayout();
        horizontalLayout.setMargin(true);
        horizontalLayout.setSpacing(true);

        horizontalLayout.addComponent(getIndicator());
        horizontalLayout.setComponentAlignment(indicator, Alignment.MIDDLE_LEFT);
        horizontalLayout.addComponent(getButtonRefresh());
        horizontalLayout.setComponentAlignment(refreshButton, Alignment.MIDDLE_LEFT);

        buttonsLayout = new HorizontalLayout();
        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);

        Embedded startedButton = new Embedded("Started node", new ThemeResource("icons/buttons/start.png"));
        startedButton.setWidth(ClusterNode.ICON_SIZE, UNITS_PIXELS);
        startedButton.setHeight(ClusterNode.ICON_SIZE, UNITS_PIXELS);
        startedButton.setEnabled(false);
        buttonsLayout.addComponent(startedButton);

        Embedded stoppedButton = new Embedded("Started node", new ThemeResource("icons/buttons/stop.png"));
        stoppedButton.setWidth(ClusterNode.ICON_SIZE, UNITS_PIXELS);
        stoppedButton.setHeight(ClusterNode.ICON_SIZE, UNITS_PIXELS);
        stoppedButton.setEnabled(false);
        buttonsLayout.addComponent(stoppedButton);

        addComponent(horizontalLayout);
        addComponent(getHadoopTable());
        addComponent(buttonsLayout);
    }

    private Button getButtonRefresh() {
        refreshButton = new Button("Refresh");
        refreshButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                table.refreshDataSource();
            }
        });

        return refreshButton;
    }

    private Label getIndicator() {
        indicator = new Label("Label");
        indicator.setIcon(new ThemeResource("icons/indicator.gif"));
        indicator.setContentMode(Label.CONTENT_XHTML);
        indicator.setHeight(11, Sizeable.UNITS_PIXELS);
        indicator.setWidth(50, Sizeable.UNITS_PIXELS);
        indicator.setVisible(true);

        return indicator;
    }

    private HadoopTable getHadoopTable() {
        if (table == null) {
            table = new HadoopTable("Hadoop Clusters", indicator);
        }

        return table;
    }
}
