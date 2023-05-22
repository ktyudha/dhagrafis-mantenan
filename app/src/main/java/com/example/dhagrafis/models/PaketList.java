package com.example.dhagrafis.models;

public class PaketList {
    private final Integer packetMediaIcon;
    private final String packetMediaTitle;
    private final String packetMediaDesc;
    private final String packetMediaPrice;
    private final String packetMediaPaket;

    public PaketList(Integer packetMediaIcon, String packetMediaTitle, String packetMediaDesc, String packetMediaPrice, String packetMediaPaket) {
        this.packetMediaIcon = packetMediaIcon;
        this.packetMediaTitle = packetMediaTitle;
        this.packetMediaDesc = packetMediaDesc;
        this.packetMediaPaket = packetMediaPaket;
        this.packetMediaPrice = packetMediaPrice;
    }

    public Integer getPacketMediaIcon() {
        return packetMediaIcon;
    }

    public String getPacketMediaTitle() {
        return packetMediaTitle;
    }

    public String getPacketMediaDesc() {
        return packetMediaDesc;
    }

    public String getPacketMediaPrice() {
        return packetMediaPrice;
    }

    public String getPacketMediaPaket() {
        return packetMediaPaket;
    }

}
