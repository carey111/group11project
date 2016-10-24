package com.geminno.empsys.pojo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/25.
 */
public class ChatRecordBean {

    public int status;
    public ArrayList<ChatRecord> chatRecordList;

    public static class ChatRecord{
        public Integer chatRecordId;
        public String userPhotoImg;
        public String userName;
        public String recordContext;

        @Override
        public String toString() {
            return "ChatRecord{" +
                    "chatRecordId=" + chatRecordId +
                    ", userPhotoImg='" + userPhotoImg + '\'' +
                    ", userName='" + userName + '\'' +
                    ", recordContext='" + recordContext + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChatRecordBean{" +
                "status=" + status +
                ", chatRecordList=" + chatRecordList +
                '}';
    }
}
