package com.toplyh.server.model.json.data;

import java.util.Date;

/**
 * Created by 我 on 2017/12/2.
 */
public class AddMeeting {

    /**
     * projectId : 2
     * meeting : {"date":1512279824307,"name":"开会"}
     */

    private int projectId;
    private MeetingBean meeting;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public MeetingBean getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingBean meeting) {
        this.meeting = meeting;
    }

    public static class MeetingBean {
        /**
         * date : 1512279824307
         * name : 开会
         */

        private Date date;
        private String name;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
