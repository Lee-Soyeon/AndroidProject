package lecture.mobile.final_project.ma01_20151026;

import java.io.Serializable;

public class MyLocation implements Serializable {

    private int _id;
    private String title;
    private String address;
    private String memo;
    private String photo;

    public MyLocation(int _id, String title, String address, String memo, String photo) {
        this._id = _id;
        this.title = title;
        this.address = address;
        this.memo = memo;
        this.photo = photo;
    }

    public int get_id() {   return _id; }
    public void set_id(int _id) {   this._id = _id; }
    public String getTitle() {  return title;   }
    public void setTitle(String title) {    this.title = title; }
    public String getAddress() {  return address;   }
    public void setAddress(String address) {    this.address = address; }
    public String getMemo() {  return memo;   }
    public void setMemo(String memo) {    this.memo = memo; }
    public String getPhoto() {  return photo;   }
    public void setPhoto(String photo) {    this.photo = photo; }

    @Override
    public String toString() {
        return "* " + title + " ( " + address + " ) : " + memo;
    }
}
