package org.project.common.redis.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

@Component
@ApiModel(value = "项目基本信息实体类", description = "项目运行的基本信息实体类")
public class BasicInfo {

    @ApiModelProperty(value = "Redis Monitor版本(暂时不管)", dataType = "String", example = "1.0.0")
    @Value("${app.version}")
    private String version;

    @ApiModelProperty(value = "Spring 版本号", dataType = "String", example = "5.4.2.RELEASE")
    private String springVersion = SpringVersion.getVersion();

    @ApiModelProperty(value = "Spring Boot 版本号", dataType = "String", example = "2.3.4.RELEASE")
    private String springBootVersion = SpringBootVersion.getVersion();

    @ApiModelProperty(value = "操作系统名称", dataType = "String", example = "Windows10.0")
    private String osName;

    @ApiModelProperty(value = "操作系统版本", dataType = "String", example = "10.0")
    private String osVersion;

    @ApiModelProperty(value = "操作系统架构", dataType = "String", example = "amd64")
    private String osArch;

    @ApiModelProperty(value = "处理器架构", dataType = "String", example = "AMD64")
    private String processorArchitecture;

    @ApiModelProperty(value = "处理器内核数", dataType = "String", example = "8")
    private String numberOfProcessors;

    @ApiModelProperty(value = "Java Home", dataType = "String", example = "C:/")
    private String javaHome;

    @ApiModelProperty(value = "Java 版本", dataType = "String", example = "1.8.0")
    private String javaVersion;

    @ApiModelProperty(value = "Java 供应商", dataType = "String", example = "Oracle")
    private String javaVendor;

    @ApiModelProperty(value = "Java 供应商网址", dataType = "String", example = "https://wwww.oracle.com")
    private String javaVendorURL;

    @ApiModelProperty(value = "Java 运行时环境", dataType = "String", example = "")
    private String runtimeName;

    @ApiModelProperty(value = "Java 虚拟机名称", dataType = "String", example = "")
    private String vmName;

    @ApiModelProperty(value = "Java 虚拟机信息", dataType = "String", example = "")
    private String vmInfo;

    @ApiModelProperty(value = "ClassPath", dataType = "String[]", example = "[]")
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
