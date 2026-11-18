# 末影矢 · Ender Arrow

<img src="src/main/resources/assets/pearlarrow/textures/item/pearl_arrow.png" width="72" alt="Ender Arrow">

一个 Minecraft **1.20.1 Fabric** 模组：新增一支会传送的箭。命中方块或生物时，射手会被传送到箭的落点，并伴随末影珍珠粒子与末影人传送音效。

**下载**：[Modrinth](https://modrinth.com/mod/ender-arrow) · [GitHub Releases](https://github.com/lyc2013/Ender-Arrow/releases)

## 特性

- 命中方块或生物后，将**射手**传送到落点
- 飞行途中持续拖出末影珍珠（`portal`）粒子，命中时在起点与落点爆出粒子并播放传送音效
- 落点被占用时会自动向上寻找可站立的位置，避免卡进方块
- 传送后清除摔落伤害，并自动脱离坐骑
- 弓与弩均可发射，命中后箭矢消失
- 物品名：末影矢 / Ender Arrow

## 安装

1. 安装 **Minecraft 1.20.1** 与 **Fabric Loader 0.16.10 或更高版本**
2. 将 [Fabric API](https://modrinth.com/mod/fabric-api)（1.20.1 对应的 0.92.x）放入 `mods` 文件夹
3. 将本模组的 `ender-arrow-1.0.0.jar` 放入 `mods` 文件夹
4. 启动游戏

依赖：Fabric Loader ≥ 0.16.10、Fabric API、Java 17

## 合成

无序合成（1 支箭 + 1 个末影珍珠）：

```
[ 箭 ] + [ 末影珍珠 ] → [ 末影矢 ] × 1
```

也可以在创造模式「战斗」物品栏中找到。

## 从源码构建

需要 JDK 17：

```bash
./gradlew build
```

产物位于 `build/libs/ender-arrow-1.0.0.jar`。

## 支持作者

如果这个模组对你有帮助，欢迎来 [爱发电](https://afdian.com/a/lyc2013) 赞助我 ☕

## 许可证

MIT
