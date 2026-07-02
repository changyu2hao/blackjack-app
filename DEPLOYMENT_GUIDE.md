# Railway 部署指南

## 第一步：本地准备（已完成）
✅ 项目已成功编译
✅ JAR 包已生成：`target/blackjack-app-0.0.1-SNAPSHOT.jar`

## 第二步：初始化 Git 仓库

```bash
cd C:\Users\huang\IdeaProjects\blackjack-app
git init
git add .
git commit -m "Initial commit: Blackjack app"
```

## 第三步：推送到 GitHub

1. 登录 [GitHub](https://github.com)
2. 创建新仓库 `blackjack-app`（不勾选任何初始文件）
3. 运行以下命令：

```bash
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/blackjack-app.git
git push -u origin main
```

## 第四步：部署到 Railway

1. 访问 [railway.app](https://railway.app)
2. 点击 **"New Project"** → **"Deploy from GitHub Repo"**
3. 授权 GitHub 并选择 `blackjack-app` 仓库
4. 选择部署配置后，添加以下变量：

### 环境变量配置

在 Railway 仪表板中设置：

| 变量名 | 值 |
|--------|-----|
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | `your-secure-password` |
| `RAILWAY_PUBLIC_DOMAIN` | 自动生成 |

### 数据库配置

1. 在 Railway 仪表板中点击 **"Add"** → **"MySQL"**
2. 选择刚创建的 MySQL 实例
3. 获取连接参数，更新环境变量

## 第五步：验证部署

部署完成后：
- 访问 `https://your-app-name.railway.app`
- 检查应用日志

## 常见问题

**Q：需要修改数据库配置吗？**
A：建议在 `application-prod.properties` 中添加生产配置

**Q：如何监控日志？**
A：在 Railway 仪表板的 Logs 标签中查看

---

**成本估算**
- 免费额度：$5/月
- MySQL 数据库：包含在免费额度内
