package com.toplyh.server.model.json.data;

/**
 * Created by 我 on 2017/11/29.
 */
public class AddMember {

    /**
     * projectId : 2
     * member : {"memName":"tim","contribution":3}
     */

    private Integer projectId;
    private MemberBean member;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public static class MemberBean {
        /**
         * memName : tim
         * contribution : 3
         */

        private String memName;
        private Integer contribution;

        public String getMemName() {
            return memName;
        }

        public void setMemName(String memName) {
            this.memName = memName;
        }

        public Integer getContribution() {
            return contribution;
        }

        public void setContribution(int contribution) {
            this.contribution = contribution;
        }
    }
}
