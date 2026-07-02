# Blackjack App - Railway 部署完整指南

## 📋 项目概述
- **类型**: Spring Boot Web 应用
- **数据库**: MySQL
- **部署平台**: Railway (免费)
- **编译状态**: ✅ 成功

## 🚀 快速部署步骤

### 1️⃣ 在 GitHub 上创建仓库

访问 https://github.com/new 创建新仓库：
- **Repository name**: `blackjack-app`
- 不要初始化任何文件

### 2️⃣ 推送本地代码到 GitHub

在项目目录运行：
```bash
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/blackjack-app.git
git push -u origin main
```

*替换 `YOUR_USERNAME` 为你的 GitHub 用户名*

### 3️⃣ 在 Railway 上创建项目

1. 访问 https://railway.app
2. 点击 **"New Project"**
3. 选择 **"Deploy from GitHub Repo"**
4. 授权 GitHub 账户
5. 选择 `blackjack-app` 仓库

### 4️⃣ 配置环境变量

在 Railway 仪表板中设置以下变量：

| 变量名 | 说明 | 示例值 |
|--------|------|--------|
| `DATABASE_URL` | MySQL 数据库连接（从 Railway MySQL 获取） | `jdbc:mysql://host:port/db` |
| `DB_USERNAME` | 数据库用户名 | `root` |
| `DB_PASSWORD` | 数据库密码 | `your-secure-password` |
| `ADMIN_PASSWORD` | 管理员密码 | `secure-pass-123` |

### 5️⃣ 添加 MySQL 数据库

1. 在 Railway 项目中点击 **"Add"** 
2. 选择 **"MySQL"**
3. 等待数据库初始化
4. 点击 MySQL 服务，复制连接信息到环境变量

### 6️⃣ 验证部署

- 访问生成的 URL（例如：`https://blackjack-app-prod.railway.app`）
- 检查 Railway Logs 查看应用日志
- 如有问题，查看错误日志进行调试

## 📝 配置文件说明

### `application.properties` (开发环境)
- 本地 MySQL 连接
- 用于本地开发测试

### `application-prod.properties` (生产环境)
- 使用环境变量
- Railway 自动激活此配置

### `Procfile`
- 告诉 Railway 如何启动应用
- 自动使用 prod 配置

## 🔧 常见问题排查

### ❌ 数据库连接失败
- 确保 `DATABASE_URL` 格式正确
- 验证 `DB_USERNAME` 和 `DB_PASSWORD`
- Railway 会自动初始化数据库，使用前需等待

### ❌ 应用无法启动
- 检查 Railway Logs
- 验证所有必需的环境变量已设置
- 确保 MySQL 服务状态正常

### ❌ 域名无法访问
- 等待 Railway 部署完成（通常 2-5 分钟）
- 检查服务状态是否为 "Running"

## 💰 成本估算
- **免费额度**: $5/月
- **MySQL 数据库**: 包含在免费额度内
- **应用运行**: 包含在免费额度内
- **超出部分**: 按使用量计费

## 📚 更多帮助

- [Railway 文档](https://docs.railway.app)
- [Spring Boot 部署指南](https://spring.io/guides/gs/spring-boot/)
- [MySQL 连接配置](https://docs.railway.app/databases/mysql)

---

✨ 祝部署顺利！如有问题，查看 Railway 控制台日志进行调试。
