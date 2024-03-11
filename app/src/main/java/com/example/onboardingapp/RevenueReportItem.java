package com.example.onboardingapp;

public class RevenueReportItem {
    String title, total;

    public RevenueReportItem(String title, String total) {
        this.title = title;
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
