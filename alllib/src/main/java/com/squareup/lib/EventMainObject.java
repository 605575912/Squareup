package com.squareup.lib;

import com.squareup.lib.utils.IProguard;

/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class EventMainObject<T> implements IProguard.ProtectClassAndMembers {
    private T data;
    private String command;
    private boolean success;

    private EventMainObject() {
    }

    public EventMainObject(T data) {
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getCommand() {
        if (command == null) {
            command = "";
        }
        return command;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
