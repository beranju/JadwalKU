package com.nextgen.jadwalku;

public class Jadwal {
    String juduljadwal;
    String deskjadwal;
    String harijadwal;
    //3video 3
    String keyjadwal;
    String userid;



    String jamjadwal;

    public Jadwal(String juduljadwal, String deskjadwal, String harijadwal, String keyjadwal, String userid, String jamjadwal) {
        this.juduljadwal = juduljadwal;
        this.deskjadwal = deskjadwal;
        this.harijadwal = harijadwal;
        this.keyjadwal = keyjadwal;
        this.userid = userid;
        this.jamjadwal = jamjadwal;
    }

    public Jadwal() {
    }

    public String getJamjadwal() {
        return jamjadwal;
    }

    public void setJamjadwal(String jamjadwal) {
        this.jamjadwal = jamjadwal;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getKeyjadwal() {
        return keyjadwal;
    }

    public void setKeyjadwal(String keyjadwal) {
        this.keyjadwal = keyjadwal;
    }

    public String getJuduljadwal() {
        return juduljadwal;
    }

    public void setJuduljadwal(String juduljadwal) {
        this.juduljadwal = juduljadwal;
    }

    public String getDeskjadwal() {
        return deskjadwal;
    }

    public void setDeskjadwal(String deskjadwal) {
        this.deskjadwal = deskjadwal;
    }

    public String getHarijadwal() {
        return harijadwal;
    }

    public void setHarijadwal(String waktujadwal) {
        this.harijadwal = waktujadwal;
    }


}
