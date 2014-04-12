package org.safehaus.kiskis.mgmt.impl.hadoop.operation;

import org.safehaus.kiskis.mgmt.api.hadoop.Config;
import org.safehaus.kiskis.mgmt.api.taskrunner.Task;
import org.safehaus.kiskis.mgmt.api.taskrunner.TaskCallback;
import org.safehaus.kiskis.mgmt.api.taskrunner.TaskStatus;
import org.safehaus.kiskis.mgmt.impl.hadoop.HadoopImpl;
import org.safehaus.kiskis.mgmt.impl.hadoop.Tasks;
import org.safehaus.kiskis.mgmt.shared.protocol.Agent;
import org.safehaus.kiskis.mgmt.shared.protocol.Response;

import java.util.regex.Pattern;

/**
 * Created by daralbaev on 12.04.14.
 */
public class NameNodeConfiguration {
    private HadoopImpl parent;
    private Config config;

    public NameNodeConfiguration(HadoopImpl parent, Config config) {
        this.parent = parent;
        this.config = config;
    }

    public boolean startNameNode() {
        Task task = Tasks.getNameNodeCommandTask(config.getNameNode(), "start");
        parent.getTaskRunner().executeTask(task);

        return task.getTaskStatus() == TaskStatus.SUCCESS;
    }

    public boolean stopNameNode() {
        Task task = Tasks.getNameNodeCommandTask(config.getNameNode(), "stop");
        parent.getTaskRunner().executeTask(task);

        return task.getTaskStatus() == TaskStatus.SUCCESS;
    }

    public boolean restartNameNode() {
        Task task = Tasks.getNameNodeCommandTask(config.getNameNode(), "restart");
        parent.getTaskRunner().executeTask(task);

        return task.getTaskStatus() == TaskStatus.SUCCESS;
    }

    public boolean statusNameNode() {
        Task task = Tasks.getNameNodeCommandTask(config.getNameNode(), "status");
        final String[] gStatus = new String[1];

        parent.getTaskRunner().executeTask(task, new TaskCallback() {
            @Override
            public Task onResponse(Task task, Response response, String stdOut, String stdErr) {
                if (task.isCompleted()) {
                    synchronized (task) {
                        String[] array = response.getStdOut().split("\n");
                        System.out.println(response.getStdOut());

                        for (String status : array) {
                            if (status.contains("NameNode")) {
                                gStatus[0] = status.
                                        replaceAll(Pattern.quote("!(SecondaryNameNode is not running on this machine)"), "").
                                        replaceAll("NameNode is ", "");
                            }
                        }

                        task.notifyAll();
                    }
                }

                return null;
            }
        });

        synchronized (task) {
            try {
                task.wait(task.getAvgTimeout() * 1000 + 1000);
            } catch (InterruptedException ex) {
                return false;
            }
        }

        System.out.println(gStatus[0]);
        return !gStatus[0].toLowerCase().contains("not");
    }

    public boolean statusSecondaryNameNode() {
        Task task = Tasks.getNameNodeCommandTask(config.getSecondaryNameNode(), "status");
        final String[] gStatus = new String[1];

        parent.getTaskRunner().executeTaskNWait(task, new TaskCallback() {
            @Override
            public Task onResponse(Task task, Response response, String stdOut, String stdErr) {
                if (task.getTaskStatus() == TaskStatus.SUCCESS) {
                    String[] array = response.getStdOut().split("\n");

                    for (String status : array) {
                        if (status.contains("SecondaryNameNode")) {
                            gStatus[0] = status.
                                    replaceAll("SecondaryNameNode is ", "");
                        }
                    }
                }

                return null;
            }
        });

        return !gStatus[0].toLowerCase().contains("not");
    }

    public boolean statusDataNode(Agent agent) {
        Task task = Tasks.getNameNodeCommandTask(agent, "status");
        final String[] gStatus = new String[1];

        parent.getTaskRunner().executeTaskNWait(task, new TaskCallback() {
            @Override
            public Task onResponse(Task task, Response response, String stdOut, String stdErr) {
                if (task.getTaskStatus() == TaskStatus.SUCCESS) {
                    String[] array = response.getStdOut().split("\n");

                    for (String status : array) {
                        if (status.contains("DataNode")) {
                            gStatus[0] = status.
                                    replaceAll("DataNode is ", "");
                        }
                    }
                }

                return null;
            }
        });

        return !gStatus[0].toLowerCase().contains("not");
    }

    public boolean startJobTracker() {
        Task task = Tasks.getJobTrackerCommand(config.getJobTracker(), "start");
        parent.getTaskRunner().executeTask(task);

        return task.getTaskStatus() == TaskStatus.SUCCESS;
    }

    public boolean stopJobTracker() {
        Task task = Tasks.getJobTrackerCommand(config.getJobTracker(), "stop");
        parent.getTaskRunner().executeTask(task);

        return task.getTaskStatus() == TaskStatus.SUCCESS;
    }

    public boolean restartJobTracker() {
        Task task = Tasks.getJobTrackerCommand(config.getJobTracker(), "restart");
        parent.getTaskRunner().executeTask(task);

        return task.getTaskStatus() == TaskStatus.SUCCESS;
    }

    public boolean statusJobTracker() {
        Task task = Tasks.getJobTrackerCommand(config.getJobTracker(), "status");
        final String[] gStatus = new String[1];

        parent.getTaskRunner().executeTaskNWait(task, new TaskCallback() {
            @Override
            public Task onResponse(Task task, Response response, String stdOut, String stdErr) {
                if (task.getTaskStatus() == TaskStatus.SUCCESS) {
                    String[] array = response.getStdOut().split("\n");

                    for (String status : array) {
                        if (status.contains("JobTracker")) {
                            gStatus[0] = status.
                                    replaceAll("JobTracker is ", "");
                        }
                    }
                }

                return null;
            }
        });

        return !gStatus[0].toLowerCase().contains("not");
    }

    public boolean statusTaskTracker(Agent agent) {
        Task task = Tasks.getJobTrackerCommand(agent, "status");
        final String[] gStatus = new String[1];

        parent.getTaskRunner().executeTaskNWait(task, new TaskCallback() {
            @Override
            public Task onResponse(Task task, Response response, String stdOut, String stdErr) {
                if (task.getTaskStatus() == TaskStatus.SUCCESS) {
                    String[] array = response.getStdOut().split("\n");

                    for (String status : array) {
                        if (status.contains("TaskTracker")) {
                            gStatus[0] = status.
                                    replaceAll("TaskTracker is ", "");
                        }
                    }
                }

                return null;
            }
        });

        return !gStatus[0].toLowerCase().contains("not");
    }
}
