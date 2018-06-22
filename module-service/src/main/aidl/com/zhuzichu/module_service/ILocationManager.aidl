// ILocationManager.aidl
package com.zhuzichu.module_service;

// Declare any non-default types here with import statements

interface ILocationManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void startLocation();
    void stopLocation();
    boolean isUse();
}
