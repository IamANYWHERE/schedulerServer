package com.toplyh.server.model.json.data;

import java.util.Date;

/**
 * Created by 我 on 2017/12/3.
 */
public class AddSprint {

    /**
     * projectId : 2
     * memberId : 1
     * sprint : {"id":null,"name":"吃东西","content":"ssssss","ddl":1512289865171,"status":"BO","workTime":3}
     */

    private int projectId;
    private int memberId;
    private SprintBean sprint;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public SprintBean getSprint() {
        return sprint;
    }

    public void setSprint(SprintBean sprint) {
        this.sprint = sprint;
    }

    public static class SprintBean {
        /**
         * id : null
         * name : 吃东西
         * content : ssssss
         * ddl : 1512289865171
         * status : BO
         * workTime : 3
         */

        private String name;
        private String content;
        private Date ddl;
        private String status;
        private int workTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getDdl() {
            return ddl;
        }

        public void setDdl(Date ddl) {
            this.ddl = ddl;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getWorkTime() {
            return workTime;
        }

        public void setWorkTime(int workTime) {
            this.workTime = workTime;
        }
    }
}
