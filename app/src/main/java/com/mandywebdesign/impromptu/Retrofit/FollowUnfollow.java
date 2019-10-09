package com.mandywebdesign.impromptu.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FollowUnfollow {

@SerializedName("status")
@Expose
private String status;
@SerializedName("data")
@Expose
private List<Object> data = null;
@SerializedName("message")
@Expose
private String message;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public List<Object> getData() {
return data;
}

public void setData(List<Object> data) {
this.data = data;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}