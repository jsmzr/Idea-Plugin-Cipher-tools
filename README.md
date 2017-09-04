# Idea-Plugin-Cipher-tools
A simple encryption and decryption, encoding and decoding tool plugin. Support AES, DES, 3DES and so on

这是一个简单的加密解密、编码解码工具插件，可以安装到idea、android studio，只是做了简单的封装。

- MD5/SHA-1/SHA-256
- AES/RSA/DES/3DES
- base64
- HmacMD5/HmacSha1/HmacSha1

![renderings](/resources/img/Renderings.png)

#### 开发流程

1. 将源码clone到本地
2. 使用idea打开，并将环境jre切换为plugin dev
3. 通常由于idea不会智能切换module classpath,需要手动在idea的workspace.xml中名为RunManager的component标签中新增如下配置
4. 完成基础配置后运行即可在新的idea窗口中看到该插件
5. 可以打成jar包导入plugin中

```xml
<configuration name="Plugin" type="#org.jetbrains.idea.devkit.run.PluginConfigurationType" factoryName="Plugin">
      <module name="Idea-Plugin-Cipher-Tools" />
      <option name="VM_PARAMETERS" value="-Xmx512m -Xms256m -XX:MaxPermSize=250m -ea" />
      <option name="PROGRAM_PARAMETERS" />
      <predefined_log_file id="idea.log" enabled="true" />
      <method>
        <option name="Make" enabled="false" />
      </method>
    </configuration>
```
*.iml在项目配置文件中修改项目类型为PLUGIN_MODULE，如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<module type="PLUGIN_MODULE" version="4">
  <component name="DevKit.ModuleBuildProperties" url="file://$MODULE_DIR$/resources/META-INF/plugin.xml" />
  <component name="NewModuleRootManager" LANGUAGE_LEVEL="JDK_1_8" inherit-compiler-output="true">
    <exclude-output />
    <content url="file://$MODULE_DIR$">
      <sourceFolder url="file://$MODULE_DIR$/src" isTestSource="false" />
      <sourceFolder url="file://$MODULE_DIR$/resources" type="java-resource" />
      <excludePattern pattern="*.png" />
    </content>
    <orderEntry type="jdk" jdkName="IntelliJ IDEA Community Edition IC-172.2827.15" jdkType="IDEA JDK" />
    <orderEntry type="inheritedJdk" />
    <orderEntry type="sourceFolder" forTests="false" />
  </component>
</module>
```
#### 注意事项
在开发中发现很多次插件效果与代码很不匹配的情况，可能是idea的缓存问题，如果无法去除缓存可以尝试清理相关文件
