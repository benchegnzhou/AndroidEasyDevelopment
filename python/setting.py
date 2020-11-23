'''
日志路径
'''

import commonCore as common

LOG_TEXT_DIR = common.father_path + "\\LOG"

'''
日志数据库路径
'''
LOG_DB_DIR = common.father_path + "\\LOG"
'''
APP 端
'publicApp' == appPort:
self.apkTypeName = '公众版'
elif 'propertyAPP' == appPort:
self.apkTypeName = '物业版'
elif 'committeeAPP' == appPort:
self.apkTypeName = '业委会版'
elif 'b2cSallerLiteAPP' == appPort:
self.apkTypeName = '商城简版'
'''

APP_PORT = "propertyAPP"

'''
权限验证文件入口
'''
USER_INFO_PATH = common.father_path + '//userInform.config'

''''
蒲公英uKey
'''
PGY_uKey = 'c8364150e5514d79cf7563013e8b231d'

''''
蒲公英_api_key
'''
PGY_api_key = 'c8364150e5514d79cf7563013e8b231d'

''''
APK 文件根目录G:\groovyStudy\python实战代码 
'''
AKP_SCANNER_ROOT = common.root_path + "\\app\\build\\outputs\\apk\\"

''''
APK 所在的文件路径
'''
ZTSC_URL_REALSE = 'http://119.3.162.131:7080/pub-service/Service?service=file&function=upload'
# ZTSC_URL_REALSE =  "http://www.correctmap.com.cn/pub-service/Service?service=file&function=upload"

''''
APK 所在的文件路径
'''
ZTSC_UPDATA_MSG_URL = 'http://www.correctmap.com.cn/pub-service/Service?service=dataManager&function=newAppVerson'
