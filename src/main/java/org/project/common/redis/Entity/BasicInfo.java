package org.project.common.redis.Entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

@Component
public class BasicInfo {

    @Value("${app.version}")
    private String version;

    private String springVersion = SpringVersion.getVersion();

    private String springBootVersion = SpringBootVersion.getVersion();

    private String osName;

    private String osVersion;

    private String osArch;

    private String processorArchitecture;

    private String numberOfProcessors;

    private String javaHome;

    private String javaVersion;

    private String javaVendor;

    private String javaVendorURL;

    private String runtimeName;

    private String vmName;

    private String vmInfo;

    private String[] classpath;

    private BasicInfo() {
        Map<String, String> map = System.getenv();
        Properties properties = System.getProperties();
        this.osName = properties.getProperty("os.name");
        this.osVersion = properties.getProperty("os.version");
        this.osArch = properties.getProperty("os.arch");
        this.processorArchitecture = map.get("PROCESSOR_ARCHITECTURE");
        this.numberOfProcessors = map.get("NUMBER_OF_PROCESSORS");
        this.javaHome = map.get("JAVA_HOME");
        this.javaVersion = properties.getProperty("java.version");
        this.javaVendor = properties.getProperty("java.vendor");
        this.javaVendorURL = properties.getProperty("java.vendor.url");
        this.runtimeName = properties.getProperty("java.runtime.name");
        this.vmName = properties.getProperty("java.vm.name");
        this.vmInfo = properties.getProperty("java.vm.info");
        this.classpath = properties.getProperty("java.class.path").split(";");
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSpringVersion() {
        return springVersion;
    }

    public void setSpringVersion(String springVersion) {
        this.springVersion = springVersion;
    }

    public String getSpringBootVersion() {
        return springBootVersion;
    }

    public void setSpringBootVersion(String springBootVersion) {
        this.springBootVersion = springBootVersion;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getProcessorArchitecture() {
        return processorArchitecture;
    }

    public void setProcessorArchitecture(String processorArchitecture) {
        this.processorArchitecture = processorArchitecture;
    }

    public String getNumberOfProcessors() {
        return numberOfProcessors;
    }

    public void setNumberOfProcessors(String numberOfProcessors) {
        this.numberOfProcessors = numberOfProcessors;
    }

    public String getJavaHome() {
        return javaHome;
    }

    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getJavaVendor() {
        return javaVendor;
    }

    public void setJavaVendor(String javaVendor) {
        this.javaVendor = javaVendor;
    }

    public String getJavaVendorURL() {
        return javaVendorURL;
    }

    public void setJavaVendorURL(String javaVendorURL) {
        this.javaVendorURL = javaVendorURL;
    }

    public String getRuntimeName() {
        return runtimeName;
    }

    public void setRuntimeName(String runtimeName) {
        this.runtimeName = runtimeName;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getVmInfo() {
        return vmInfo;
    }

    public void setVmInfo(String vmInfo) {
        this.vmInfo = vmInfo;
    }

    public String[] getClasspath() {
        return classpath;
    }

    public void setClasspath(String[] classpath) {
        this.classpath = classpath;
    }

}
