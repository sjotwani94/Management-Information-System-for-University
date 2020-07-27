package com.example.myapplication;

public class NoticeListData {
    private String noticeSender;
    private String noticeSubject;
    private byte[] noticeImage;
    private String noticeDescription;

    public String getNoticeSender() {
        return noticeSender;
    }

    public void setNoticeSender(String noticeSender) {
        this.noticeSender = noticeSender;
    }

    public String getNoticeSubject() {
        return noticeSubject;
    }

    public void setNoticeSubject(String noticeSubject) {
        this.noticeSubject = noticeSubject;
    }

    public byte[] getNoticeImage() {
        return noticeImage;
    }

    public void setNoticeImage(byte[] noticeImage) {
        this.noticeImage = noticeImage;
    }

    public String getNoticeDescription() {
        return noticeDescription;
    }

    public void setNoticeDescription(String noticeDescription) {
        this.noticeDescription = noticeDescription;
    }

    public NoticeListData(String noticeSender, String noticeSubject, byte[] noticeImage, String noticeDescription) {
        this.noticeSender = noticeSender;
        this.noticeSubject = noticeSubject;
        this.noticeImage = noticeImage;
        this.noticeDescription = noticeDescription;
    }
}
