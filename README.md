<div align="center">
    <h1>
        Prism
    </h1>
    <a href="https://github.com/Dengzixu/prism">
        <img alt="Project" src="https://img.shields.io/badge/project-prism-green?style=for-the-badge&logo=github">
    </a>
    <img alt="Love" src="https://img.shields.io/badge/code%20with-love%E2%99%A5%EF%B8%8F-CC0066?style=for-the-badge">
    <img alt="GitHub" src="https://img.shields.io/github/license/Dengzixu/prism?style=for-the-badge">
    <p>一个用于抓取 BiliBili 直播弹幕的小工具</p>
</div>

## 如何构建 / How to build

> 本项目最低 JDK 版本为 **17**

### 1. clone 本项目并进入项目目录

```shell
git@github.com:Dengzixu/prism.git
cd ./prism/
```

### 2. 初始化子模块

```shell
git submodule update --init --recursive
```

### 3. 构建

- 手动构建
  ```shell
  ./gradlew build
  ```