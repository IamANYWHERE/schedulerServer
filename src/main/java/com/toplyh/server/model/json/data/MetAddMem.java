package com.toplyh.server.model.json.data;

import java.util.List;

/**
 * Created by 我 on 2017/12/3.
 */
public class MetAddMem {

    /**
     * meetingId : 2
     * members : [{"memberId":2},{"memberId":3}]
     */

    private int meetingId;
    private List<MembersBean> members;

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public List<MembersBean> getMembers() {
        return members;
    }

    public void setMembers(List<MembersBean> members) {
        this.members = members;
    }

    public static class MembersBean {
        /**
         * memberId : 2
         */

        private int memberId;

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }
    }
}
