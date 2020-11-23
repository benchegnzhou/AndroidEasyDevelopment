'''
记录日志信息
'''
import commonCore as common
import setting
import sqlite3
import os
import time
import operator


class LoggerHelper():
    def __init__(self):
        self.appPort = ""
        self.appPortName = ""
        self.appSize = 0
        self.appSizeStr = "0 MB"
        self.operationUser = ""
        self.up2ZTSCSuccess = False
        self.up2PGYSuccess = False
        self.newVersionName = ""
        self.newVersionCode = ""
        self.logTextDir = ""
        self.logDBDir = ""
        self.isPad = ""
        self.updateNecess = ""
        self.updateMsg = ""
        self.dbFilePath = ""

    def markDirs(self, dir):
        # 判断路径是否存在
        # 存在     True
        # 不存在   False
        if not os.path.exists(dir):
            # 多层创建目录
            os.makedirs(dir)

    def writeText(self):
        '''
        写成文件日志
        :return:
        '''
        try:
            # 文件操作日志写入

            print("日志文件根目录 :" + self.logTextDir + '\\updataLog.log')
            with open(self.logTextDir + '\\updataLog.log', 'a', encoding='UTF-8') as faa:
                faa.writelines("\n")
                faa.writelines("--------------- 更新日志信息 ---------------\n")
                faa.writelines('操作时间             ' + time.strftime("%Y.%m.%d %H:%I:%S", time.localtime(time.time())))
                faa.writelines("\n")
                faa.writelines(self.operationUser + '  上传了 ' + self.appPortName + ' App')
                faa.writelines("\n")
                faa.writelines("版本名称             " + self.newVersionName)
                faa.writelines("\n")
                faa.writelines("版本编号             " + self.newVersionCode)
                faa.writelines("\n")
                faa.writelines("文件大小             " + self.appSizeStr)
                faa.writelines("\n")
                faa.writelines("上传公司内部服务器    %s" % ("成功上传" if self.up2ZTSCSuccess else "上传失败"))
                faa.writelines("\n")
                faa.writelines("上传蒲公英           %s" % ("成功上传" if self.up2PGYSuccess else "上传失败"))
                faa.writelines("\n")
                faa.writelines('操作人                ' + self.operationUser)
                faa.writelines("\n" * 10)

        except Exception as e:
            print(e)

        finally:
            pass

    def getSuccess(self, signal):
        if signal:
            return "上传成功"
        else:
            return "上传失败"

    def lacolDbInit(self):
        '''
        数据库文件没有创建，则创建数据库文件
        '''
        print("dbfile_path %s" % (self.dbFilePath))
        if not os.path.exists(self.dbFilePath):
            self.createDbFile()

    def createDbFile(self):

        try:
            conn = sqlite3.connect(self.dbFilePath)
            cur = conn.cursor()
            create_sql = """create table app_updata(opertionId integer PRIMARY KEY autoincrement,operatorName text,operatorTime text,operation text,appPort text,newVersionName text,newVersionCode text,updateNecess text,updateMsg text,isPad text,appSize text,uptoZtscSuccess text,uptoPgySuccess text);"""
            cur.execute(create_sql)
            cur.close()
            conn.close()
        except Exception as e:
            print(e)
        finally:
            pass

    def writeLogDB(self):
        '''
        日志信息写入数据库
        :param appPort:
        :param appSize:
        :param up2ZtscSuccess:
        :param up2ZtscSuccess:
        :return:
        '''
        try:

            operatorTime = time.strftime("%Y.%m.%d %H:%I:%S", time.localtime(time.time()))

            updateMsg = self.updateMsg
            updateMsg = updateMsg.replace('\n', "\\n");

            # 向数据库插入数据
            conn = sqlite3.connect(self.dbFilePath)
            cur = conn.cursor()

            cur.execute(
                "INSERT INTO APP_UPDATA ( OPERATORNAME, OPERATORTIME, OPERATION, APPPORT, NEWVERSIONNAME, NEWVERSIONCODE,"
                " UPDATENECESS, UPDATEMSG,ISPAD,APPSIZE, uptoZtscSuccess, uptoPgySuccess) \
                       VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')" % (
                    self.operationUser, operatorTime, '发布新版本', self.appPort, self.newVersionName, self.newVersionCode,
                    self.updateNecess,
                    self.updateMsg,
                    '兼容' if operator.eq(self.isPad, '1') else '不兼容',
                    self.appSizeStr, "上传成功" if self.up2ZTSCSuccess else "上传失败",
                    "上传成功" if self.up2PGYSuccess else "上传失败"));

            conn.commit()

        except Exception as e:
            print(e)
        finally:
            conn.close()

    def writeLog(self, appPort, appPortName, appSize, newVersionCode, newVersionName, isPad, updateNecess, updateMsg, operationUser,
                 up2ZTSCSuccess, up2PGYSuccess):
        self.appPort = appPort
        self.appSize = appSize
        self.appSizeStr = "%.2f MB" % (self.appSize / 1024.0 / 1024)
        self.operationUser = operationUser
        self.up2ZTSCSuccess = up2ZTSCSuccess
        self.up2PGYSuccess = up2PGYSuccess
        self.newVersionName = newVersionName
        self.newVersionCode = newVersionCode
        self.logTextDir = setting.LOG_TEXT_DIR
        self.logDBDir = setting.LOG_DB_DIR
        self.updateNecess = updateNecess
        self.markDirs(self.logTextDir)
        self.markDirs(self.logDBDir)
        self.isPad = isPad
        self.updateMsg = updateMsg
        self.dbFilePath = self.logDBDir + '\\updataApp.db'
        self.lacolDbInit()
        self.appPort=appPort
        self.appPortName=appPortName


        self.writeText()
        self.writeLogDB()


logHelper = LoggerHelper()
