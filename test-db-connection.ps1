# 数据库连接诊断脚本
# 使用方法: powershell -ExecutionPolicy Bypass -File test-db-connection.ps1

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   SmartAdmin 数据库连接诊断工具" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 远程数据库配置
$remoteHost = "62.234.184.96"
$remotePort = 3928
$remoteDbUrl = "jdbc:p6spy:mysql://${remoteHost}:${remotePort}/smart"

Write-Host "【测试 1】测试远程数据库端口连通性..." -ForegroundColor Yellow
try {
    $result = Test-NetConnection -ComputerName $remoteHost -Port $remotePort -InformationLevel Quiet -WarningAction SilentlyContinue
    if ($result) {
        Write-Host "✓ 端口 $remotePort 连通正常" -ForegroundColor Green
    } else {
        Write-Host "✗ 端口 $remotePort 无法连接" -ForegroundColor Red
        Write-Host "  可能原因:" -ForegroundColor Yellow
        Write-Host "  1. 远程数据库服务器未启动" -ForegroundColor Yellow
        Write-Host "  2. 网络防火墙阻止连接" -ForegroundColor Yellow
        Write-Host "  3. IP 地址或端口配置错误" -ForegroundColor Yellow
    }
} catch {
    Write-Host "✗ 测试失败: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "【测试 2】检查本地 MySQL 是否运行..." -ForegroundColor Yellow
try {
    $localResult = Test-NetConnection -ComputerName "127.0.0.1" -Port 3306 -InformationLevel Quiet -WarningAction SilentlyContinue
    if ($localResult) {
        Write-Host "✓ 本地 MySQL (端口 3306) 正在运行" -ForegroundColor Green
    } else {
        Write-Host "✗ 本地 MySQL 未运行" -ForegroundColor Red
        Write-Host "  建议: 安装并启动本地 MySQL" -ForegroundColor Yellow
    }
} catch {
    Write-Host "✗ 测试失败: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "【测试 3】检查本地 Redis 是否运行..." -ForegroundColor Yellow
try {
    $redisResult = Test-NetConnection -ComputerName "127.0.0.1" -Port 6379 -InformationLevel Quiet -WarningAction SilentlyContinue
    if ($redisResult) {
        Write-Host "✓ 本地 Redis (端口 6379) 正在运行" -ForegroundColor Green
    } else {
        Write-Host "✗ 本地 Redis 未运行" -ForegroundColor Red
        Write-Host "  建议: 安装并启动本地 Redis" -ForegroundColor Yellow
    }
} catch {
    Write-Host "✗ 测试失败: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "【测试 4】检查配置文件..." -ForegroundColor Yellow
$configFile = "sa-base\src\main\resources\dev\sa-base.yaml"
if (Test-Path $configFile) {
    Write-Host "✓ 配置文件存在: $configFile" -ForegroundColor Green

    # 读取配置文件中的数据库 URL
    $configContent = Get-Content $configFile -Raw
    if ($configContent -match "url:\s*jdbc:p6spy:mysql://([^:]+):(\d+)/") {
        $dbHost = $matches[1]
        $dbPort = $matches[2]
        Write-Host "  数据库主机: $dbHost" -ForegroundColor Cyan
        Write-Host "  数据库端口: $dbPort" -ForegroundColor Cyan

        if ($dbHost -eq "127.0.0.1" -or $dbHost -eq "localhost") {
            Write-Host "  ✓ 已配置为本地数据库" -ForegroundColor Green
        } else {
            Write-Host "  ⚠ 当前使用远程数据库" -ForegroundColor Yellow
        }
    }
} else {
    Write-Host "✗ 配置文件不存在: $configFile" -ForegroundColor Red
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   诊断结果总结" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "【建议操作】" -ForegroundColor Yellow
Write-Host ""

# 检查是否有 MySQL 可用
if ($localResult) {
    Write-Host "1. ✓ 本地 MySQL 可用，建议切换到本地数据库" -ForegroundColor Green
    Write-Host "   参考 DATABASE_FIX_GUIDE.md 中的【方案 2】" -ForegroundColor Gray
} elseif ($result) {
    Write-Host "1. ✓ 远程数据库可用，建议等待应用自动重连" -ForegroundColor Green
    Write-Host "   已优化连接配置，提高了容错能力" -ForegroundColor Gray
} else {
    Write-Host "1. ✗ 无可用数据库，请选择以下方案之一:" -ForegroundColor Red
    Write-Host "   - 安装本地 MySQL（推荐）" -ForegroundColor Cyan
    Write-Host "   - 使用 Docker 启动 MySQL 容器" -ForegroundColor Cyan
    Write-Host "   - 联系远程数据库管理员恢复服务" -ForegroundColor Cyan
}

Write-Host ""
Write-Host "2. 详细的修复步骤请参考: DATABASE_FIX_GUIDE.md" -ForegroundColor Cyan
Write-Host ""
Write-Host "按任意键退出..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
