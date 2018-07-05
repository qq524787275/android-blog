package com.zhuzichu.module_cartoon.beans;

import java.util.List;

/**
 * 作者: Zzc on 2018-06-28.
 * 版本: v1.0
 */
public class TagsBean {


    private List<TopicsBean> topics;

    public List<TopicsBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsBean> topics) {
        this.topics = topics;
    }

    public static class TopicsBean {
        /**
         * comic_type : 0
         * comics_count : 58
         * cover_image_url : https://i1s.kkmh.com/image/170217/fem4et0qb.webp-w750.jpg
         * created_at : 1462328306
         * description : 甜到腻的短梗漫，恩爱情侣间的逗比日常~只要恩爱秀得好，幸福生活少不了！【授权/不定期更新 责编：珉xi】
         * discover_image_url : null
         * exclusive_flag : 0
         * gender_bias : 1
         * id : 793
         * label_id : 5
         * likes : 4817万
         * male_cover_image : null
         * male_vertical_cover_image : null
         * order : 499
         * quality_certified : 0
         * title : 大圣和小夭
         * update_day : 每周日更新
         * update_status : 1
         * updated_at : 1462328306
         * user : {"avatar_url":"https://i1s.kkmh.com/image/160504/a1neglko9.webp-w180.w","birthday":852048000000,"created_at":1462326135000,"email":null,"flag":0,"gender":null,"grade":1,"id":11824542,"intro":"","nickname":"妖妖小精","phone":null,"privilege":19,"role":"AUTHOR","status":"active"}
         * vertical_image_url : https://i1s.kkmh.com/image/170804/ls89s5u7g.webp-w320.w.jpg
         */

        private int comic_type;
        private int comics_count;
        private String cover_image_url;
        private int created_at;
        private String description;
        private Object discover_image_url;
        private int exclusive_flag;
        private int gender_bias;
        private int id;
        private int label_id;
        private String likes;
        private Object male_cover_image;
        private Object male_vertical_cover_image;
        private int order;
        private int quality_certified;
        private String title;
        private String update_day;
        private int update_status;
        private int updated_at;
        private UserBean user;
        private String vertical_image_url;

        public int getComic_type() {
            return comic_type;
        }

        public void setComic_type(int comic_type) {
            this.comic_type = comic_type;
        }

        public int getComics_count() {
            return comics_count;
        }

        public void setComics_count(int comics_count) {
            this.comics_count = comics_count;
        }

        public String getCover_image_url() {
            return cover_image_url;
        }

        public void setCover_image_url(String cover_image_url) {
            this.cover_image_url = cover_image_url;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getDiscover_image_url() {
            return discover_image_url;
        }

        public void setDiscover_image_url(Object discover_image_url) {
            this.discover_image_url = discover_image_url;
        }

        public int getExclusive_flag() {
            return exclusive_flag;
        }

        public void setExclusive_flag(int exclusive_flag) {
            this.exclusive_flag = exclusive_flag;
        }

        public int getGender_bias() {
            return gender_bias;
        }

        public void setGender_bias(int gender_bias) {
            this.gender_bias = gender_bias;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLabel_id() {
            return label_id;
        }

        public void setLabel_id(int label_id) {
            this.label_id = label_id;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public Object getMale_cover_image() {
            return male_cover_image;
        }

        public void setMale_cover_image(Object male_cover_image) {
            this.male_cover_image = male_cover_image;
        }

        public Object getMale_vertical_cover_image() {
            return male_vertical_cover_image;
        }

        public void setMale_vertical_cover_image(Object male_vertical_cover_image) {
            this.male_vertical_cover_image = male_vertical_cover_image;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getQuality_certified() {
            return quality_certified;
        }

        public void setQuality_certified(int quality_certified) {
            this.quality_certified = quality_certified;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdate_day() {
            return update_day;
        }

        public void setUpdate_day(String update_day) {
            this.update_day = update_day;
        }

        public int getUpdate_status() {
            return update_status;
        }

        public void setUpdate_status(int update_status) {
            this.update_status = update_status;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getVertical_image_url() {
            return vertical_image_url;
        }

        public void setVertical_image_url(String vertical_image_url) {
            this.vertical_image_url = vertical_image_url;
        }

        public static class UserBean {
            /**
             * avatar_url : https://i1s.kkmh.com/image/160504/a1neglko9.webp-w180.w
             * birthday : 852048000000
             * created_at : 1462326135000
             * email : null
             * flag : 0
             * gender : null
             * grade : 1
             * id : 11824542
             * intro :
             * nickname : 妖妖小精
             * phone : null
             * privilege : 19
             * role : AUTHOR
             * status : active
             */

            private String avatar_url;
            private long birthday;
            private long created_at;
            private Object email;
            private int flag;
            private Object gender;
            private int grade;
            private int id;
            private String intro;
            private String nickname;
            private Object phone;
            private int privilege;
            private String role;
            private String status;

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public long getBirthday() {
                return birthday;
            }

            public void setBirthday(long birthday) {
                this.birthday = birthday;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
                this.gender = gender;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public int getPrivilege() {
                return privilege;
            }

            public void setPrivilege(int privilege) {
                this.privilege = privilege;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
