package com.toplyh.server.model.json.data;

/**
 * Created by 我 on 2017/11/29.
 */
public class AddStory {

    /**
     * projectId : 2
     * story : {"id":null,"description":"加油","status":"BB"}
     */

    private Integer projectId;
    private StoryBean story;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public StoryBean getStory() {
        return story;
    }

    public void setStory(StoryBean story) {
        this.story = story;
    }

    public static class StoryBean {
        /**
         * description : 加油
         * status : BB
         */

        private String description;
        private String status;


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
